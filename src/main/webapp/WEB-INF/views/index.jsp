<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-1N3FJ1ETYL"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());

      gtag('config', 'G-1N3FJ1ETYL');
    </script>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="url shortener">
	<meta property="og:type" content="website">
	<meta property="og:title" content="단축 주소 생성">
	<meta property="og:url" content="ipi.pw">
	<meta property="og:description" content="단축 주소 생성 웹페이지입니다.">
	<meta property="og:image" content="test.png">
    <title>단축 주소 생성 웹</title>
    <link href="http://localhost/css/main.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
    <script src="/js/loading.js"></script>
    <script type="text/javascript">
    	function loading() {
    	 $('#loading').show();
    	}
    </script>
</head>
<body>
    <div id="loading"><img id="loading-image" src="/images/loading.gif" alt="Loading..." /></div>
    <br>
    <center>
    <h1>단축 URL 생성</h1>

    <%
    String shorterurl = (String) request.getAttribute("shorterurl");

    if (shorterurl != null) {
        %>
        <p class="success"><a id ="a" href="<%=shorterurl%>"><%=shorterurl%></a></p>
        <h3><div id="copyok" class="copyok" style="display:none">복사완료</div></h3>
        <input type="submit" id="btn_div_copy" value="복사" class="submit_copy">
        <%
    }

    String error = (String) request.getAttribute("error");

    if (error != null) {
        if (error.equals("0")) {
            %>
            <p class="alert">이미 존재하는 커스텀 주소입니다.</p>
            <%
        } else if (error.equals("1")) {
            %>
            <p class="alert">존재하지 않는 주소입니다.</p>
           <%
        } else if (error.equals("2")) {
            %>
            <p class="alert">URL을 입력해주세요.</p>
            <%
        } else if (error.equals("3")) {
            %>
            <p class="alert">예기치 않는 오류가 발생했습니다.</p>
            <%
        }
    }
    %>

	<form action="/" method="post" onsubmit="return loading()">
        <div class="section group">
            <div class="col span_3_of_3">
                <input type="url" id="input" name="input" autocomplete="off" name="url" class="input" placeholder="Ex)https://www.naver.com" required autofocus>
            </div>
            <div class="col span_1_of_3">
                <input type="text" id="custom" name="custom" autocomplete="off" name="custom" class="input_custom" placeholder="커스텀 주소" readonly>
            </div>
            <div class="col span_2_of_3">
                <div class="onoffswitch">
                    <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch"
                           onclick="toggle()">
                    <label class="onoffswitch-label" for="myonoffswitch"></label>
                </div>
            </div>
        </div>
        <input type="submit" value="Generate" class="submit">
    </form>
    <script>
        function toggle() {
            if (document.getElementById('myonoffswitch').checked) {
                document.getElementById('custom').placeholder = '커스텀 주소를 입력하세요'
                document.getElementById('custom').readOnly = false
                document.getElementById('custom').focus()
            }
            else {
                document.getElementById('custom').value = ''
                document.getElementById('custom').placeholder = '커스텀 주소'
                document.getElementById('custom').readOnly = true
                document.getElementById('custom').blur()
                document.getElementById('input').focus()
            }
        }
        document.getElementById("btn_div_copy").onclick = function () {
            // a 내부 텍스트 취득
            const valOfDIV = document.getElementById("a").innerText;

            // textarea 생성
            const textArea = document.createElement("textarea");

            // textarea 추가
            document.body.appendChild(textArea);

            // textara의 value값으로 div내부 텍스트값 설정
            textArea.value = valOfDIV;

            // textarea 선택 및 복사
            textArea.select();
            document.execCommand("copy");
            //alert("복사완료")
            document.getElementById("copyok").style.display = "block";
            // textarea 제거
            document.body.removeChild(textArea);
        }
    </script>
    </center>
    <br>
    <br>
    <br>
    <br>
    <script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
    <script>
        Kakao.init("deb52fd0e85dcc27c791af5b139b6c29");
        function sendLink() {
            Kakao.Link.sendCustom({
                templateId: 19117,
                templateArgs: {
                    title: "{{ page.title }}",
                    description: "{{ page.excerpt }}",
                    url: "{{ page.url }}",
                },
            });
        }
    </script>
    <h2>
        <a id="kakao-link-btn" href="javascript:sendLink()">
            <img src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png" /></img>
        </a>
    </h2>
</body>
</html>