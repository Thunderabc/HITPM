package hitpm_v2.ICES_beans_xes2bpmn;

import java.util.Iterator;


import hitpm_v2.ICES_beans_xes2bpmn.graph.ActivityNode;
import hitpm_v2.ICES_beans_xes2bpmn.graph.ENode;
import hitpm_v2.ICES_beans_xes2bpmn.graph.GatewayNode;
import hitpm_v2.ICES_beans_xes2bpmn.graph.Graph;
import hitpm_v2.ICES_beans_xes2bpmn.graph.VNode;
import hitpm_v2.ICES_beans_xes2bpmn.graph.myNode;

import org.processmining.processtree.Block;
import org.processmining.processtree.Edge;
import org.processmining.processtree.Node;
import org.processmining.processtree.ProcessTree;

public class Tree2Graph {
  public Graph graph;
  
  public ProcessTree tree;
  
  private int xorIndex = 1;
  
  private int andIndex = 1;
  
  public Graph convert(ProcessTree tree) {
    this.tree = tree;
    Node root = tree.getRoot();
    this.graph = new Graph();
    toGraph(root);
    
    delXorGatewayEnd();
    mergeGateway();
    return this.graph;
  }
  
  public void toGraph(Node root) {
    int[] se = new int[2];
    se[0] = this.graph.insertVex("Start");
    se[1] = this.graph.insertVex("End");
    int[] ends = toGraph(root, se);
    this.graph.insertEdge(se[0], ends[0]);
    this.graph.insertEdge(ends[1], se[1]);
  }
  
  public int[] toGraph(Node root, int[] se) {
    int[] ends = new int[2];
    if (root.isLeaf()) {
      String name = root.toStringShort();
      if (name.equals("tau")) {
        ends[0] = se[1];
        ends[1] = se[0];
      } else {
        int index = this.graph.indexOfVex(name);
        if (index < 0)
          index = this.graph.insertVex(name); 
        ends[0] = index;
        ends[1] = index;
      } 
    } else if (root instanceof Block) {
      Block block = (Block)root;
      Iterator<Edge> it = block.getOutgoingEdges().iterator();
      if (root.toStringShort().equals("Seq")) {
        int[] temp = { -1, -1 };
        boolean first = true;
        int last = -1;
        while (it.hasNext()) {
          Edge edge = it.next();
          Node node = edge.getTarget();
          temp = toGraph(node, se);
          if (first) {
            ends[0] = temp[0];
            last = temp[0];
            first = false;
          } else {
            this.graph.insertEdge(last, temp[0]);
            last = temp[1];
          } 
          if (!it.hasNext())
            ends[1] = temp[1]; 
        } 
      } else if (root.toStringShort().equals("Xor")) {
        ends[0] = this.graph.insertVex("GatewayXorS", this.xorIndex);
        ends[1] = this.graph.insertVex("GatewayXorE", this.xorIndex);
        this.xorIndex++;
        while (it.hasNext()) {
          Edge edge = it.next();
          Node node = edge.getTarget();
          int[] temp = toGraph(node, ends);
          this.graph.insertEdge(ends[0], temp[0]);
          this.graph.insertEdge(temp[1], ends[1]);
        } 
      } else if (root.toStringShort().equals("And")) {
        ends[0] = this.graph.insertVex("GatewayAndS", this.andIndex);
        ends[1] = this.graph.insertVex("GatewayAndE", this.andIndex);
        this.andIndex++;
        while (it.hasNext()) {
          Edge edge = it.next();
          Node node = edge.getTarget();
          int[] temp = toGraph(node, ends);
          this.graph.insertEdge(ends[0], temp[0]);
          this.graph.insertEdge(temp[1], ends[1]);
        } 
      } else if (root.toStringShort().equals("XorLoop")) {
        ends[1] = this.graph.insertVex("GatewayXorS", this.xorIndex);
        this.xorIndex++;
        boolean first = true;
        int[] temp = { -1, -1 };
        while (it.hasNext()) {
          Edge edge = it.next();
          Node node = edge.getTarget();
          if (first) {
            temp = toGraph(node, ends);
            this.graph.insertEdge(temp[1], ends[1]);
            ends[0] = ends[1];
            ends[1] = temp[0];
            first = false;
            continue;
          } 
          if(!node.toStringShort().equals("tau")){
	          temp = toGraph(node, ends);
	          this.graph.insertEdge(ends[0], temp[0]);
	          this.graph.insertEdge(temp[1], ends[1]);
	      }
        } 
        int i = ends[0];
        ends[0] = ends[1];
        ends[1] = i;
      } 
    } 
    

    return ends;
  }
  
  
  public void mergeGateway() {
	  
	  for(int i=0;i<this.graph.vexs.size();i++) {
		  	VNode vNode=this.graph.vexs.get(i);
			if(vNode.node instanceof GatewayNode) {
				
				if(((GatewayNode)vNode.node).flag==true) {
					
					ENode eNode=vNode.firstadj;	
					
					//ǰһ����㣬����ɾ������
					Object lastNode =vNode;
					
					while(eNode!=null) {
						
						VNode gateNode = graph.vexs.get(eNode.adjvex);
						
						if(gateNode.node instanceof GatewayNode) {
							
							if(lastNode instanceof VNode) {
								((VNode) lastNode).firstadj=eNode.nextadj;
							}
							else if(lastNode instanceof ENode){
								((ENode) lastNode).nextadj=eNode.nextadj;
							}
							
							if(vNode.firstadj!=null) {
								
								ENode linkNode=vNode.firstadj;
								
								while(linkNode.nextadj!=null) {
									linkNode=linkNode.nextadj;
								}								
								linkNode.nextadj=graph.vexs.get(eNode.adjvex).firstadj;								
							}
							else {

								vNode.firstadj=graph.vexs.get(eNode.adjvex).firstadj;
								
							}							
							((GatewayNode)gateNode.node).flag=false;
						
						}
						
						
						eNode=eNode.nextadj;
						
						if(lastNode instanceof VNode) {
							lastNode=((VNode) lastNode).firstadj;
						}
						else if(lastNode instanceof ENode){
							
							lastNode=((ENode) lastNode).nextadj;
							
						}
					}
					
				}
			}
	  }
  }
  
  public void delXorGatewayEnd() {
	  for(int i=0;i<this.graph.vexs.size();i++) {
		  	
		  	VNode vNode=this.graph.vexs.get(i);
			if(vNode.node instanceof GatewayNode) {
				
				
				if(vNode.node.nodeName.equals("GatewayXorE")) {
					if(vNode.firstadj!=null) {
						
						int gatewayIndex=graph.indexOfGXorE(((GatewayNode)vNode.node).index);
						int nextIndex = vNode.firstadj.adjvex;
						
						for(VNode v:graph.vexs) {
							
							ENode eNode=v.firstadj;
							while(eNode!=null) {
								if(eNode.adjvex==gatewayIndex)
									eNode.adjvex=nextIndex;
								eNode=eNode.nextadj;
							}
						}
						((GatewayNode)vNode.node).flag=false;
				
					}
				}
			}
	  }
  }	

}
