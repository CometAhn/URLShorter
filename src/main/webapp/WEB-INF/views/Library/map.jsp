<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script src="/js/loading.js"></script>
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<link href="http://localhost/css/style.css" rel="stylesheet">
<title>Map</title>
</head>
<body>
<div id="loading"><img id="loading-image" src="/images/loading.gif" alt="Loading..." /></div>
	<jsp:include page="./menu.jsp" />

	<section id="mid">
		<div class="banner">
			<img src="/images/teachers_background.jpg" alt="">
		</div>
		<div class="contents">
			<div class="lib_info">
				<h1>도서관 안내</h1>
				<div class="map">
					<div class="map_1">
						<h3>도서관 위치</h3>
						<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1629.4933461709093!2d128.58319310059574!3d35.23170130097478!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x356f32161a07d2b5%3A0xd8e276b01df1f6e4!2z7JWE7J207Yuw7JeQ65OA64S37ZWZ7JuQ!5e0!3m2!1sko!2skr!4v1663147825611!5m2!1sko!2skr" width="600" height="450" style="border: 0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
					</div>
					<div class="map_2">
						<div class="map_text">
							<div class="map_text1">
								<p>전화번호 : 055-123-1234</p>
							</div>
						</div>
						<div class="map_text">
							<div class="map_text1">
								<p>주소 : 경남 창원시 마산길 12-3</p>
							</div>
						</div>
						<div class="map_text">
							<div class="map_text1">
								<p>운영 시간 : 09:00 ~ 18:00</p>
							</div>
						</div>
						<div class="map_text">
							<div class="map_text1">
								<p>문의 사항 : it@naver.com</p>
							</div>
						</div>
						<br>
					</div>
				</div>
			</div>
		</div>
	</section>

	<jsp:include page="./footer.jsp" />
</body>
</html>
