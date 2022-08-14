<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- <jsp:useBean id="spvm" class="spvm.Graph2BPMN"/> --%>

<html data-genuitec-lp-enabled="false" data-genuitec-file-id="wc3-13" data-genuitec-path="/HITpm/WebRoot/test2.jsp">
<html lang="zh-CN">
	<head> 
 		<meta charset="UTF-8">  
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"/>  
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />  
		<title>BPMNJS</title>    
		<link rel="stylesheet" href="https://unpkg.com/bpmn-js@7.3.0/dist/assets/diagram-js.css" />  
		<link rel="stylesheet" href="https://unpkg.com/bpmn-js@7.3.0/dist/assets/bpmn-font/css/bpmn.css" />
		<%-- <script url=<%=spvm.getURL()+"Str.js"%>></script> --%>
		<script src=".\Model.js"></script>
		
	<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"62988",secure:"51521"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body>  
	<div id="canvas" style="height:80vh;"></div>    

	<script src="https://unpkg.com/bpmn-js@7.3.0/dist/bpmn-modeler.development.js"></script>  
	<!-- <script src="./AutoLayout.js"></script> -->
	

	<script>    
		var bpmnModeler = new BpmnJS({      
			container: '#canvas'  
			  
		});    

  		
		bpmnModeler.importXML(Str, function(err) { 
		     
			if (!err) {

                // è®©å¾è½èªéåºå±å¹
                var canvas = bpmnJS.get('canvas')

              	canvas.zoom('fit-viewport')

              	
            }else{        
				return console.error('failed to load diagram', err);      
			}    
		});



	/* var autoLayout = new AutoLayout(); */


</script>

</body>
</html>