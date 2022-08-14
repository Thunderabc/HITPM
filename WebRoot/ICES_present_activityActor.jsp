<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ page import="java.util.*"%>

<!DOCTYPE html>
<jsp:useBean id="aabean" class="hitpm_v2.ICES_beans_activityActor.Myaabean" scope="session"/>

<html>

<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"62988",secure:"51521"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
<body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc3-9" data-genuitec-path="/HITpm/WebRoot/pmResults/aa.jsp">
	<%
		HashMap<String, ArrayList<String>> dataMap = aabean.getAaHash();
	
	%>
		<table border="1" style="width: 100%; " data-genuitec-lp-enabled="false" data-genuitec-file-id="wc3-9" data-genuitec-path="/HITpm/WebRoot/pmResults/aa.jsp"><!-- 600px -->
		<caption style="font-size: 25px; " >活动-参与者</caption>
		<tr>
		  <th>活动</th>
		  <!-- <td style="width: 15%; ">&nbsp;</td> -->
		  <th>参与者</th>
		  <!-- <td style="width: 15%; ">&nbsp;</td> -->
		</tr>
		<%
		
	    for (String activity : dataMap.keySet()) {
	    	int temp=0;
	    	ArrayList<String> actorList = dataMap.get(activity);
	    	int size = actorList.size();
	    	for (String actor : actorList)
	    	{
	    		out.println("<tr>");
	    		if(temp==0)
	    		{
	    			out.println("<th rowspan="+size+">"+ activity +"</td>");
	    		}
	         	
	         	out.println("<td>"+ actor +"</td>");
	         	out.println("</tr>");
	         	temp=1;
	    	}
            /* System.out.println("key: " + i + " value: " + dataMap.get(activity)); */
        }
        
		%>
		
		
<%-- 	<div>
    <c:forEach items="${dataMap}" var="dataMap"> 
        <c:set var="activity" value='${dataMap[\"key\"]}' />
        <c:forEach items="${dataMap.get(key)}" var="actor"> 
		<table border="1">
		<tr>
		<td>${dataMap.key}</td>
		</tr>
		</table>    
        <!-- </c:forEach>  -->                
    </c:forEach>
	</div> --%>
<!-- 		<table border="1">
		<tr>
		<td>aa, cell 1</td>
		<td>row 1, cell 2</td>
		</tr>
		<tr>
		<td>row 2, cell 1</td>
		<td>row 2, cell 2</td>
		</tr>
		</table> -->
	
	<%System.out.println(aabean.getAaHash()); %>
</body>
</html>