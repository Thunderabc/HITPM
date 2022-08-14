package hitpm_v2.ICES_beans_xes2bpmn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.deckfour.xes.model.XLog;

import hitpm_v2.ICES_beans_xes2bpmn.graph.ENode;
import hitpm_v2.ICES_beans_xes2bpmn.graph.GatewayNode;
import hitpm_v2.ICES_beans_xes2bpmn.graph.Graph;
import hitpm_v2.ICES_beans_xes2bpmn.graph.VNode;
import hitpm_v2.ICES_beans_xes2bpmn.graph.myNode;
import hitpm_v2.ICES_beans_bpmn.converter.BpmnXMLConverter;
import hitpm_v2.ICES_beans_bpmn.model.Association;
import hitpm_v2.ICES_beans_bpmn.model.BpmnModel;
import hitpm_v2.ICES_beans_bpmn.model.EndEvent;
import hitpm_v2.ICES_beans_bpmn.model.ExclusiveGateway;
import hitpm_v2.ICES_beans_bpmn.model.FlowElement;
import hitpm_v2.ICES_beans_bpmn.model.GraphicInfo;
import hitpm_v2.ICES_beans_bpmn.model.ParallelGateway;
import hitpm_v2.ICES_beans_bpmn.model.Process;
import hitpm_v2.ICES_beans_bpmn.model.SequenceFlow;
import hitpm_v2.ICES_beans_bpmn.model.StartEvent;
import hitpm_v2.ICES_beans_bpmn.model.TextAnnotation;
import hitpm_v2.ICES_beans_bpmn.model.UserTask;
import hitpm_v2.ICES_beans_xes2bpmn.value.myPV;

public class Graph2BPMN {
  public void convert(Graph graph,XLog log) throws IOException, ParseException {
    BpmnModel model = new BpmnModel();
    Process process = new Process();
    model.addProcess(process);
    
    //�����¼���Ϣ
    for (VNode vNode : graph.vexs) {
      myNode node = vNode.node;
      if (node instanceof hitpm_v2.ICES_beans_xes2bpmn.graph.ActivityNode) {
        if (node.nodeName.equals("Start")) {
          StartEvent startEvent = new StartEvent();
          startEvent.setId("Start");
          process.addFlowElement((FlowElement)startEvent);
          continue;
        } 
        if (node.nodeName.equals("End")) {
          EndEvent endEvent = new EndEvent();
          endEvent.setId("End");
          process.addFlowElement((FlowElement)endEvent);
          continue;
        } 
        UserTask task = new UserTask();
        String name = node.nodeName.replaceAll("\\s*", "");
        task.setId(name);
        task.setName(name);
        process.addFlowElement((FlowElement)task);
        continue;
      } 
      if (node.nodeName.equals("GatewayXorS") || node.nodeName.equals("GatewayXorE")) {
    	if(((GatewayNode)node).flag==true) {
	        ExclusiveGateway gateway = new ExclusiveGateway();
	        gateway.setId(String.valueOf(node.nodeName) + ((GatewayNode)node).index);
	        process.addFlowElement((FlowElement)gateway);
	        continue;
	     }
      } 
      if (node.nodeName.equals("GatewayAndS") || node.nodeName.equals("GatewayAndE")) {
        ParallelGateway gateway = new ParallelGateway();
        gateway.setId(String.valueOf(node.nodeName) + ((GatewayNode)node).index);
        process.addFlowElement((FlowElement)gateway);
      } 
    } 
    
    //����ߵ���Ϣ
    for (VNode vNode : graph.vexs) {
      if (vNode.firstadj == null)
        continue; 
      ENode e = vNode.firstadj;
      String from = "";
      if (vNode.node instanceof hitpm_v2.ICES_beans_xes2bpmn.graph.ActivityNode) {
        from = vNode.node.nodeName.replaceAll("\\s*", "");
      } else {
    	if(((GatewayNode)vNode.node).flag!=true)
    		continue;
    	from = String.valueOf(vNode.node.nodeName) + ((GatewayNode)vNode.node).index;
      } 
      while (e != null) {
        String to = "";
        VNode v = graph.vexs.get(e.adjvex);
        if (v.node instanceof hitpm_v2.ICES_beans_xes2bpmn.graph.ActivityNode) {
          to = v.node.nodeName.replaceAll("\\s*", "");
        } else {
          to = String.valueOf(v.node.nodeName) + ((GatewayNode)v.node).index;
        } 
        String id = String.valueOf(from) + to;
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId(id);
        sequenceFlow.setSourceRef(from);
        sequenceFlow.setTargetRef(to);
        process.addFlowElement((FlowElement)sequenceFlow);
        e = e.nextadj;
      } 
    } 
    
    process.setId("my-process");
    
    //����ע����Ϣ
    myPV pv = new hitpm_v2.ICES_beans_xes2bpmn.value.myPV();
    HashMap<String, String> valueMap = pv.convert(log);
    
    for(Entry<String, String> entry:valueMap.entrySet()) {
		
    	TextAnnotation text= new TextAnnotation();
		
		text.setText(entry.getValue());
		text.setId(entry.getKey()+"value");
		
		process.addArtifact(text);
		
		Association association =new Association();
		association.setSourceRef(entry.getKey());
		association.setTargetRef(entry.getKey()+"value");
		association.setId(entry.getKey()+"2value");
		
		process.addArtifact(association);

    }
    
    //���ɲ�����Ϣ
    (new BpmnAutoLayout(model)).execute();
    
    writeXML(model, "Model.js");
    
    //����ע�͵�λ����Ϣ
    Map<String, GraphicInfo> Loc = model.getLocationMap();
    Map<String, List<GraphicInfo>> flow = model.getFlowLocationMap();
    
    for(Entry<String, String> entry:valueMap.entrySet()) {
    	
    	//ע�Ͷ�Ӧ���¼���λ����Ϣ
    	GraphicInfo activityInfo=Loc.get(entry.getKey());
    	//System.out.println("now handle acticity:"+entry.getKey());
    	
    	//�¼���λ
    	double x=activityInfo.getX()+activityInfo.getWidth()/2;
    	double y=activityInfo.getY()+activityInfo.getHeight();
    	    	
	    GraphicInfo graphicInfo=new GraphicInfo();
	    graphicInfo.setX(x);
	    graphicInfo.setY(y+100);
	    graphicInfo.setWidth(150);
	    graphicInfo.setHeight(800);
	    
	    Loc.put(entry.getKey()+"value", graphicInfo);
	    
	    //associationλ����Ϣ
	    List<GraphicInfo> list= new ArrayList<GraphicInfo>();
	    GraphicInfo start = new GraphicInfo();
	    GraphicInfo end = new GraphicInfo();
	    
	    start.setX(x);
	    start.setY(y);
	    
	    end.setX(x);
	    end.setY(y+100);
	    
	    list.add(start);
	    list.add(end);
	    
	    flow.put(entry.getKey()+"2value",list);
	    	
    } 

    
    //ת��ΪXML
    writeXML(model, "valueModel.js");
    
  }
  
