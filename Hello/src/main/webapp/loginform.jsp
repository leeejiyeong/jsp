<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<div>
			<h1>로그인</h1>
		</div>
		<div>
			<form id="frm" action="Login" onsubmit="return valuecheck()"
				method="post">
				<div>
					<table border="1">
						<tr>
							<th width="150">아이디</th>
							<td width="200"><input type="text" id="name" name="name">
								<!-- id는 자바스크립트, name은 자바에서 쓰는거 --></td>
						</tr>
						<tr>
							<th width="150">패스워드</th>
							<td width="200"><input type="password" id="password"
								name="password"></td>
						</tr>
					</table>
				</div>
				<br> <input type="submit" value="로그인">&nbsp;&nbsp; <input
					type="reset" value="취소">
			</form>
		</div>

		<script type="text/javascript">
			function valuecheck() {
				let name = document.getElementById("name").value;
				let password = frm.password.value;

				if (name == "") {
					alert("이름을 입력하세요!!!!!");
					document.getElementById("name").focus();
					return false;
				}
				if (password == "") {
					alert("password를 입력하세요!!!!!");
					document.getElementById("password").focus();
					return false;
				}
				return true;
			}
		</script>
	</div>
</body>
</html>