<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="http://localhost/Library/resources/css/style.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>

<title>회원 가입</title>
</head>
<body>
	<jsp:include page="../menu.jsp" />

	<section id="mid">
		<div class="banner">
			<img src="/Library/resources/img/teachers_background.jpg" alt="">
		</div>
    <p>인가 코드 : ${code}</p>
    <p>유효 토큰 : ${access_token}</p>
    <p>사용자 정보 : ${userInfo}</p>
    <p>동의 정보 : ${agreementInfo}</p>
    <a href="https://kauth.kakao.com/oauth/logout?client_id=e6f60f053eb85f6437f9f7e99973580c&logout_redirect_uri=http://localhost/Lib/klogout">로그아웃</a>
    </section>

	<jsp:include page="../footer.jsp" />
</body>
</html>