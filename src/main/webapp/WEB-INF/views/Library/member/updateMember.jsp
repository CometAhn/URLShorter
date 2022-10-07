<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script src="/js/loading.js"></script>
<link href="http://localhost/css/style.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<title>회원 수정</title>
</head>
<div id="loading"><img id="loading-image" src="/images/loading.gif" alt="Loading..." /></div>
<%

String msg = (String) request.getAttribute("msg");
    if (msg != null) {
    	if (msg.equals("0")) {
        %>
        <script>
        	alert('반납하지 않은 책이 존재합니다.\n확인 후 다시 시도해주세요.');
        </script>
        <%
        } else if (msg.equals("1")) {
        %>
        <script>
        	alert('reCAPTCHA 동의 후 다시 시도 해주세요.');
        </script>
        <%
        }
    }
%>
<body onload="init()">
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
	<jsp:include page="../menu.jsp" />
	<section id="mid">
		<div class="banner">
			<img src="/images/teachers_background.jpg" alt="">
		</div>
		<div class="contents">
			<div class="signup">
				<h1>회원 정보 수정</h1>
				<br>
				<div class="signup_ct">
					<form name="newMember" class="form-horizontal" action="/Lib/update" method="post" onsubmit="return checkForm()" autocomplete="off">
						<input name="lid" type="text" class="form-control" placeholder="id" hidden="" value="${login.lid}">
						<h3>비밀번호</h3>
						<p>
							<input name="password" type="password" class="form-control" placeholder="password" value="${login.password}" minlength="6" maxlength="16">
						</p>
						<h3>비밀번호 확인</h3>
						<p>
							<input name="password_confirm" type="password" class="form-control" placeholder="password_confirm">
						</p>
						<h3>이름</h3>
						<p>
							<input name="name" type="text" class="form-control" placeholder="name" value="${login.name}">
						</p>
						<h3>생년월일</h3>
						<p>
							<input type="text" name="birthyy" maxlength="4" placeholder="년(4자)" size="6" value="${birthyy}"> <select name="birthmm" id="birthmm">
								<option value="">월</option>
								<option value="01">1</option>
								<option value="02">2</option>
								<option value="03">3</option>
								<option value="04">4</option>
								<option value="05">5</option>
								<option value="06">6</option>
								<option value="07">7</option>
								<option value="08">8</option>
								<option value="09">9</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
							</select> <input type="text" name="birthdd" maxlength="2" placeholder="일" size="4" value="${birthdd}">
						</p>
						<h3>성별</h3>
						<p>
							<c:set var="gender" value="${row.gender}" />
							<input name="gender" type="radio" value="남" <c:if test="${login.gender.equals('남')}"> <c:out value="checked" /> </c:if>>남 <input name="gender" type="radio" value="여" <c:if test="${login.gender.equals('여')}"> <c:out value="checked" /> </c:if>>여
						</p>
						<h3>연락처</h3>
						<p>
							<input name="phone" type="text" class="form-control" placeholder="01000000000" value="${login.phone}" minlength="11" maxlength="11">
						</p>

						<h3>E-mail</h3>
						<p>
							<input type="text" name="email1" maxlength="50" value="${email1}">@ <select name="email2" id="mail2">
								<option>naver.com</option>
								<option>daum.net</option>
								<option>gmail.com</option>
								<option>nate.com</option>
							</select>
						</p>
						<h3>주소</h3>
						<p>
							<input name="address" type="text" class="form-control" placeholder="address" value="${login.address}">
						</p>
						<center>
                            <div class="g-recaptcha" data-sitekey="6LctdVYiAAAAANSXZSeaohuJzWh5SCH6mvjrd-o9"></div>
                        </center>
						<br> <input type="submit" class="btn btn-primary" value="회원수정 ">
					</form>
					<br>
					<form name="newMember" class="form-horizontal" action="/Lib/delete" method="post">
						<input name="id" type="text" class="form-control" placeholder="id" hidden="" value="${login.lid}"> <input type="submit" class="btn btn-danger" value="회원탈퇴 ">
					</form>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="../footer.jsp" />
</body>
</html>
<script type="text/javascript">
	function init() {
		setComboMailValue("${email2}");
		setComboBirthValue("${birthmm}");
	}
	function setComboMailValue(val) {
		var selectMail = document.getElementById('mail2');
		for (i = 0, j = selectMail.length; i < j; i++) {
			if (selectMail.options[i].value == val) {
				selectMail.options[i].selected = true;
				break;
			}
		}
	}
	function setComboBirthValue(val) {
		var selectBirth = document.getElementById('birthmm');
		for (i = 0, j = selectBirth.length; i < j; i++) {
			if (selectBirth.options[i].value == val) {
				selectBirth.options[i].selected = true;
				break;
			}
		}
	}
	function checkForm() {
		if (grecaptcha.getResponse().length == 0) {
        	alert('reCAPTCHA를 확인해 주세요.');
        	return false;
        }
		if (!document.newMember.id.value) {
			alert("아이디를 입력하세요.");
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