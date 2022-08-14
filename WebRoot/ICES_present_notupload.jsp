<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>未登录提示</title>
<% String path = request.getContextPath(); String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; %>
 
<script type="text/javascript"> alert("亲，您还没有上传文件，请上传后访问！"); window.location.href="<%=path%>"+"/html/ICES_present_top.html"; </script>
</head>
<body>
</body>
</html>
1
2
3
4
5
6
7
8
9
10
11
12
13
