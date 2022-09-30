<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
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
<link href="http://localhost/Library/resources/css/style.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<title>장바구니</title>
</head>
<body>
	<jsp:include page="./menu.jsp" />
	<!-- 첫번째 섹션 -->
	<section id="mid">
		<div class="banner">
			<img src="/Library/resources/img/teachers_background.jpg" alt="">
		</div>
		<div class="contents">
			<div class="ct1">
				<h1>리뷰 TOP 3</h1>
			</div>
			<div class="cartlist">
				<div class="cartlist_info">
					<div class="cartlist_abc">
						<p>등 수</p>
					</div>
					<div class="cartlist_abc">
						<p>아이디(이름)</p>
					</div>
					<div class="cartlist_abc">
						<p>작성한 리뷰 수</p>
					</div>
				</div>
				<c:forEach var="RC" items="${RClist}" varStatus="status">
					<div class="cartlist_a">
						<div class="cartlist_abc">
							<p>${status.count}</p>
						</div>
						<div class="cartlist_abc">
							<p>${RC.lid}(${RC.name})</p>
						</div>
						<div class="cartlist_abc">
							<p>${RC.reviewCount}</p>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="ct1">
				<h1>대여 TOP 3</h1>
			</div>
			<div class="cartlist">
				<div class="cartlist_info">
					<div class="cartlist_abc">
						<p>등 수</p>
					</div>
					<div class="cartlist_abc">
						<p>아이디(이름)</p>
					</div>
					<div class="cartlist_abc">
						<p>대여한 누적 도서 수</p>
					</div>
				</div>
				<c:forEach var="LC" items="${LClist}" varStatus="status">
					<div class="cartlist_a">
						<div class="cartlist_abc">
							<p>${status.count}</p>
						</div>
						<div class="cartlist_abc">
							<p>${LC.lid}(${LC.name})</p>
						</div>
						<div class="cartlist_abc">
							<p>${LC.loanCount}</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
	<jsp:include page="./footer.jsp" />
</body>
</html>