<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<jsp:useBean id="mylog" class="hitpm_v2.ICES_beans_processLog.ICES_beans_logInfo" scope="session"/>

<head><title>处理上传的事件日志</title></head>
  <body>
  
	<%
	int log = mylog.getLog();
	int traceNum = mylog.getTraceNum();
	int eventNum = mylog.getEventNum();
	int eventKind = mylog.getEventKind();
	int actor=mylog.getActor();
	String startTime=mylog.getStartTimeStr();
	String endTime=mylog.getEndTimeStr();
	%>
	<table border="1" style="width: 100%; "  target="aframe">
	  <tr>
	    <th>关键数据</th>
	  </tr>
	  <tr>
	    <td>流程：<%out.print(log);%>
	    </td>
	  </tr>
	  	  <tr>
	    <td>实例：<%out.print(traceNum);%>
	    </td>
	  </tr>
	  	  <tr>
	    <td>事件：<%out.print(eventNum);%>
	    </td>
	  </tr>
	  	  <tr>
	    <td>事件种类：<%out.print(eventKind);%>
	    </td>
	  </tr>
	  	  <tr>
	    <td>参与者：<%out.print(actor);%>
	    </td>
	  </tr>
	  	  <tr>
	    <td>开始时间：<%out.print(startTime);%>
	    </td>
	  </tr>
	  	  <tr>
	    <td>结束时间：<%out.print(endTime);%>
	    </td>
	  </tr>
	 </table>
</body>
</html>