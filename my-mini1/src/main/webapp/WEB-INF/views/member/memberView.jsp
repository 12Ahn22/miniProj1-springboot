<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html lang="ko">

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>RATTY | 회원 상세정보</title>
			<jsp:include page="/WEB-INF/views/include/bootStrap.jsp" />
		</head>

		<body>
			<div class="container">
				<jsp:include page="/WEB-INF/views/include/layoutHeader.jsp" />
				<main>
					<input type="hidden" id="memberId" value="${member.id}" />
					<h1>${member.id}</h1>
					<div><span>이름:</span><span>${member.name}</span></div>
					<div><span>성별:</span><span>${member.gender}</span></div>
					<div><span>전화번호:</span><span>${member.phone}</span></div>
					<div><span>주소:</span><span>${member.address}</span></div>
					<div><span>취미:</span>
						<c:forEach var="hobby" items="${member.hobbies}">
							<span>${hobby.hobby}</span>
						</c:forEach>
					</div>
					<div>
						<a class="btn btn-primary" href="updateForm?id=${member.id}">수정</a> <button class="btn btn-secondary" id="deleteBtn">삭제</button>
					</div>
				</main>
			</div>
			<script>
				const deleteBtn = document.getElementById("deleteBtn");
				const memberId = document.getElementById("memberId");

				deleteBtn.addEventListener("click", () => {
					if (confirm("정말 삭제하시겠습니까?")) {
						const param = {
							id: memberId.value,
							action: "delete"
						}
						fetch("delete", {
							method: "POST",
							body: JSON.stringify(param),
							headers: { "Content-type": "application/json; charset=utf-8" }
						}).then((res) => res.json())
							.then((data) => {
							if (data.status === 204) {
								alert("회원 정보삭제에 성공했습니다.");
								// 페이지 리다이렉트
								location = "list";
							} else {
								alert(data.statusMessage);
							}
						})
					}
				});
			</script>
		</body>

		</html>