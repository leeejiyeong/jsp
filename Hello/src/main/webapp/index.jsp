<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>WelCome to JSP</h1>
	<%!String message = "자바코드로 작성한 것";%>
	<%
	for (int i = 0; i < 10; i++) {
	%>
	${1+1}
	<br>
	<%
	}
	%>
	<%=message%>
	<br> ${message }

	<br>
	<a href="FirstServlet">서블릿 호출</a>
	<br>
	<a href="login.jsp?name=홍길동&password=1234">로그인</a>
	<br>
	<a href="Login?name=홍길동&password=1234">로그인서블릿</a>
	<br>
	<a href="loginform.jsp">로그인폼호출하기</a>
</body>
</html>