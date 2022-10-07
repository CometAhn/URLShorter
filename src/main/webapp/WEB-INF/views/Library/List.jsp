<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
// 세션 못 받아오면 false, 받아오면 받아온 값.
boolean admin;
int total_record;
int pagenum;
int total_page;
String items;
String text;

if (session.getAttribute("admin") != null) {
	admin = (Boolean) session.getAttribute("admin");
} else {
	admin = false;
}

// 파라미터를 저장하지 않고 리턴을 list로 했을 때 오류.
// booklist는 못 가져와서 값이 없지만, 일단 500 error는 안 뜸.
// 땜빵용
if (request.getAttribute("total_record") != null) {
	total_record = ((Integer) request.getAttribute("total_record")).intValue();
} else {
	total_record = 10;
}
if (request.getAttribute("pagenum") != null) {
	pagenum = ((Integer) request.getAttribute("pagenum")).intValue();
} else {
	pagenum = 1;
}
if (request.getAttribute("total_page") != null) {
	total_page = ((Integer) request.getAttribute("total_page")).intValue();
} else {
	total_page = 2;
}
if (request.getAttribute("items") != null) {
	items = (String) request.getAttribute("items");
} else {
	items = "";
}
if (request.getAttribute("text") != null) {
	text = (String) request.getAttribute("text");
} else {
	text = "Title";
}

// 대충 게시글 넘버링
// 현재 안 쓰는 중. 지우기 귀찮으니 냅둠!
int i = (pagenum * 5) - 4;
%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script src="/js/loading.js"></script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="http://localhost/css/style.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<title>도서 목록</title>
</head>
<body>
<div id="loading"><img id="loading-image" src="/images/loading.gif" alt="Loading..." /></div>
	<script type="text/javascript">
		function nobook() {
			alert('재고가 없습니다.');
		}
	</script>
	<%
	String alert = (String) session.getAttribute("alert");
	if (alert != null) {
		if (alert.equals("0")) {
	%>
	<script>
		alert('도서를 등록했습니다.');
	</script>
	<%
	session.removeAttribute("alert"); // 세션 삭제
	}
	}
	%>
	<!-- 상단 메뉴 -->
	<jsp:include page="./menu.jsp" />
	<!-- 첫번째 섹션 -->
	<section id="mid">
		<div class="banner">
			<img src="/images/teachers_background.jpg" alt="">
		</div>
		<div class="contents">
			<div class="ct1">
				<h1>도서 목록</h1>
			</div>
			<!-- 에러 출력부 -->
			<c:if test="${error != null }">
				<div class="alert alert-danger alert-dismissible fade show mt-3">
					<center>
						<button type="button" class="btn-close" data-bs-dismiss="alert">${error}</button>
					</center>
				</div>
			</c:if>
			<%
			session.removeAttribute("error");
			%>
			<div class="booklist">
				<c:forEach var="book" items="${booklist}" varStatus="status" begin="${spage}" end="${epage}" >
					<c:if test="${book.stock!=0 }">
						<div class="booklist_img">
				            <a href="getbook/${book.bid}">
								<img src="${book.bookCover}" alt="">
								<h3>${book.title}</h3>
								<h4>${book.writer}</h4>
								<br>
								<p>재고 수 : ${book.stock}</p>
					        </a>
							<% if (admin == true) { %>
						        <a href="/Lib/delete/${book.bid}" class="btn btn-info">삭제</a>
						    <% } %>
						</div>
					</c:if>
					<c:if test="${book.stock==0 }">
						<div class="booklist_img">
						    <a onclick="nobook()">
								<img src="${book.bookCover}" alt="">
								<h3>${book.title}</h3>
								<h4>${book.writer}</h4>
								<br>
								<p>(재고 없음)</p>
						    </a>
							<% if (admin == true) { %>
						        <a href="/Lib/delete/${book.bid}" class="btn btn-info">삭제</a>
						    <% } %>
						</div>
					</c:if>
				</c:forEach>
			</div>
			<div class="booklist_search">
				<c:set var="pagenum" value="<%=pagenum%>" />
				<c:forEach var="i" begin="1" end="<%=total_page%>">
					<a href="list?pagenum=${i}
			<%if(items != null && text != null) {%>
			&items=<%=items%>&text=<%=text %>
			<%} %>"> <c:choose>
							<c:when test="${pagenum==i}">
								<font color='4C5317'><b> [${i}]</b></font>
							</c:when>
							<c:otherwise>
								<font color='4C5317'> [${i}]</font>
							</c:otherwise>
						</c:choose>
					</a>
				</c:forEach>
			</div>
			<br>
			<div class="booklist_search">
				<form action="/Lib/list" method="get">
					<p>
						<input name="pagenum" type="text" value="1" hidden /> <select name="items" class="txt">
							<option value="Title">책 제목</option>>
							<option value="Writer">글쓴이</option>
							<option value="Category">카테고리</option>
							<option value="Publisher">출판사</option>
						</select>
						<input name="text" type="text" />
						<input type="submit" id="btnAdd" class="btn btn-primary" value="검색" />
					</p>
				</form>
			</div>
		</div>
	</section>
	<%-- 등록하는거. 안 꾸밈! --%>
	<%
	if (admin == true) {
	%>
	<!-- 책 등록 양식 -->
	<button class="btn btn-outline-info mb-3" type="button" data-bs-toggle="collapse" data-bs-target="#addForm" aria-expanded="false" aria-controls="addForm">책 등록</button>
	<div class="collapse" id="addForm">
		<div class="card card-body">
			<form action="/Lib/add" method="post" enctype="multipart/form-data">
				<label class="form-label">제목</label>
				<input type="text" name="title" class="form-control"><label class="form-label">
				글쓴이</label>
				<input type="text" name="writer" class="form-control">
				<label class="form-label">책 내용</label>
				<textarea rows="5" cols="50" name="description" class="form-control"></textarea>
				<label class="form-label">분류</label>
				<input type="text" name="category" class="form-control">
				<label class="form-label">출판사</label>
				<input type="text" name="publisher" class="form-control">
				<label class="form-label">수량</label>
				<input type="text" name="stock" class="form-control">
				<label class="form-label">이미지</label>
				<input type="file" name="file" class="form-control">
				<button type="submit" class="btn btn-success mt-3">저장</button>
			</form>
		</div>
	</div>
	<%
	}
	%>
	<jsp:include page="./footer.jsp" />
</body>
</html>

