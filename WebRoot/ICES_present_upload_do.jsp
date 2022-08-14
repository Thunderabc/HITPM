<%@page import="hitpm_v2.ICES_beans_activityActor.ActivityActorMining"%>
<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ page import="com.jspsmart.upload.*"%>
<%@ page import="java.text.*"%>
<%@ page import="org.deckfour.xes.model.XLog"%>
<%@ page import="java.io.*"%>
<%@ page import="org.deckfour.xes.classification.XEventNameClassifier"%>
<%@ page import="org.deckfour.xes.classification.XEventClasses"%>
<%@ page import="org.deckfour.xes.classification.XEventClass"%>
<%@ page import="org.processmining.plugins.InductiveMiner.efficienttree.EfficientTree"%>
<%@ page import="org.processmining.plugins.inductiveminer2.plugins.InductiveMinerDialog" %>>
<%@ page import="org.processmining.plugins.inductiveminer2.logs.IMLog"%>
<%@ page import="org.processmining.plugins.inductiveminer2.logs.IMLogImpl"%>
<%@ page import="org.processmining.plugins.inductiveminer2.mining.MiningParameters"%>
<%@ page import="org.processmining.framework.packages.PackageManager.Canceller"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="org.deckfour.xes.model.impl.XAttributeTimestampImpl"%>
<%@ page import="java.io.File"%>
<%@ page import="org.deckfour.xes.info.impl.XTimeBoundsImpl"%>



<jsp:useBean id="myParse" class="hitpm_v2.ICES_beans_xml.XML3"/>

<jsp:useBean id="mylog" class="hitpm_v2.ICES_beans_processLog.ICES_beans_logInfo" scope="session"/>
<jsp:useBean id="aabean" class="hitpm_v2.ICES_beans_activityActor.Myaabean" scope="session"/>
<jsp:useBean id="aavbean" class="hitpm_v2.ICES_beans_activityActorValue.Myaavbean" scope="session"/>
<jsp:useBean id="aav" class="hitpm_v2.ICES_beans_activityActorValue.AavTable"/>
<jsp:useBean id="IM" class="hitpm_v2.ICES_beans_xes2bpmn.MyIMBean"/>
<jsp:useBean id="Trace" class="hitpm_v2.ICES_beans_processLog.ICES_beans_TraceStatistics" scope="session"/>
<%-- <jsp:useBean id="lyq" class="myTemp.myPV"/> --%>
<%-- 
<jsp:useBean id="path" class="before.Path" scope="session"/> --%>

