<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script src="/js/loading.js"></script>
<link href="http://localhost/css/style.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<title>Login</title>
</head>
<body>
<script type="text/javascript">
	function checkForm() {
		if (!document.newMember.emailkey.value) {
			alert("인증번호를 입력하세요.");
			return false;
		}
	 $('#loading').show();
	}
</script>

    <div id="loading"><img id="loading-image" src="/images/Spin-1s-200px.gif" alt="Loading..." /></div>
	<jsp:include page="../menu.jsp" />

	<section id="mid">
		<div class="banner">
			<img src="/images/teachers_background.jpg" alt="">
		</div>
		<div class="contents">
			<div class="login">
				<h1>이메일 인증</h1>
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
                    out.println("메일함을 확인해주세요.");
                        out.println("</div>");
                  }else if (error == "1"){
                    out.println("<div class='alert alert-danger'>");
                    out.println("인증번호가 알맞지 않습니다.");
                        out.println("</div>");

                  }
                }
				%>
				<form name="newMember" class="form-signin" action="/Lib/emailcheck" method="post" onsubmit="return checkForm()">
					<p>
						<input type="text" class="form-control" name="id" value="${id}" hidden>
					</p>
					<h3>이메일</h3>
					<p>
						<input type="text" class="form-control" value="${email}" disabled>
					</p>
					<h3>인증번호</h3>
					<p>
						<input type="text" class="form-control" name="emailkey" required>
					</p>
					<br>
					<p>
						<button class="btn btn btn-lg btn-success btn-block" type="submit">확인</button>
					</p>
				</form>
			</div>
		</div>
	</section>
	<jsp:include page="../footer.jsp"/>
</body>
</html>