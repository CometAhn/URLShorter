<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="url shortener">
	<meta property="og:type" content="website">
	<meta property="og:title" content="단축 주소 생성">
	<meta property="og:url" content="ipi.pw">
	<meta property="og:description" content="단축 주소 생성 웹페이지입니다.">
	<meta property="og:image" content="test.png">
    <title><?php echo SITE_NAME; ?></title>
    <link href="http://localhost/css/main.css" rel="stylesheet">
</head>
<body>
<script>
		 document.getElementById("btn_div_copy").onclick = function()
		 {
        // a 내부 텍스트 취득
        const valOfDIV = document.getElementById("a").innerText;

        // textarea 생성
        const textArea = document.createElement('textarea');

        // textarea 추가
        document.body.appendChild(textArea);

        // textara의 value값으로 div내부 텍스트값 설정
        textArea.value = valOfDIV;

        // textarea 선택 및 복사
        textArea.select();
        document.execCommand('copy');
		//alert("복사완료")
		document.getElementById('copyok').style.display='block';
        // textarea 제거
        document.body.removeChild(textArea);
    }
	</script>
<br>
<center>
    <h1>단축 URL 생성</h1>
	<form action="/add" method="post">
        <div class="section group">
            <div class="col span_3_of_3">
                <input type="url" id="input" name="input" autocomplete="off" name="url" class="input" placeholder="Ex)https://www.naver.com">
            </div>
            <div class="col span_1_of_3">
                <input type="text" id="custom" name="custom" autocomplete="off" name="custom" class="input_custom" placeholder="커스텀 주소"
                       readonly>
            </div>
            <div class="col span_2_of_3">
                <div class="onoffswitch">
                    <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch"
                           onclick="toggle()">
                    <label class="onoffswitch-label" for="myonoffswitch"></label>
                </div>
            </div>
        </div>
        <input type="submit" value="만들기" class="submit">
    </form>
    <script>
      function toggle () {
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
    </script>

</body>
</html>

</center>
<br>
<br>
<br>
<br>
<left>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
    Kakao.init("deb52fd0e85dcc27c791af5b139b6c29");
    function sendLink() {
      Kakao.Link.sendCustom({
        templateId: 19117,
        templateArgs: {
          title: "{{ page.title }}",
          description: "{{ page.excerpt }}",
          url : "{{ page.url }}",
        },
      });
    }
</script>
<left>
<h2>
<a id="kakao-link-btn" href="javascript:sendLink()">
<img src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png" /></img>
</a>
</h2>