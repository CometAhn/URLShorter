<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="http://localhost/css/style.css" rel="stylesheet">
<%
String sessionId = (String) session.getAttribute("sessionId");
String token = (String) session.getAttribute("token");
String name = (String) session.getAttribute("name");
%>
<!-- 상단 메뉴 -->
<nav id="navbar">
	<div class="container1">
		<div class="itlib">
			<i class="fas fa-book-open fa-2x"></i>
			<h3>아이티 도서관</h3>
		</div>
		<div class="menu">
			<ul class="menubar">
				<li><a href="/Lib/index"> Home </a></li>
				<li><a href="/Lib/list?pagenum=1&items=Title&text="> 도서 목록 </a></li>

				<c:choose>
					<c:when test="${empty sessionId}">
						<li><a href="/Lib/loginpage"> 장바구니 </a></li>
						<li><a href="/Lib/loginpage"> 대여 목록 </a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/Lib/listcart?id=<%=sessionId%>"> 장바구니 </a></li>
						<li><a href="/Lib/listloan?id=<%=sessionId%>"> 대여 목록 </a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="/Lib/map"> 도서관 안내 </a></li>
				<li><a href="/Lib/rank"> 순위 </a></li>
			</ul>
		</div>

		<c:choose>
			<c:when test="${empty sessionId}">
				<div class="menu1">
					<a href="/Lib/loginpage"> 로그인 </a>
				</div>
				<div class="menu1">
					<a href="/Lib/register"> 회원 가입 </a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="menu1">
                    <c:if test="${token eq '0'}">
					    <a href="/Lib/logout"> 로그아웃 </a>
					</c:if>
                    <c:if test="${token ne '0'}">
					    <a href="https://kauth.kakao.com/oauth/logout?client_id=e6f60f053eb85f6437f9f7e99973580c&logout_redirect_uri=http://localhost/Lib/klogout"> 로그아웃 </a>
					</c:if>
				</div>
				<div class="menu1">
					<form name="newMember" action="/Lib/edit" method="post">
						<input name="id" type="text" hidden="" value="<%=sessionId%>"> <input type="submit" class="registersubmit" value="회원수정">
					</form>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</nav>