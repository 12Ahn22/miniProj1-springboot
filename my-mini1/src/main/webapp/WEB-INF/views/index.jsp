<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>RATTY | 당신의 작은 반려동물</title>
<jsp:include page="/WEB-INF/views/include/bootStrap.jsp" />
</head>
<body>
	<div class="container">
			<jsp:include page="/WEB-INF/views/include/layoutHeader.jsp" />
			<div style="display: flex; align-items: center; justify-content: center;">
				<img src="https://i.pinimg.com/736x/68/ec/dd/68ecddb392067a4d969690ca127b3f6d.jpg" alt="rat" width="350"/>
			</div>
	</div>
</body>
</html>