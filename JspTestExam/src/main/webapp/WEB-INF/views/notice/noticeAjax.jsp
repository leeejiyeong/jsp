<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>noticeAjax.jsp</title>
<link rel="stylesheet" href="css/modal.css">
<script src="js/table.js"></script>
<script src="js/noticeAjaxFetch.js"></script>
</head>

<body>
	<h2>Ajax연습</h2>

	<div>
		<form name="ajaxFrm" action="noticeInsert.do" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="noticeDate" value="2022-12-16">
			<table>
				<tr>
					<th><label for="writer">작성자</label></th>
					<td><input type="text" name="noticeWriter" id="writer"
						value="홍길동"></td>
				</tr>
				<tr>
					<th><label for="title">제목</label></th>
					<td><input type="text" name="noticeTitle" id="title"
						value="Ajax연습"></td>
				</tr>
				<tr>
					<th><label for="subject">내용</label></th>
					<td><textarea cols="30" rows="3" name="noticeSubject"
							id="subject">Ajax 연습중입니다</textarea></td>
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

	<!-- ============================ -->
	<div id="id01" class="modal" style="display: none">

		<form class="modal-content animate" action="/action_page.php" method="post">
			<div class="imgcontainer">
				<span onclick="document.getElementById('id01').style.display='none'"
					class="close" title="Close Modal">&times;</span> 
					<img src="" alt="Avatar" class="avatar">
			</div>

			<div class="container">
				<label for="writer"><b>작성자</b></label> 
					<input type="text" placeholder="Enter Username" name="writer" required> 
					
				<label for="title"><b>제목</b></label> 
					<input type="text" placeholder="Enter Password" name="title" required>
				
				<label for="date"><b>작성일자</b></label> 
					<input type="date" placeholder="Enter Username" name="noticeDate" required><br>
				
				<label for="subject"><b>내용</b></label> 
					<textarea  cols="30" rows="5" placeholder="Enter Username" name="subject" required> </textarea>
					
				<button type="submit">수정</button>
				<label> <input type="hidden" name="noticeId"></label>
			</div>

			<div class="container" style="background-color: #f1f1f1">
				<button type="button"
					onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
			</div>
		</form>
	</div>
	<!-- ========================= -->
</body>

</html>