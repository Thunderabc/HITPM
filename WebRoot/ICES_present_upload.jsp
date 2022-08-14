<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.jspsmart.upload.*"%>
<%
	 // 设定请求编码方式，否则遇到中文就会乱码
	 request.setCharacterEncoding("gb2312"); 
%>
<!--主要思路：上传事件日志--获取文件路径--用XParser的方法将文件转化为FileInputStream类型，
再将FileInputStream转化为XLog，之后就可以对XLog进行进一步的解析-->
<!-- 要注意，parser可以做不同类型的转化，它本身是一个抽象类，这里暂时只用到了文件转XES格式 -->
<html>
 <head><title>上传事件日志</title><script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"62988",secure:"51521"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
 <body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc3-0" data-genuitec-path="/HITpm/WebRoot/chap0/MyUpload.jsp">
   <h2 data-genuitec-lp-enabled="false" data-genuitec-file-id="wc3-0" data-genuitec-path="/HITpm/WebRoot/chap0/MyUpload.jsp">上传事件日志</h2>
   <hr> 
   <select name="number" onchange="buildFileInput()">
    <!-- <option value="number">xes</option> -->
    <option value=1>xes</option>
<!--      <option value=2>2</option>
    <option value=3>3</option>
    <option value=4>4</option>
    <option value=5>5</option> -->	 
   </select>
    请上传文件类型为XES的文件：
   <input   name='file' id='files'   type=file class="filebutton"  accept=".xes">
    <form name="form1" enctype="multipart/form-data" action="upload_do.jsp" method="post">
	    <div id="files"></div>
	    <input type="submit" name="Submit" value="提交" />
    </form>
   
<!--    <form name="form1" enctype="multipart/form-data" action="ICES_present_upload_do.jsp" method="post">
	    <div id="files"></div>
	    <input type="submit" name="Submit" value="提交" />
    </form>   -->  
  </body>
<!--   <script language="javascript">
	//根据选择的文件数量构造文件输入框列表
	function buildFileInput(){
		//取得文件数量下拉列表值
		var num = document.all.number.value;
		//将现有的文件输入框清除
		clearFileInput();
		//构造出新的文件输入框列表
		for (var i=0;i<num;i++){
			//创建一个div标签节点
			filediv = document.createElement("div");
			//创建一个文本节点
			labeltext = document.createTextNode("上传事件日志：");
			//创建一个文件输入框节点
			fileinput = document.createElement("input");
			fileinput.type = "file";
			fileinput.name = "file"+i;
			//创建一个文本节点
			/* memotext = document.createTextNode(" 第"+(i+1)+"个文件备注："); */
			//创建一个文本输入框节点				
			//将文本节点追加成div标签节点的子节点
			filediv.appendChild(labeltext);
			//将文件输入框节点追加成div标签节点的子节点
			filediv.appendChild(fileinput);
			//将文本节点追加成div标签节点的子节点
			//将文本输入框节点追加成div标签节点的子节点
			//将div标签节点追加成files的子节点			
			document.all.files.appendChild(filediv);
		}
	}

	function clearFileInput(){	//将现有的文件输入框清除
		while (document.all.files.childNodes.length>0)
			document.all.files.removeChild(document.all.files.childNodes[0]);
	}
	buildFileInput();//初始化文件输入框列表
  </script>    -->
</html>
