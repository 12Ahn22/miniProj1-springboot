<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>RATTY | 게시글 수정</title>
<jsp:include page="/WEB-INF/views/include/bootStrap.jsp" />
</head>
<body>
	<div class="container">
		<jsp:include page="/WEB-INF/views/include/layoutHeader.jsp" />
		<main>
			<h2>게시물 수정</h2>
			<form id="uForm">
				<input type="hidden" name="bno" id="bno" value="${board.bno}">
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="author" id="author" value="${board.author}">
				<label for="title">제목:</label><br>
				<input type="text" id="title" name="title" value="${board.title}"><br>
				<label for="content">내용:</label><br>
				<textarea id="content" name="content" rows="4" cols="50">${board.content}</textarea><br><br>
				<input class="btn btn-primary" type="submit" value="수정">
				<a class="btn btn-secondary" href="board?action=view&bno=${board.bno}">취소</a>
		</form>
	</div>
	</main>
	<script>
		const uForm = document.getElementById("uForm");
		uForm.addEventListener("submit",(e)=>{
			e.preventDefault();
			const param = {
				bno:bno.value,
				action:"update",
				author:author.value,
				title:title.value,
				content:content.value
			};

			fetch("update", {
								method: "POST",
								body: JSON.stringify(param),
								headers: { "Content-type": "application/json; charset=utf-8" }
							}).then((res) => res.json())
								.then((data) => {
								if (data.status === 204) {
									alert("게시글 수정에 성공했습니다.");
									// 페이지 리다이렉트
									location = "view?bno=" + bno.value;
								} else {
									alert(data.statusMessage);
								}
							});
		});
	</script>
</body>
</html>