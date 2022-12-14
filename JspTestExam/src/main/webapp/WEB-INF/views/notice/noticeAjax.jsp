<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>noticeAjax.jsp</title>
<script src="js/table.js"></script>
<script src="js/noticeAjax.js"></script>
</head>

<body>
	<h2>Ajax연습</h2>

	<div>
		<form name="ajaxFrm" action="" enctype="multipart/form-data">
			<table>
				<tr>
					<th><label for="writer">작성자</label></th>
					<td><input type="text" name="writer" id="writer" value="홍길동"></td>
				</tr>
				<tr>
					<th><label for="title">제목</label></th>
					<td><input type="text" name="title" id="title" value="Ajax연습"></td>
				</tr>
				<tr>
					<th><label for="subject">내용</label></th>
					<td><textarea cols="30" rows="3" name="subject" id="subject">Ajax 연습중입니다</textarea></td>
				</tr>
				<tr>
					<th><label for="file">첨부파일</label></th>
					<td><input type="file" name="nfile" id="nfile"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="저장"></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="show"></div>

</body>

</html>