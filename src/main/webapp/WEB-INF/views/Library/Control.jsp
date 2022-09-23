<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<title></title>
</head>
<body>
	<%
	// 그냥 컨트롤러로서 이용.
	String msg = (String) request.getAttribute("msg");
	if (msg != null) {
		if (msg.equals("0")) {
			// 로그인 관련 부분.
			String id = (String) request.getAttribute("login");
			Boolean grade = (Boolean) request.getAttribute("grade");
			String name = (String) request.getAttribute("name");
			session.setAttribute("sessionId", id);
			session.setAttribute("admin", grade);
			session.setAttribute("name", name);

			response.sendRedirect("/Lib/index");
		} else if (msg.equals("1")) {
			session.setAttribute("alert", "0"); // 등록 완료 alert
			response.sendRedirect("/Lib/list?pagenum=1&items=Title&text=");
		} else if (msg.equals("2")) {
			session.setAttribute("error", request.getAttribute("error")); // 에러 코드 받아오기.
			response.sendRedirect("/Lib/list?pagenum=1&items=Title&text=");
		} else if (msg.equals("3")) {
			session.setAttribute("iderror", "0"); // 가입된 아이디
			response.sendRedirect("/Lib/register");
		}
	} else {
		response.sendRedirect("/Lib/list?pagenum=1&items=Title&text=");
	}
	%>
</body>
</html>