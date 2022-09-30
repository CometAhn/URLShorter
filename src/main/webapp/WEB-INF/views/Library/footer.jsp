<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String sessionId = (String) session.getAttribute("sessionId");
String name = (String) session.getAttribute("name");
%>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
    Kakao.init("372e9be4c85d8e5f3b182ea4e17070ae");
    function sendLink() {
      Kakao.Link.sendCustom({
        templateId: 83300,
        templateArgs: {
          title: "{{ page.title }}",
          description: "{{ page.excerpt }}",
          url : "{{ page.url }}",
        },
      });
    }
</script>
<section id="bottom">
	<div>
		<div class="bott">
			<div class="bot">
				<div class="bottext">
					<h5>Quick Link</h5>
					<div class="bottextsize">
						<div>
							<div class="quick">
								<p>
									<a href="/Lib/index"><i class="fas fa-angle-right"></i> Home</a>
								</p>
							</div>
							<div class="quick">
								<p>
									<a href="/Lib/list?pagenum=1&items=Title&text="><i class="fas fa-angle-right"></i> 도서 목록</a>
								</p>
							</div>
							<c:choose>
								<c:when test="${empty sessionId}">
									<div class="quick">
										<p>
											<a href="/Lib/loginpage"><i class="fas fa-angle-right"></i></i> 장바구니 </a>
										</p>
									</div>
									<div class="quick">
										<p>
											<a href="/Lib/loginpage"><i class="fas fa-angle-right"></i></i> 대여 목록 </a>
										</p>
									</div>
								</c:when>
								<c:otherwise>
									<div class="quick">
										<p>
											<a href="/Lib/listcart?id=<%=sessionId%>"><i class="fas fa-angle-right"></i> 장바구니 </a>
										</p>
									</div>
									<div class="quick">
										<p>
											<a href="/Lib/listloan?id=<%=sessionId%>"><i class="fas fa-angle-right"></i> 대여 목록 </a>
										</p>
									</div>
								</c:otherwise>
							</c:choose>
							<div class="quick">
								<p>
									<a href="/Lib/map"><i class="fas fa-angle-right"></i> 도서관 안내 </a>
								</p>
							</div>
						</div>
					</div>
				</div>
				<div class="bottext">
					<h5>Contact</h5>
					<div class="bottextsize">
						<i class="fas fa-map-marker-alt"></i> 경남 창원시 마산동 12-3<br> <i class="fas fa-phone-alt"></i> 055-123-1234 <br> <i class="fas fa-envelope"></i> it@naver.com <br>
					</div>
					<div class="conticon">
						<a href=""><i class="fab fa-twitter fa-lg"></i></a> <a href=""><i class="fab fa-facebook-f fa-lg"></i></a> <a href=""><i class="fab fa-youtube fa-lg"></i></a> <a href=""><i class="fab fa-linkedin-in fa-lg"></i></a>
					</div>
					<div class="conticon">
							<li>
                                    <a id="kakao-link-btn" href="javascript:sendLink()">
                                     <img src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png" /></img>
                                     </a>
                            </li>
                    </div>
				</div>
			</div>
			<hr>
			<div class="end">
				<div class="endd">
					<div class="endtext">
						<div class="enda">
							<p>
								© Your Site Name, All Right Reserved. Designed By HTML Codex <br> <br>
							<p>Distributed By ThemeWagon
						</div>
						<div class="endb">
							<div class="endmenu">
								<li><a href="">Home</a></li>
								<li><a href="">Cookies</a></li>
								<li><a href="">Help</a></li>
								<li><a href="">FAQs</a></li>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>