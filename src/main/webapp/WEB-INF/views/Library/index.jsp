<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<link href="http://localhost/Library/resources/css/style.css" rel="stylesheet">
<title>Home</title>
</head>
<script type="text/javascript">
	function nobook() {
		alert('재고가 없습니다.');
	}
</script>
<%-- 상단 내비게이션 --%>
<%@ include file="./menu.jsp"%>
<!-- 첫번째 섹션 -->
<section id="mid">
	<div class="banner">
		<img src="/Library/resources/img/teachers_background.jpg" alt="">
	</div>
	<div class="contents">
		<h1>환영 합니다</h1>
		<br> <br>
		<h2>이달의 추천 도서</h2>
		<br>
		<div class="booklist">
			<c:forEach var="book" items="${booklist}" varStatus="status">
				<c:if test="${book.library.stock!=0 }">
					<a href="getbook/${book.library.bid}">
						<div class="booklist_img">
							<img src="${book.library.bookCover}" alt="">
							<h3>${book.library.title}</h3>
							<h4>${book.library.writer}</h4>
							<br>
							<p>재고 수 : ${book.library.stock}</p>
						</div>
					</a>
				</c:if>
				<c:if test="${book.library.stock==0 }">
					<a onclick="nobook()">
						<div class="booklist_img">
							<img src="${book.library.bookCover}" alt="">
							<h3>${book.library.title}</h3>
							<h4>${book.library.writer}</h4>
							<br>
							<p>(재고 없음)</p>
						</div>
					</a>
				</c:if>
			</c:forEach>
		</div>
	</div>
</section>
<br>
<%-- footer --%>
<jsp:include page="./footer.jsp" />
</body>
</html>