<html>
  <head><title>处理上传的事件日志</title><script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"62988",secure:"51521"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
  <body>
	<hr>
    
	<%
    		// 新建一个SmartUpload对象,此项是必须的
    			SmartUpload myupload = new SmartUpload();		
    			// 初始化,此项是必须的
    			myupload.initialize(pageContext);		
    			// 设定允许上传的文件（通过扩展名限制）
    			 myupload.setAllowedFilesList("xml,xes");		 
    			// 设定禁止上传的文件（通过扩展名限制）
    			 myupload.setDeniedFilesList("exe,bat,jsp,htm,html,");
    			
    			try{			
    		// 上传文件,此项是必须的
    		myupload.upload();			
    		// 统计上传文件的总数
    		int count = myupload.getFiles().getCount();			
    		// 取得Request对象
    		Request myRequest = myupload.getRequest();
    		String rndFilename,fileExtName,fileName,filePathName,memo;
    	/* 			Date dt = null; 
    		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");  */
    		
    		
    		
    		// 逐一提取上传文件信息，同时可保存文件	
    		for (int i=0;i<count;i++){
    			//　取得一个上传文件
    			com.jspsmart.upload.File file = myupload.getFiles().getFile(i);				
    			// 若文件不存在则继续
    			if (file.isMissing()) continue
    			;		
    			// 取得文件全名
    			filePathName = file.getFilePathName();
    			fileExtName = file.getFileExt();
    			
    			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();  
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
    			
    			String name="Sample"+"."+fileExtName;
    			file.saveAs("/upload/" + name, myupload.SAVE_VIRTUAL);
    			filePathName="/upload/"+name;
    			
/*     			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();  
       			if (classLoader == null) {  
           			classLoader = ClassLoader.getSystemClassLoader();  
       			} 
       			java.net.URL url = classLoader.getResource("");  
      			String ROOT_CLASS_PATH = url.getPath() + "/"; 
       			File rootFile = new File(ROOT_CLASS_PATH);  
       			String WEB_INFO_DIRECTORY_PATH = rootFile.getParent() + "/";
       			File webInfoDir = new File(WEB_INFO_DIRECTORY_PATH);  
       			String SERVLET_CONTEXT_PATH = webInfoDir.getParent() + "/"; 
       			System.out.println(SERVLET_CONTEXT_PATH); */
       
    			String fp = SERVLET_CONTEXT_PATH+"upload\\Sample.xes";
    					
    			// 显示当前文件信息
    			/* XLog log = myParse.read(filePathName); */
    			/* out.println("Log共有：<br>"+myParse.getTranceNum(filePathName)+"条trace"); */
    			
    			/* out.println(" 文件路径为："+filePathName+"<br>"); */
    			java.io.FileInputStream file1 = myParse.convert(fp);
    			
    			/* java.io.FileInputStream file1 = myParse.convert("./Simple12_10.xes"); */
    			/* out.println("该文件是否可以以事件日志形式导入："+myParse.canParser(file1)); */
    			XLog log = myParse.getLog(file1);
    			
    			
    			/* XEventNameClassifier classifier = new XEventNameClassifier(); */
/*     			mylog.setEventKind(myParse.getLogInfo(log).getNameClasses().size());
    			mylog.setEventNum(myParse.getLogInfo(log).getNumberOfEvents());
    			mylog.setTraceNum(myParse.getLogInfo(log).getNumberOfTraces()); */
/*     			session.setAttribute("size", myParse.getLogSize(log));
    			session.setAttribute("trace", myParse.getLogInfo(log).getNumberOfTraces());
    			session.setAttribute("eventN", myParse.getLogInfo(log).getNumberOfEvents());
    			session.setAttribute("eventK", myParse.getLogInfo(log).getNameClasses().size()); */

    			XEventNameClassifier classifier = new XEventNameClassifier();
    			mylog.setLog(1);
    			mylog.setEventKind(myParse.getLogInfo(log).getNameClasses().size());//以事件名划分，若以分类器划分.getEventClasses()
    			mylog.setEventNum(myParse.getLogInfo(log).getNumberOfEvents());
    			mylog.setTraceNum(myParse.getLogInfo(log).getNumberOfTraces());
    			mylog.setActor(myParse.getLogInfo(log).getResourceClasses().size());//获得发起者数量，只计算org:resourse
    			mylog.setStartTime(((XTimeBoundsImpl) myParse.getLogInfo(log).getLogTimeBoundaries()).getStartDate());
    			mylog.setEndTime(((XTimeBoundsImpl) myParse.getLogInfo(log).getLogTimeBoundaries()).getEndDate());
    			
    			/*			HashMap<String, HashMap<String,ActorValue>> tempProcessValue=myPV.mineProcessValue(log);
    			List<String> actorList=new ArrayList<String>();
    			for(String activity: tempProcessValue.keySet()) {
    				
//    				int actorNum=tempProcessValue.get(activity).keySet().size();
//    				System.out.println("activity"+activity+actorNum);
    				for(String actor: tempProcessValue.get(activity).keySet()) {
    					if(!actorList.contains(actor)){
    						actorList.add(actor);
    					}
    				}
    			}
    			System.out.println("actor"+actorList);
    			System.out.println("actorsize"+actorList.size());//加入了customer
    */	//获得总参与者数量

    			
    			Map attr=log.get(0).get(0).getAttributes();
    			
    			Trace.setLog(log);
    			
    			aabean.setAaHash(ActivityActorMining.spvmMiner(out, log));
 				aavbean.setList(aav.pvMiner(log));
				/* lyq.convert(log); */
				IM.setILog(log);
    			
    			//α算法
    			/*
    			double[][] dfa = myPM.createAlphaClassicAbstraction(log, classifier);
    			out.println("&nbsp&nbsp");
    			for(int j1 = 0; j1<dfa.length; j1++)
    			{
    				
    				out.println(String.format("%02d",j1)+"&nbsp");
    			}
    			out.println("<br>");
    			for(int j1 = 0; j1<dfa.length; j1++)
    			{
    				out.println(String.format("%02d",j1));
    				for(int j2 = 0; j2<dfa[i].length; j2++)
    				{
    					out.println(dfa[j1][j2]);
    				}
    				out.println("<br>");
    			}
    			out.println("<br>");
    			//输出各个事件的名字
    			XEventClasses classes = myPM.getXEventClasses(log, classifier);
    			String[] s = myPM.getEventClassName(classes);
     			for(int s1 = 0; s1<s.length; s1++)
    			{
    				out.println(String.format("%02d",s1)+": "+s[s1]);
    				out.println("<br>");
    			} 
    			*/
    			
    			
    			
    	
    			
    			
    			
    			
    		}
    			
    		
    			}catch(Exception ex){
    		out.println("上传文件超过了限制条件，上传失败!<br>");
    		out.println("错误原因：<br>"+ex.toString());
    			}
    			
    			
    		
    	%>
    	
    	<%
    	if(true)
    	{
    		response.sendRedirect("ICES_present_top.html");
    	}
    	%>
  </body> 
</html>
