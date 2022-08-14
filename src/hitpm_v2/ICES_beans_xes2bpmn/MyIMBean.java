package hitpm_v2.ICES_beans_xes2bpmn;

import java.io.IOException;
import java.text.ParseException;

import hitpm_v2.ICES_beans_xes2bpmn.graph.Graph;
import org.deckfour.xes.model.XLog;
import org.processmining.plugins.InductiveMiner.efficienttree.EfficientTree;
import org.processmining.plugins.InductiveMiner.efficienttree.EfficientTree2processTree;
import org.processmining.plugins.inductiveminer2.logs.IMLog;
import org.processmining.processtree.ProcessTree;

import hitpm_v2.ICES_beans_InductiveMiner.mineTree;

public class MyIMBean {
  private IMLog ILog;
  
  private EfficientTree tree;
  
  private ProcessTree procesTree;
  
  public IMLog getILog() {
    return this.ILog;
  }
  
  public void setILog(XLog xlog) throws IOException, ParseException {
    
	mineTree miner =new mineTree();
	
	this.tree=miner.mine(xlog);
	
	//System.out.println(tree.toString());
	
    this.procesTree = EfficientTree2processTree.convert(this.tree);
    
    Tree2Graph tree2Graph = new Tree2Graph();
    Graph graph = tree2Graph.convert(this.procesTree);

    
    Graph2BPMN graph2bpmn = new Graph2BPMN();
    graph2bpmn.convert(graph,xlog);
  }
  
  public ProcessTree getProcessTree() {
    return this.procesTree;
  }
}
