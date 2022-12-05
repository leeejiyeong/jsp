<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%=request.getParameter("name")%>
	<br>
	<%=request.getParameter("password")%>
	<br> 사용자 명 : ${param.name }
	<br> 패스워드 : ${param.password }
</body>
</html>