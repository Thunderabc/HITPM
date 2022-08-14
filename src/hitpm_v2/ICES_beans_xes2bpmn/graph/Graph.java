package hitpm_v2.ICES_beans_xes2bpmn.graph;

import java.util.ArrayList;

public class Graph {
  public ArrayList<VNode> vexs = new ArrayList<>();
  
  public int getNum() {
    return this.vexs.size();
  }
  
  public int insertVex(String name) {
    VNode vex = new VNode(name);
    this.vexs.add(vex);
    return indexOfVex(name);
  }
  
  public int insertVex(String name, int index) {
    VNode vex = new VNode(name, index);
    this.vexs.add(vex);
    if (name.equals("GatewayAndS"))
      return indexOfGAS(index); 
    if (name.equals("GatewayAndE"))
      return indexOfGAE(index); 
    if (name.equals("GatewayXorS"))
      return indexOfGXorS(index); 
    if (name.equals("GatewayXorE"))
      return indexOfGXorE(index); 
    return -1;
  }
  
  public int indexOfVex(String name) {
    for (int i = 0; i < this.vexs.size(); i++) {
      if (((VNode)this.vexs.get(i)).node.nodeName.equals(name))
        return i; 
    } 
    return -1;
  }
  
  public int indexOfGAS(int index) {
    for (int i = 0; i < this.vexs.size(); i++) {
      myNode node = ((VNode)this.vexs.get(i)).node;
      if (node.nodeName.equals("GatewayAndS") && 
        ((GatewayNode)node).index == index)
        return i; 
    } 
    return -1;
  }
  
  public int indexOfGAE(int index) {
    for (int i = 0; i < this.vexs.size(); i++) {
      myNode node = ((VNode)this.vexs.get(i)).node;
      if (node.nodeName.equals("GatewayAndE") && 
        ((GatewayNode)node).index == index)
        return i; 
    } 
    return -1;
  }
  
  public int indexOfGXorS(int index) {
    for (int i = 0; i < this.vexs.size(); i++) {
      myNode node = ((VNode)this.vexs.get(i)).node;
      if (node.nodeName.equals("GatewayXorS") && 
        ((GatewayNode)node).index == index)
        return i; 
    } 
    return -1;
  }
  
  public int indexOfGXorE(int index) {
    for (int i = 0; i < this.vexs.size(); i++) {
      myNode node = ((VNode)this.vexs.get(i)).node;
      if (node.nodeName.equals("GatewayXorE") && 
        ((GatewayNode)node).index == index)
        return i; 
    } 
    return -1;
  }
  
  public myNode valueOfVex(int v) {
    if (v < 0 || v >= this.vexs.size())
      return null; 
    return ((VNode)this.vexs.get(v)).node;
  }
  
  public boolean insertEdge(int v1, int v2) {
    if (v1 < 0 || v2 < 0 || v1 >= this.vexs.size() || v2 >= this.vexs.size())
      throw new ArrayIndexOutOfBoundsException(); 
    if (v1 == v2)
      return false; 
    ENode vex1 = new ENode(v2);
    if (((VNode)this.vexs.get(v1)).firstadj == null) {
      ((VNode)this.vexs.get(v1)).firstadj = vex1;
    } else if (!existEdge(v1, v2)) {
      vex1.nextadj = ((VNode)this.vexs.get(v1)).firstadj;
      ((VNode)this.vexs.get(v1)).firstadj = vex1;
    } 
    return true;
  }
  
  public boolean deleteEdge(int v1, int v2) {
    if (v1 < 0 || v2 < 0 || v1 >= this.vexs.size() || v2 >= this.vexs.size())
      throw new ArrayIndexOutOfBoundsException(); 
    ENode current = ((VNode)this.vexs.get(v1)).firstadj;
    ENode previous = null;
    while (current != null && current.adjvex != v2) {
      previous = current;
      current = current.nextadj;
    } 
    if (current != null)
      previous.nextadj = current.nextadj; 
    return true;
  }
  
  public boolean existNode(String name) {
    boolean exist = false;
    for (int i = 0; i < this.vexs.size(); i++) {
      if (((VNode)this.vexs.get(i)).node.nodeName.equals(name))
        exist = true; 
    } 
    return exist;
  }
  
  public boolean existEdge(String from, String to) {
    boolean exist = false;
    int fromIndex = indexOfVex(from);
    if (((VNode)this.vexs.get(fromIndex)).firstadj != null) {
      ENode e = ((VNode)this.vexs.get(fromIndex)).firstadj;
      while (e != null) {
        if (((VNode)this.vexs.get(e.adjvex)).node.nodeName == to) {
          exist = true;
          continue;
        } 
        e = e.nextadj;
      } 
    } 
    return exist;
  }
  
  public boolean existEdge(int fromIndex, int toIndex) {
    boolean exist = false;
    if (((VNode)this.vexs.get(fromIndex)).firstadj != null) {
      ENode e = ((VNode)this.vexs.get(fromIndex)).firstadj;
      while (e != null) {
        if (e.adjvex == toIndex) {
          exist = true;
          break;
        } 
        e = e.nextadj;
      } 
    } 
    return exist;
  }
  
  public String toString() {
    String graphString = "";
    for (VNode vNode : this.vexs) {
      if (vNode.node instanceof ActivityNode) {
        graphString = String.valueOf(graphString) + vNode.node.nodeName;
      } else {
        GatewayNode gNode = (GatewayNode)vNode.node;
        graphString = String.valueOf(graphString) + gNode.nodeName + gNode.index;
      } 
      ENode eNode = vNode.firstadj;
      while (eNode != null) {
        graphString = String.valueOf(graphString) + "->";
        if (((VNode)this.vexs.get(eNode.adjvex)).node instanceof ActivityNode) {
          graphString = String.valueOf(graphString) + ((VNode)this.vexs.get(eNode.adjvex)).node.nodeName;
        } else {
          GatewayNode gNode = (GatewayNode)((VNode)this.vexs.get(eNode.adjvex)).node;
          graphString = String.valueOf(graphString) + gNode.nodeName + gNode.index;
        } 
        eNode = eNode.nextadj;
      } 
      graphString = String.valueOf(graphString) + "\n";
    } 
    return graphString;
  }
}
