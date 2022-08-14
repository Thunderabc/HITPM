package hitpm_v2.ICES_beans_xes2bpmn.graph;

public class ENode {
  public int adjvex;
  
  public ENode nextadj;
  
  public ENode(int adjvex) {
    this.adjvex = adjvex;
  }
}
