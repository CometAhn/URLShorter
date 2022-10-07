<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script src="/js/loading.js"></script>
<link href="http://localhost/css/style.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<title>비밀번호 찾기</title>
</head>
<body>
<script type="text/javascript">
	function checkForm() {
		if (grecaptcha.getResponse().length == 0) {
			alert('reCAPTCHA 동의 후 다시 로그인 해주세요.');
			return false;
		}

		if (!document.newMember.email.value) {
			alert("인증번호를 입력하세요.");
			return false;
		}

	 $('#loading').show();
	}
</script>

    <div id="loading"><img id="loading-image" src="/images/loading.gif" alt="Loading..." /></div>
	<jsp:include page="../menu.jsp" />

	<section id="mid">
		<div class="banner">
			<img src="/images/teachers_background.jpg" alt="">
		</div>
		<div class="contents">
			<div class="login">
				<h1>비밀번호 찾기</h1>
	            <br>
				<%
				String error;

				if(request.getAttribute("error")!=null){
				    error = (String) request.getAttribute("error");
				}else{
				    error = null;
				}
				if(error != null){
				  if (error == "0") {
                    out.println("<div class='alert alert-danger'>");
                    out.println("reCAPTCHA 동의 후 다시 시도 해주세요.");
                        out.println("</div>");
                  } else if (error == "1"){
                    out.println("<div class='alert alert-danger'>");
                    out.println("일치하는 정보가 없습니다.");
                        out.println("</div>");
                  } else if (error == "2"){
                    out.println("<div class='alert alert-success'>");
                    out.println("카카오 로그인을 사용하는 이메일입니다.<br>");
                    out.println("카카오 로그인을 이용해주세요.");
                    out.println("</div>");
                    }
                }
				%>
				<form name="newMember" class="form-signin" action="/Lib/findpww" method="post" onsubmit="return checkForm()">
					<h3>아이디</h3>
					<p>
						<input type="text" class="form-control" name="lid" value="${id}">
					</p>
					<h3>이메일</h3>
					<p>
						<input type="email" class="form-control" name="email" value="${email}">
					</p>
					<center>
                        <div class="g-recaptcha" data-sitekey="6LctdVYiAAAAANSXZSeaohuJzWh5SCH6mvjrd-o9"></div>
                        <br>
                    </center>
					<p>
						<button class="btn btn btn-lg btn-success btn-block" type="submit">임시비밀번호 전송</button>
					</p>
				</form>

				<a href="/Lib/loginpage">로그인 </a> / <a href="/Lib/findid"> 아이디 찾기</a>
			</div>
			<center>
				<p>
				    <a href="https://kauth.kakao.com/oauth/authorize?client_id=e6f60f053eb85f6437f9f7e99973580c&redirect_uri=http://localhost/Lib/kakao&response_type=code"><img src="/images/kakao_login_large_narrow.png" alt=""></a>
				</p>
			</center>
		</div>
	</section>
	<jsp:include page="../footer.jsp"/>
</body>
</html>