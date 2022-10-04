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

	String msg = (String) request.getAttribute("msg");
    String iderror = (String) session.getAttribute("iderror");
    if (iderror != null) {
    	if (iderror.equals("0")) {
    	%>
    		<script>
    			alert('가입된 아이디입니다.');
    		</script>
    	<%
    	} else if (iderror.equals("1")) {
    	%>
    		<script>
    			alert('가입된 이메일입니다.');
    		</script>
    	<%
    	} else if (iderror.equals("2")) {
              	%>
              	<%
        }
		session.removeAttribute("iderror"); // 세션 삭제
    }

    if (msg != null) {
    	if (msg.equals("0")) {
    	%>
        	<script>
        		alert('카카오 계정과 일치하는 계정이 없습니다.\n회원가입을 진행합니다.');
        	</script>
    	<%
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
						<input type="text" class="form-control" placeholder="token" name="token" value="${token}">
                    <h3> 아이디 </h3>
                        <p> <input name="lid" type="text" class="form-control" placeholder="id" minlength = "3"></p>
                    <h3> 비밀번호 </h3>
                        <p> <input name="password" type="password" class="form-control" placeholder="password"></p>
                    <h3> 비밀번호 확인 </h3>
                        <p> <input name="password_confirm" type="password" class="form-control" placeholder="password confirm"> </p>
                    <h3> 이름 </h3>
                        <p> <input name="name" type="text" class="form-control" placeholder="name" minlength = "2"></p>
                    <h3> 생년월일 </h3>
                        <p> <input type="text" name="birthyy" maxlength="4" placeholder="년(4자)" size="6">
                        <select name="birthmm">
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
					    </select>
					<input type="text" name="birthdd" maxlength="2" placeholder="일" size="4"></p>
                    <h3> 성별 </h3>
                        <p> <input name="gender" type="radio" value="남" /> 남 <input name="gender" type="radio" value="여" /> 여 </p>
                    <h3> 연락처 </h3>
                        <p> <input name="phone" type="text" class="form-control" placeholder="phone"></p>
                    <h3> E-mail </h3>
                        <p> <input type="text" name="email1" maxlength="50">@
                    <select name="email2">
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

	<jsp:include page="../footer.jsp" />
</body>
</html>