package hitpm_v2.ICES_beans_xes2bpmn.graph;

public class GatewayNode extends myNode {
  public int index;
  
  public boolean flag=true;
  
  public GatewayNode(String name, int index) {
    super(name);
    this.index = index;
  }
}
