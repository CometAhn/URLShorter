<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String sessionId = (String) session.getAttribute("sessionId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<title>뉴스 관리 앱</title>
</head>
<body>
	<%
	String msg = (String) request.getAttribute("msg");

	if (msg != null) {
		if (msg.equals("0")) {
	%>
	<script>
		alert('이미 장바구니에 추가된 책입니다.');
		location.href = "/Lib/getbook/${bid}";
	</script>
	<%
	//response.sendRedirect("lib?action=getBook&id=" + bid);
	} else if (msg.equals("1")) {
	%>
	<script>
		alert('장바구니에 추가했습니다.');
		location.href = "/Lib/getbook/${bid}";
	</script>
	<%
	} else if (msg.equals("2")) {
	%>
	<script>
		alert('책 대여 후 반납하지 않은 책은 장바구니에 담을 수 없습니다.');
		location.href = "/Lib/getbook/${bid}";
	</script>
	<%
	} else if (msg.equals("3")) {
	%>
	<script>
		alert('리뷰가 변경되었습니다.');
		location.href = "/Lib/getbook/${bid}";
	</script>
	<%
	} else if (msg.equals("4")) {
	%>
	<script>
		alert('리뷰를 삭제했습니다.');
		location.href = "/Lib/getbook/${bid}";
	</script>
	<%
	}
	}
	%>



	<!-- 상단 메뉴 -->
	<jsp:include page="./menu.jsp" />
	<script type="text/javascript">
		function addtocart() {
			if (confirm("상품을 장바구니에 추가 하시겠습니까?")) {
		    location.href = "/Lib/addcarttocart?id=<%=sessionId%>&bid=${book.bid}";
			} else {

			}
		}
		function addtocart1() {
			if (confirm("상품을 장바구니에 추가 하시겠습니까?")) {
		    location.href = "/Lib/addcart?id=<%=sessionId%>&bid=${book.bid}";
			} else {

			}
		}
    	function loginfirst() {
		    alert('로그인 후 이용가능합니다.');
		    location.href = "/Lib/loginpage";
    	}
	</script>

	<section id="mid">
		<div class="banner">
			<img src="/Library/resources/img/teachers_background.jpg" alt="">
		</div>
	</section>

	<div class="container w-75 mt-5 mx-auto">
		<hr>
		<div class="card w-75 mx-auto">
			<img class="card-img-top" src="${book.bookCover}">
			<div class="card-body">
				<h4 class="card-title">책 제목 : ${book.title}</h4>
				<p class="card-text">책 내용 : ${book.description}</p>
				<p class="card-text">분류 : ${book.category}</p>
				<p class="card-text">출판사 : ${book.publisher}</p>
				<p class="card-text">재고수 : ${book.stock}</p>
			</div>
			<form name="addform" action="./addcart.jsp?id=${book.bid}" method="post">
				<% if (sessionId != null) {
				    %>
				<a class="btn btn-info" onclick="addtocart()">책 대여 &raquo;</a>
				 <a class="btn btn-warning" onclick="addtocart1()">장바구니 &raquo;</a>
				    <% } else { %>
				<a class="btn btn-info" onclick="loginfirst()">책 대여 &raquo;</a>
				 <a class="btn btn-warning" onclick="loginfirst()">장바구니 &raquo;</a>
				 <% } %>

				  <a href="/Lib/list?pagenum=1&items=Title&text=" class="btn btn-secondary">도서 목록 &raquo;</a> <a href="javascript:history.back()" " class="btn btn-primary"><< Back</a>
			</form>
		</div>
		<hr>
	</div>

	<div class="container w-75 mt-5 mx-auto">
		<h1>Review</h1>
		<c:forEach var="review" items="${reviewlist}" varStatus="status">
			<div class="card w-75 mx-auto">
				<div class="card-body">
					<h4 class="card-title">제목 : ${review.title}</h4>
					<p class="card-text">${review.contents}</p>
					<p class="card-text">평점 : ${review.score}</p>
					<c:if test="${review.login.lid == sessionId}">
						<a href="/Lib/delreview/${review.loan.id}" class="btn btn-danger">삭제</a>
						<a class="btn btn-outline-info" type="button" data-bs-toggle="collapse" data-bs-target="#addForm${review.id}" aria-expanded="false" aria-controls="addForm">리뷰 수정</a>
						<!-- 리뷰 수정 기능 -->
						<div class="collapse" id="addForm${review.id}">
							<div class="card card-body">
								<form action="/Lib/upreview" method="post" enctype="multipart/form-data">
									<input type="text" name="id" class="form-control" value="${review.id}" hidden> <input type="text" name="library.bid" class="form-control" value="${book.bid}" hidden> <label class="form-label"> 제목 </label> <input type="text" name="title" class="form-control" value="${review.title}"> <label class="form-label">리뷰 내용</label>
									<textarea rows="5" cols="50" name="contents" class="form-control">${review.contents}</textarea>
									<label class="form-label"> 평점 </label> <input type="text" name="score" class="form-control" value="${review.score}">
									<!-- 평점 추가 해야함 -->
									<button type="submit" class="btn btn-success mt-3">수정</button>
								</form>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</c:forEach>
	</div>

	<!-- 하단 메뉴 -->
	<jsp:include page="./footer.jsp" />
</body>
</html>