  public void writeXML(BpmnModel model,String path) throws IOException,ParseException {
	  	
	  BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
	    String str1 = "var Str = `";
	    String str2 = "`";
	    byte[] b1 = str1.getBytes("UTF8");
	    byte[] b2 = str2.getBytes("UTF8");
	    byte[] b3 = bpmnXMLConverter.convertToXML(model);
	    byte[] bytes = byteMergerAll(new byte[][] { b1, b3, b2 });
	    ClassLoader classLoader = Thread.currentThread()
	      .getContextClassLoader();
	    if (classLoader == null)
	      classLoader = ClassLoader.getSystemClassLoader(); 
	    URL url = classLoader.getResource("");
	    String ROOT_CLASS_PATH = String.valueOf(url.getPath()) + "/";
	    File rootFile = new File(ROOT_CLASS_PATH);
	    String WEB_INFO_DIRECTORY_PATH = String.valueOf(rootFile.getParent()) + "/";
	    File webInfoDir = new File(WEB_INFO_DIRECTORY_PATH);
	    String SERVLET_CONTEXT_PATH = String.valueOf(webInfoDir.getParent()) + "/";
	    File file = new File(SERVLET_CONTEXT_PATH, path);
	    if (file.exists())
	      file.delete(); 
	    if (!file.getParentFile().exists()) {
	      boolean mkdir = file.getParentFile().mkdirs();
	      if (!mkdir)
	        throw new RuntimeException(); 
	    } 
	    file.createNewFile();
	    try {
	      FileOutputStream fos = new FileOutputStream(file);
	      fos.write(bytes);
	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
}
  
  private static byte[] byteMergerAll(byte[]... values) {
    int length_byte = 0;
    for (int i = 0; i < values.length; i++)
      length_byte += (values[i]).length; 
    byte[] all_byte = new byte[length_byte];
    int countLength = 0;
    for (int j = 0; j < values.length; j++) {
      byte[] b = values[j];
      System.arraycopy(b, 0, all_byte, countLength, b.length);
      countLength += b.length;
    } 
    return all_byte;
  }
}
