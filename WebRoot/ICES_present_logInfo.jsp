<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.jspsmart.upload.*"%>
<%@ page import="java.text.*"%>
<%@ page import="org.deckfour.xes.model.XLog"%>
<%@ page import="java.io.*"%>
<%@ page import="org.deckfour.xes.classification.XEventNameClassifier"%>
<%@ page import="org.deckfour.xes.classification.XEventClasses"%>
<%@ page import="org.deckfour.xes.classification.XEventClass"%>
<%@ page import="org.processmining.plugins.InductiveMiner.efficienttree.EfficientTree"%>
<%@ page import="org.processmining.plugins.inductiveminer2.plugins.InductiveMinerDialog" %>
<%@ page import="org.processmining.plugins.inductiveminer2.logs.IMLog"%>
<%@ page import="org.processmining.plugins.inductiveminer2.logs.IMLogImpl"%>
<%@ page import="org.processmining.plugins.inductiveminer2.mining.MiningParameters"%>
<%@ page import="org.processmining.framework.packages.PackageManager.Canceller"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="org.deckfour.xes.model.impl.XAttributeTimestampImpl"%>
<%@ page import="java.io.File"%>


<jsp:useBean id="myParse" class="hitpm_v2.ICES_beans_xml.XML3"/>
<jsp:useBean id="mylog" class="hitpm_v2.ICES_beans_processLog.ICES_beans_logInfo" scope="session"/>

<html>
  <head><title>处理上传的事件日志</title><script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"62988",secure:"51521"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
  <body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc3-11" data-genuitec-path="/HITpm/WebRoot/pmResults/logInfo.jsp">
    <h2 data-genuitec-lp-enabled="false" data-genuitec-file-id="wc3-11" data-genuitec-path="/HITpm/WebRoot/pmResults/logInfo.jsp">处理上传的事件日志</h2>
    <hr>
    <%-- out.println(" 文件路径为：<%=path.getFilePath()%><br>"); --%>
<%--     <%
       ClassLoader classLoader = Thread.currentThread()
               .getContextClassLoader();  
       if (classLoader == null) {  
           classLoader = ClassLoader.getSystemClassLoader();  
       } 
       java.net.URL url = classLoader.getResource("");  
       String ROOT_CLASS_PATH = url.getPath() + "/"; 
       File rootFile = new File(ROOT_CLASS_PATH);  
       String WEB_INFO_DIRECTORY_PATH = rootFile.getParent() + "/";
       File webInfoDir = new File(WEB_INFO_DIRECTORY_PATH);  
       String SERVLET_CONTEXT_PATH = webInfoDir.getParent() + "/"; 
       System.out.println(SERVLET_CONTEXT_PATH);
       
    	String filePathName = SERVLET_CONTEXT_PATH+"chap0\\upload\\Sample.xes";
    /* "E:\\javaEE\\workspace\\HITpm\\WebRoot\\chap0\\Simple12_10.xes"; */
    /* path.getFilePath(); */
    System.out.println(filePathName);
    java.io.FileInputStream file1 = myParse.convert(filePathName);
    XLog log = myParse.getLog(file1);		
  	XEventNameClassifier classifier = new XEventNameClassifier();
  	mylog.setSize(myParse.getLogSize(log));
  	mylog.setEventKind(myParse.getLogInfo(log).getNameClasses().size());
  	mylog.setEventNum(myParse.getLogInfo(log).getNumberOfEvents());
  	mylog.setTraceNum(myParse.getLogInfo(log).getNumberOfTraces());
 			
     %> --%>
    
	<%
	int log = mylog.getLog();
	int traceNum = mylog.getTraceNum();
	int eventNum = mylog.getEventNum();
	int eventKind = mylog.getEventKind();
	int actor=mylog.getActor();
	String startTime=mylog.getStartTimeStr();
	String endTime=mylog.getEndTimeStr();
	%>
    
	<script src="https://how2j.cn/study/js/chartjs/2.8.0/chart.min.js"></script>
	
	<div style="width:400px;margin:0px auto">
	    <canvas id="myChart" ></canvas>
	</div>
    <canvas id="myChart" width="400" height="400"></canvas>
	<script>
	const ctx = document.getElementById('myChart').getContext('2d');
	const myChart = new Chart(ctx, {
	    type: 'bar',
	    data: {
	        labels: ['流程', '实例', '事件','事件种类','参与者'],
	        datasets: [{
	            label: '数量',
	            /* data: [mylog.getSize(), mylog.getTraceNum(), mylog.getEventNum(), mylog.getEventKind()], */
	            data: [<%=log%>, <%=traceNum%>, <%=eventNum%>, <%=eventKind%>, <%=actor%>],
	            backgroundColor: [
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(255, 99, 132, 0.2)'

	                
	            ],
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});
	</script>
    
  </body> 
</html>
