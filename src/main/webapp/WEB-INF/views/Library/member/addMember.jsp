<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="http://localhost/Library/resources/css/style.css" rel="stylesheet">
<script src="https://kit.fontawesome.com/e561738355.js" crossorigin="anonymous"></script>
<script type="text/javascript">
	function checkForm() {
		if (!document.newMember.lid.value) {
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
	}
</script>
<title>회원 가입</title>
</head>
<body>
		<%
    	String iderror = (String) session.getAttribute("iderror");
    	if (iderror != null) {
    		if (iderror.equals("0")) {
    		%>
    			<script>
    				alert('가입된 아이디 입니다.');
    			</script>
    		<%
			session.removeAttribute("iderror"); // 세션 삭제
    		}
    	}
    	%>

	<jsp:include page="../menu.jsp" />

	<section id="mid">
        <div class="banner">
			<img src="/Library/resources/img/teachers_background.jpg" alt="">
        </div>
        <div class="contents">
            <div class="signup">
                <h1> 회원 가입 </h1> <br>
                <div class="signup_ct">
                <form name="newMember" class="form-horizontal" action="/Lib/regist" method="post" onsubmit="return checkForm()">
                        <h3> 아이디 </h3>
                            <p> <input name="lid" type="text" class="form-control" placeholder="id"></p>
                        <h3> 비밀번호 </h3>
                            <p> <input name="password" type="text" class="form-control" placeholder="password"></p>
                        <h3> 비밀번호 확인 </h3>
                            <p> <input name="password_confirm" type="text" class="form-control" placeholder="password confirm"> </p>
                        <h3> 이름 </h3>
                            <p> <input name="name" type="text" class="form-control" placeholder="name"></p>
                        <h3> 생년월일 </h3>
                            <p> <input type="text" name="birthyy" maxlength="4" placeholder="년(4자)" size="6"> <select name="birthmm">
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
					</select> <input type="text" name="birthdd" maxlength="2" placeholder="일" size="4"></p>
                        <h3> 성별 </h3>
                            <p> <input name="gender" type="radio" value="남" /> 남 <input name="gender" type="radio" value="여" /> 여 </p>
                        <h3> 연락처 </h3>
                            <p> <input name="phone" type="text" class="form-control" placeholder="phone"></p>

                        <h3> E-mail </h3>
                            <p> <input type="text" name="mail1" maxlength="50">@ <select name="mail2">
						<option>naver.com</option>
						<option>daum.net</option>
						<option>gmail.com</option>
						<option>nate.com</option>
					</select>
                            </p>
                        <h3> 주소 </h3>
                            <p> <input name="address" type="text" class="form-control" placeholder="address"></p>

                        <p > <input type="submit" class="btn btn-primary " value="등록">
                            <input type="reset" class="btn btn-primary " value="취소 " onclick="reset()"></p>
                    </form>
                </div>
            </div>
        </div>
    </section>


</body>
</html>