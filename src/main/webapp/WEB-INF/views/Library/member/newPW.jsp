<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script src="/js/loading.js"></script>
<link href="http://localhost/css/style.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<title>비밀번호 변경</title>
</head>
<body>
<script type="text/javascript">
	function checkForm() {
		if (grecaptcha.getResponse().length == 0) {
			alert('reCAPTCHA 동의 후 다시 로그인 해주세요.');
			return false;
		}
		if (!document.newMember.password.value) {
 			alert("비밀번호를 입력하세요.");
			return false;
		}
		if (document.newMember.password.value != document.newMember.password_confirm.value) {
			alert("비밀번호를 동일하게 입력하세요.");
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
				<h1>비밀번호 변경</h1>
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
                    out.println("계정 정보를 불러올 수 없습니다.<br>비밀번호 찾기 재시도해주세요.");
                    out.println("</div>");
                  } else if (error == "2"){
                    out.println("<div class='alert alert-success'>");
                    out.println("비밀번호를 변경해 주세요.");
                    out.println("</div>");
                  }
                }
				%>
				<form name="newMember" class="form-signin" action="/Lib/newpw" method="post" onsubmit="return checkForm()" autocomplete="off">
				    <input name="lid" type="text" class="form-control" value="${id}" hidden>
                    <h3>새 비밀번호</h3>
                        <p> <input name="password" type="password" class="form-control" placeholder="password" value="${pw}" minlength="6" maxlength="16"></p>
                    <h3>비밀번호 확인</h3>
                        <p> <input name="password_confirm" type="password" class="form-control" placeholder="password confirm" value="${pw}"> </p>
					<center>
                        <div class="g-recaptcha" data-sitekey="6LctdVYiAAAAANSXZSeaohuJzWh5SCH6mvjrd-o9"></div>
                    </center>
                        <br>
					<p>
						<button class="btn btn btn-lg btn-success btn-block" type="submit">비밀번호 변경</button>
					</p>
				</form>
			</div>
		</div>
	</section>
	<jsp:include page="../footer.jsp"/>
</body>
</html>