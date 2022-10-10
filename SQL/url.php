<?php
    session_start();
    require_once("./config.php");
?>
<?php
//If the HTTPS is not found to be "on"
if(!isset($_SERVER["HTTPS"]) || $_SERVER["HTTPS"] != "on")
{
    //Tell the browser to redirect to the HTTPS URL.
    header("Location: https://" . $_SERVER["HTTP_HOST"] . $_SERVER["REQUEST_URI"], true, 301);
    //Prevent the rest of the script from executing.
    exit;
}
?>
<!DOCTYPE html>
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
    <link rel="stylesheet" href="assets/css/main.css">
</head>
<body>

<br>
<center>
    <h1><?php echo SITE_NAME; ?></h1>
    <?php
    if (isset($_SESSION['success'])) {
		echo "	<script>
		function copy_to_clipboard() {
		alert(\"스크립트부터되는지 테스트\")
		}
	</script>";

        echo "<p class='success'>" . $_SESSION['success'] . "</p>";
		echo "<h3><div id='copyok' class=\"copyok\" style='display:none'>복사완료</div></h3>";
		echo "\t<input type=\"submit\" id=\"btn_div_copy\" value=\"복사\" class=\"submit_copy\">";
		echo "	<script>
    //function copy_to_clipboard1(){
		 document.getElementById(\"btn_div_copy\").onclick = function()
		 {
        // a 내부 텍스트 취득
        const valOfDIV = document.getElementById(\"a\").innerText;

        // textarea 생성
        const textArea = document.createElement('textarea');

        // textarea 추가
        document.body.appendChild(textArea);
        
        // textara의 value값으로 div내부 텍스트값 설정
        textArea.value = valOfDIV;

        // textarea 선택 및 복사
        textArea.select();
        document.execCommand('copy');
		//alert(\"복사완료\")
		document.getElementById('copyok').style.display='block';
        // textarea 제거
        document.body.removeChild(textArea);
    }
	</script>";
        unset($_SESSION['success']);
    }
    if (isset($_SESSION['error'])) {
        echo "<p class='alert'>" . $_SESSION['error'] . "</p>";
        unset($_SESSION['error']);
    }
    if (isset($_GET['error']) && $_GET['error'] == 'db') {
        echo "<p class='alert'>데이터베이스 연결 오류!</p>";
    }
    if (isset($_GET['error']) && $_GET['error'] == 'inurl') {
        echo "<p class='alert'>올바른 URL이 아닙니다!</p>";
    }
    if (isset($_GET['error']) && $_GET['error'] == 'dnp') {
        echo "<p class='alert'>등록된 주소가 아닙니다. 다시 입력해주세요.</p>";
    }
    ?>
	<form method="POST" action="functions/shorten">
        <div class="section group">
            <div class="col span_3_of_3">
                <input type="url" id="input" autocomplete="off" name="url" class="input" placeholder="Ex)https://www.naver.com">
            </div>
            <div class="col span_1_of_3">
                <input type="text" id="custom" autocomplete="off" name="custom" class="input_custom" placeholder="커스텀 주소"
                       disabled>
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
          document.getElementById('custom').disabled = false
          document.getElementById('custom').focus()
        }
        else {
          document.getElementById('custom').value = ''
          document.getElementById('custom').placeholder = '커스텀 주소'
          document.getElementById('custom').disabled = true
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