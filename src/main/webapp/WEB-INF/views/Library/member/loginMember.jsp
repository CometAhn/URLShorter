<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<title>Login</title>
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<%
	String msg = request.getParameter("msg");

	if (msg != null) {
		if (msg.equals("0")) {
	%>
	<script>
	alert('회원가입이 완료되었습니다.');
	</script>
	<%
	}

	}
	%>

	<section id="mid">
		<div class="banner">
			<img src="/Library/resources/img/teachers_background.jpg" alt="">
		</div>
		<div class="contents">
			<div class="login">
				<h1>로그인</h1>
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
                    out.println("계정 생성이 완료되었습니다.");
                        out.println("</div>");
                    } else if (error == "1") {
				    	out.println("<div class='alert alert-danger'>");
				    	out.println("아이디와 비밀번호를 확인해 주세요");
				    	out.println("</div>");
				    } else if(error == "2"){
				    	out.println("<div class='alert alert-danger'>");
				    	out.println("탈퇴한 계정입니다.");
				    	out.println("</div>");
				    } else if(error == "3"){
                     	out.println("<div class='alert alert-danger'>");
                     	out.println("회원 정보가 변경되었습니다.");
                     	out.println("</div>");
                    } else if(error == "4"){
						session.invalidate();
                     	out.println("<div class='alert alert-danger'>");
                     	out.println("회원 탈퇴가 완료되었습니다.");
                     	out.println("</div>");
                    }
                }
				%>
				<form class="form-signin" action="/Lib/login" method="post">
					<h3>아이디</h3>
					<p>
						<input type="text" class="form-control" placeholder="ID" name="id" required autofocus>
					</p>
					<h3>비밀번호</h3>
					<p>
						<input type="password" class="form-control" placeholder="Password" name="password" required>
					</p>
					<br>
					<p>
						<button class="btn btn btn-lg btn-success btn-block" type="submit">로그인</button>
					</p>
				</form>
			</div>
			<center>
				<p>
				    <a href="https://kauth.kakao.com/oauth/authorize?client_id=e6f60f053eb85f6437f9f7e99973580c&redirect_uri=http://localhost/Lib/kakao&response_type=code"><img src="/Library/resources/img/kakao_login_large_narrow.png" alt=""></a>
				</p>
			</center>
		</div>

	</section>
	<jsp:include page="../footer.jsp"/>
</body>
</html>