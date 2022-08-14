<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="org.deckfour.xes.model.XLog"%>

<jsp:useBean id="myParse" class="hitpm_v2.ICES_beans_xml.XML3"/>
<jsp:useBean id="aav" class="hitpm_v2.ICES_beans_activityActorValue.AavTable"/>
<jsp:useBean id="aavbean" class="hitpm_v2.ICES_beans_activityActorValue.Myaavbean" scope="session"/>
<html>
  <head>

    
    <title>My JSP 'aavTable.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  <script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"62988",secure:"51521"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
  
  <body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc3-10" data-genuitec-path="/HITpm/WebRoot/pmResults/aavTable.jsp">
    
    <%
    	List<Map> list=aavbean.getList();
    	System.out.println(list);
     %>
    
	<table border="1" style="width: 100%; " data-genuitec-lp-enabled="false" data-genuitec-file-id="wc3-10" data-genuitec-path="/HITpm/WebRoot/pmResults/aavTable.jsp"><!-- 600px -->
	<caption style="font-size: 25px; " >活动-参与者-价值</caption>
		<tr>
		  <td>活动</td>
		  <!-- <td style="width: 15%; ">&nbsp;</td> -->
		  <td>参与者</td>
		  <!-- <td style="width: 15%; ">&nbsp;</td> -->
		  <td>输入价值</td>
		  <!-- <td style="width: 15%; ">&nbsp;</td> -->
		  <td>输出价值</td>
		  <!-- <td style="width: 15%; ">&nbsp;</td> -->
<!-- 		  <td rowspan="5" style="width: 100px; " align="center">照片</td> -->
		  <!-- 注意：为了避免这个空的单元格的边框没有被显示，在空单元格中添加一个空格占位符，就可以将边框显示出来。 -->
		</tr>
		<%
		try {
/* 		    String filePathName="D:\\PycharmProjects\\pythonProject\\pm4py\\mygraduation\\xes\\Simple1211新.xes";
			java.io.FileInputStream file1 = myParse.convert(filePathName);
			XLog log = myParse.getLog(file1); */
			
			
		 aav.setList(list);
		 String activity;
			for (int j = 0; j < list.size(); j++) {
				out.println("<tr>");
                // 得到要插入的每一条记录
                Map dataMap = list.get(j);

                
                if (dataMap.containsKey("activity")){
                	int actorNum=aav.getActorNum(j);
//                	System.out.println(actorNum);
					activity=activity = dataMap.get("activity").toString();
                	out.println("<td rowspan=\""+ actorNum+"\" style=\"width: 100px; \" align=\"center\">" + activity +"</td>");
//   		         out.println("<td>"+ activity +"</td>");
                	for(int i=j+1;i<=j+actorNum;i++){
                		Map dataMap1 = list.get(i);
                		String actor = dataMap1.get("actor").toString();
                		String invalue = dataMap1.get("invalue").toString();	
                		String outvalue = dataMap1.get("outvalue").toString();
                		if(!(i==j+1))
                			out.println("<tr>");

	    		        out.println("<td>"+ actor +"</td>");
	    		        out.println("<td>"+ invalue + "</td>");
	    		        out.println("<td>"+ outvalue +"</td>");
	    		        out.println("</tr>");	
                	}
                	
                }
//                else{
//                	String actor = dataMap.get("actor").toString();
//                    String invalue = dataMap.get("invalue").toString();	
//                    String outvalue = dataMap.get("outvalue").toString();
//                }
              }
//    String id,sp_Name,sp_Price,buy_Num,buy_Count;
//    while (rs.next()){
//      id = rs.getString("id").trim();
//      sp_Name = rs.getString("sp_Name").trim();
//      sp_Price = rs.getString("sp_Price").trim();
//      buy_Num = rs.getString("buy_Num").trim();
//      buy_Count = rs.getString("buy_Count").trim();
//      String sp_No=rs.getString("sp_No").trim();
//      out.println("<tr>");
//      out.println("<td>"+ sp_Name +"</td>");
//      out.println("<td>"+ sp_Price +"</td>");
//      out.println("<td><input type=text value="+ buy_Num + " onChange=\"updateNum('"+id+"',this.value,"+ buy_Num +",'"+sp_Price+"','"+sp_No+"')\"></td>");
//      out.println("<td>"+ buy_Count +"</td>");
//      out.println("<td><a href='buy.jsp?op=del&id="+id+"&sp_No="+sp_No+"&num0="+buy_Num+"'>退回商品架</a></td>");
//      out.println("</tr>");
//    }
	       
	       		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		%>	

	</table>
  </body>
</html>
