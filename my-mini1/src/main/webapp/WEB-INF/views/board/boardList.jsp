<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>RATTY | 게시글 목록</title>
    <jsp:include page="/WEB-INF/views/include/bootStrap.jsp" />
  </head>
  <body>
    <div class="container">
      <jsp:include page="/WEB-INF/views/include/layoutHeader.jsp" />
      <main>
        <h1>게시물 리스트</h1>
        <a class="btn btn-primary mb-2" href="insertForm">새 글 작성하기</a>
        <form id="searchForm" method="get" action="list">
        	<select id="size" name="size" >
				<c:forEach var="size" items="${sizes}">
        			<option value="${size.codeid}" ${pageRequestVO.size == size.codeid ? 'selected' : ''} >${size.name}</option>
        		</c:forEach>
        	</select>
          <input
            type="text"
            name="searchKey"
            id="searchKey"
            placeholder="Search..."
          />
          <input type="submit" value="검색" />
        </form>
        <table class="table">
          <thead>
            <tr>
              <th scope="col">no</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
              <th scope="col">조회수</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="board" items="${pageResponseVO.list}">
              <tr>
                <td>${board.bno}</td>
                <td><a data-bs-toggle="modal" data-bs-toggle="modal" data-bs-target="#boardViewModel" data-bs-bno="${board.bno}">${board.title}</a></td>
                <td>${board.author}</td>
                <td>${board.createdAt}</td>
                <td>${board.viewCount}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
        <!--  페이지 네비게이션 바 출력  -->
        <div class="float-end">
          <ul class="pagination flex-wrap">
            <c:if test="${pageResponseVO.prev}">
              <li class="page-item">
                <a class="page-link" data-num="${pageResponseVO.start -1}"
                  >이전</a
                >
              </li>
            </c:if>

            <c:forEach
              begin="${pageResponseVO.start}"
              end="${pageResponseVO.end}"
              var="num"
            >
              <li
                class="page-item ${pageResponseVO.pageNo == num ? 'active':''}"
              >
                <a class="page-link" data-num="${num}">${num}</a>
              </li>
            </c:forEach>

            <c:if test="${pageResponseVO.next}">
              <li class="page-item">
                <a class="page-link" data-num="${pageResponseVO.end + 1}"
                  >다음</a
                >
              </li>
            </c:if>
          </ul>
        </div>
      </main>
    </div>
    
    <!-- 상세보기 Modal -->
	<div class="modal fade" id="boardViewModel" role="dialog">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="staticBackdropLabel">게시물 상세보기</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
		      <label>게시물 번호:</label><span id="bno"></span><br/>
		      <label>제목 : </label><span id="title"></span><br/>
		      <label>내용 : </label><span id="content"></span><br/>
		      <label>ViewCount :</label><span id="viewCount"></span><br/>
		      <label>작성자 : </label><span id="author"></span><br/>
		      <label>작성일 : </label><span id="createdAt"></span><br/>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	      </div>
	    </div>
	  </div>
	</div>
  </body>
  <script>
    document
      .querySelector('.pagination')
      .addEventListener('click', function (e) {
        e.preventDefault();

        const target = e.target;

        if (target.tagName !== 'A') {
          return;
        }
        //dataset 프로퍼티로 접근 또는 속성 접근 메서드 getAttribute() 사용 하여 접근 가능
        //const num = target.getAttribute("data-num")
        const num = target.dataset['num'];
        const size = document.getElementById("size").value;
        location = `?pageNo=\${num}&size=\${size}`;
      });

    document.querySelector('#size').addEventListener('change', (e) => {
      searchForm.submit();
    });

    const searchForm = document.getElementById('searchForm');
    searchForm.addEventListener('submit', (e) => {
      e.preventDefault();
      searchForm.submit();
    });

		const url = new URL(window.location.href);
		const urlParams = url.searchParams;
		if(urlParams.get("searchKey")){
			searchKey.value = urlParams.get("searchKey");
		}
		
	const boardViewModel = document.querySelector("#boardViewModel");
	const span_bno = document.querySelector(".modal-body #bno");
	const span_title = document.querySelector(".modal-body #title");
	const span_content = document.querySelector(".modal-body #content");
	const span_viewCount = document.querySelector(".modal-body #viewCount");
	const span_author= document.querySelector(".modal-body #author");
	const span_createdAt = document.querySelector(".modal-body #createdAt");
	
	boardViewModel.addEventListener('shown.bs.modal', function (event) {
		const a = event.relatedTarget;
		const bno = a.getAttribute('data-bs-bno');
		console.log("모달 대화 상자 출력... bno ", bno);
		
		span_bno.innerText = "";
		span_title.innerText = "";
		span_content.innerText = "";
		span_viewCount.innerText = "";
		span_author.innerText = "";
		span_createdAt.innerText = "";
		
		// 요청
		fetch(`jsonView?bno=\${bno}`, {
			method: "GET",
			headers: { "Content-type": "application/json; charset=utf-8" }
		}).then((res) => res.json())
			.then((data) => {
			if (data.status === 204) {
				//성공
				const board = data.board; 
				span_bno.innerText = board.bno;
				span_title.innerText = board.title;
				span_content.innerText = board.content;
				span_viewCount.innerText = board.viewCount;
				span_author.innerText = board.author;
				span_createdAt.innerText = board.createdAt;
				
			} else {
				alert(data.statusMessage);
			}
		});
		
	})
  </script>
</html>
