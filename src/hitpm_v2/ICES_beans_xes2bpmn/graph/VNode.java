package hitpm_v2.ICES_beans_xes2bpmn.graph;

public class VNode {
  public myNode node;
  
  public ENode firstadj;
  
  public VNode(String name) {
    myNode node = new ActivityNode(name);
    this.node = node;
  }
  
  public VNode(String name, int index) {
    myNode node = new GatewayNode(name, index);
    this.node = node;
  }
}
