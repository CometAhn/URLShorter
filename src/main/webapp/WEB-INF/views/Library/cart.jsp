<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String sessionId = (String) session.getAttribute("sessionId");
int count = (int) request.getAttribute("count");
int i = 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="http://localhost/Library/resources/css/style.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<link href="http://localhost/Lib/Library/resources/css/style.css" rel="stylesheet">
<title>장바구니</title>
</head>
<body>
	<script type="text/javascript">
		function manybookincart() {
			alert('최대 3권까지 대여할 수 있습니다.');
		}
		function nobook() {
			alert('장바구니에 책이 존재하지 않습니다.');
		}
		function manybooksum() {
			alert('장바구니의 책과 빌린 책의 수의 합이 4권이 넘습니다.\n빌린 후 반납하지 않은 책의 수는 <%=count%>
		권입니다.');
		}
	</script>
	<%
	String msg = (String) request.getAttribute("msg");

	if (msg != null) {
		if (msg.equals("0")) {
	%>
	<script>
		alert('재고 수량이 없는 책이 포함되어 있습니다.\n확인 후 다시 시도해주세요.');
		location.href = "/Lib/listcart?id=${sessionId}";
	</script>
	<%
	} else if (msg.equals("1")) {
	%>
	<script>
		alert('반납 기한을 초과하여 도서 대여가 불가능합니다.\n ${overdue} 이후 도서 대여가 가능합니다.');
	</script>
	<%
	}
	}
	%>
	<jsp:include page="./menu.jsp" />
	<!-- 첫번째 섹션 -->
	<section id="mid">
		<div class="banner">
			<img src="/Library/resources/img/teachers_background.jpg" alt="">
		</div>
		<div class="contents">
			<div class="ct1">
				<h1>장바구니 목록</h1>
			</div>
			<div class="cartlist">
				<div class="cartlist_info">
					<div class="cartlist_abc">
						<p>책 제목(저자)</p>
					</div>
					<div class="cartlist_abc">
						<p>재고 수</p>
					</div>
					<div class="cartlist_abc">
						<p>삭제</p>
					</div>
				</div>
				<c:forEach var="book" items="${booklist}" varStatus="status">
					<div class="cartlist_a">
						<div class="cartlist_abc">
							<p>${book.title}(${book.writer })</p>
						</div>
						<div class="cartlist_abc">
							<p>${book.stock }</p>
						</div>
						<div class="cartlist_abc">
							<a href="/Lib/deleteCart?bid=${book.bid}&id=<%=sessionId%>"> <span class="badge bg-secondary">&times;</span></a>
						</div>
					</div>
					<%
					i++;
					%>
				</c:forEach>
			</div>
			<div class="cart_button">
				<%
				if (i == 0) {
				%><td align="right"><a href="#" class="btn btn-danger" onclick="nobook()">대여하기</a></td>
				<%
				} else if (i > 3) {
				%><td align="right"><a href="#" class="btn btn-danger" onclick="manybookincart()">대여하기</a></td>
				<%
				} else if (i + count > 3) {
				%><td align="right"><a href="#" class="btn btn-danger" onclick="manybooksum()">대여하기</a></td>
				<%
				} else {
				%><td align="right"><a href="/Lib/loan?id=<%=sessionId%>" class="btn btn-primary">대여하기</a></td>
				<%
				}
				%>
			</div>
		</div>
	</section>
	<jsp:include page="./footer.jsp" />
</body>
</html>