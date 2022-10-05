package library;

import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import library.DAO.*;
import library.Entity.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/Lib")
public class LibraryController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); // 로거 선언

	@Value("${lib.imgdir}")
	String fdir;
	static final int LISTCOUNT = 9;
	final LibraryDAO dao;
	final LoginDAO daoG;
	final CartDAO daoC;
	final ReviewDAO daoR;
	final RecommendDAO daoRc;
	final LoanDAO daoL;

	@Autowired
	KaKaoService ks;
	@Autowired
	reCAPTCHA re;

	@Autowired
	public LibraryController(LibraryDAO dao, LoginDAO daoG, CartDAO daoC, ReviewDAO daoR, RecommendDAO daoRc, LoanDAO daoL) {
		this.dao = dao;
		this.daoG = daoG;
		this.daoC = daoC;
		this.daoR = daoR;
		this.daoRc = daoRc;
		this.daoL = daoL;
	}

	String controller = "Library/Control";

	@PostMapping("/add")
	public String addBook(@ModelAttribute Library library, Model m, @RequestParam("file") MultipartFile file) {
		try {
			// 저장 파일 객체 생성
			File dest = new File(fdir + file.getOriginalFilename());

			// 파일 저장
			file.transferTo(dest);

			// library 객체에 파일 이름 저장
			library.setBookCover(dest.getName());
			dao.addBook(library);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 추가 과정에서 문제 발생!!");
			m.addAttribute("error", "책이 정상적으로 등록되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}
		m.addAttribute("msg", "1");
		return controller;
	}

	// getAll 메서드 구현
	@GetMapping("/list")
	public String listbook(@RequestParam int pagenum, @RequestParam String items, @RequestParam String text, Model m) {
		List<Library> list;
		int limit = LISTCOUNT;
		int spage;
		int epage;
		int total_record = dao.getlistcount(items, text);

		// System.out.println("토탈 값 가져오니? " + total_record);

		int total_page;

		if (total_record % limit == 0) {
			total_page = total_record / limit;
			Math.floor(total_page);
		} else {
			total_page = total_record / limit;
			Math.floor(total_page);
			total_page = total_page + 1;
		}

		try {
			list = dao.getAll(pagenum, limit, items, text);

			// 페이지 넘버링 야매 ㄱ
			spage = 0 + (pagenum - 1) + ((pagenum - 1) * 8);
			epage = 8 + (pagenum - 1) + ((pagenum - 1) * 8);

			m.addAttribute("booklist", list);
			m.addAttribute("total_record", total_record);
			m.addAttribute("pagenum", pagenum);
			m.addAttribute("total_page", total_page);
			m.addAttribute("items", items);
			m.addAttribute("text", text);
			m.addAttribute("spage", spage);
			m.addAttribute("epage", epage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "책 목록 생성이 정상적으로 처리되지 않았습니다!!");
		}
		return "Library/List";
	}

	// getBook() 메서드 구현
	@GetMapping("/getbook/{id}")
	public String getBook(@PathVariable int id, Model m) {
		try {
			Library n = dao.getBook(id);
			m.addAttribute("book", n);

			List<Review> list = daoR.getReview(id);
			m.addAttribute("reviewlist", list);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("책 정보를 가져오는 과정에서 문제 발생!!");
			m.addAttribute("error", "책 정보를 정상적으로 가져오지 못했습니다!!");
		}
		return "Library/View";
	}

	// 책정보 삭제
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id, Model m) {
		try {
			dao.delBook(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 삭제 과정에서 문제 발생!!");
			m.addAttribute("error", "책 정보를 정상적으로 삭제되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}
		return controller;
	}

	// 이번달 추천 책 + index
	@GetMapping("/index")
	public String home(Model m) {
		List<Recommend> list;
		try {
			// 이번달 추천 책 사용하기 위해 이번달 구하기.
			DecimalFormat df = new DecimalFormat("#0");
			Calendar currentCalendar = Calendar.getInstance();

			Integer month = Integer.parseInt(df.format(currentCalendar.get(Calendar.MONTH) + 1));
			list = daoRc.recommend(month);

			m.addAttribute("booklist", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("추천 책 출력 과정에서 문제 발생!!");
			m.addAttribute("error", "추천 책 출력을 정상적으로 하지 못했습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		return "Library/index";
	}
	//// 책 끝

	// 페이지 이동하는 기능.
	@GetMapping("map")
	public String map() {
		return "Library/map";
	}

	@GetMapping("loginpage")
	public String login() {
		return "Library/member/loginMember";
	}

	@GetMapping("register")
	public String register() {
		return "Library/member/addMember";
	}

	@GetMapping("/logout")
	public String logout() {
		return "Library/member/logoutMember";
	}


	//// 멤버 시작
	// 회원가입
	@PostMapping("regist")
	public String regist(@ModelAttribute Login g, @RequestParam String birthyy, @RequestParam String birthmm, @RequestParam String birthdd, @RequestParam String email1, @RequestParam String email2, @RequestParam String token, Model m, HttpServletRequest req) {
		List<Login> list;
		List<Login> list1;

		String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
		JSONObject json = re.getJSONResponse(gRecaptchaResponse);

		boolean isSuccess = (boolean)json.get("success");
		// 리캡챠 동의 안되어있으면, 로그인 ㄴㄴ
		if(isSuccess == false) {
			m.addAttribute("token", token);
			m.addAttribute("iderror", "2");
			return "Library/member/addMember";
		}

		try {
			// 년, 월, 일 값 birth에 넣기
			g.setBirth(birthyy + "/" + birthmm + "/" + birthdd);
			// 이메일 앞, 뒤 합쳐서 email에 넣기
			g.setEmail(email1 + "@" + email2);
			//System.out.println("생일 맞니?" + g.getBirth());
			//System.out.println("이메일 맞니?" + g.getEmail());
			//System.out.println("token test : " + token);
			list = daoG.getid(g.getLid());
			list1 = daoG.getemail(g.getEmail());

			for (int i = 0; i < list.size(); i++) {
				Login check = list.get(i);
				// 대소문자 구분 없이 검색한 아이디와 입력한 아이디가 같다면
				if (check.getLid().equalsIgnoreCase(g.getLid())) {
					// 회원가입 페이지로 이동 후 가입 된 아이디 경고창 ㄱ
					m.addAttribute("token", token);
					m.addAttribute("iderror", "0");
					return "Library/member/addMember";
				}
			}
			for (Login emailcheck : list1) {
				// 대소문자 구분 없이 검색한 이메일과 입력한 이메일이 같다면
				if (emailcheck.getEmail().equalsIgnoreCase(g.getEmail())) {
					// 회원가입 페이지로 이동 후 가입 된 이메일 경고창 ㄱ
					m.addAttribute("token", token);
					m.addAttribute("iderror", "1");
					return "Library/member/addMember";
				}
			}
			daoG.regist(g); // 이상 없다면 가입
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("계정 추가 중 오류 발생");
			m.addAttribute("error", "계정 생성이 제대로 이루어지지 않았습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		m.addAttribute("error", "0");
		return "Library/member/loginMember";
	}

	// 변경페이지 데이터 조회
	@PostMapping("/edit")
	public String edit(@RequestParam String id, Model m) throws Exception {
		Login g = null;

		try {
			g = daoG.edit(id);

			// 번거롭지만 일단 ㄱ
			String[] brith = g.getBirth().split("/"); // 생년월일 나누기
			String[] mail = g.getEmail().split("@"); // 메일 나누기

			m.addAttribute("login", g);
			m.addAttribute("birthyy", brith[0]);
			m.addAttribute("birthmm", brith[1]);
			m.addAttribute("birthdd", brith[2]);
			m.addAttribute("email1", mail[0]);
			m.addAttribute("email2", mail[1]);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("계정 조회 과정에서 문제 발생!!");
			m.addAttribute("error", "계정 조회에 실패했습니다!!!");
		}
		return "Library/member/updateMember";
	}

	// 업데이트
	@PostMapping("/update")
	public String update(@ModelAttribute Login g, @RequestParam String birthyy, @RequestParam String birthmm, @RequestParam String birthdd, @RequestParam String email1, @RequestParam String email2
			, Model m, HttpServletRequest req) {

		String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
		JSONObject json = re.getJSONResponse(gRecaptchaResponse);

		boolean isSuccess = (boolean)json.get("success");

		// 리캡챠 동의 안되어있으면, 로그인 ㄴㄴ
		if(isSuccess == false) {
			m.addAttribute("msg", "1");
			return "Library/member/updateMember";
		}

		try {
			// 년, 월, 일 값 birth에 넣기
			g.setBirth(birthyy + "/" + birthmm + "/" + birthdd);
			// 이메일 앞, 뒤 합쳐서 email에 넣기
			g.setEmail(email1 + "@" + email2);

			daoG.update(g);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("계정 정보 변경 중 오류 발생");
			m.addAttribute("error", "계정 정보 변경이 제대로 이루어지지 않았습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		m.addAttribute("error", "3");
		return "Library/member/loginMember";
	}

	// 탈퇴
	@PostMapping("/delete")
	public String delete(@RequestParam String id, Model m) {
		// 빌린 책을 조회해서 책이 있다면 탈퇴 x
		int count;

		try {
			count = daoL.getbookcount(id);// 빌리고 아직 반납 안한 책 수

			// 책 목록이 널이 아니라면?
			if (count != 0) {
				// 탈퇴 하지 말고 오류 출력.
				m.addAttribute("id", id);
				m.addAttribute("msg", "0");

				return "Library/member/updateMember";
			}
			daoG.deleteID(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("계정 삭제 중 오류 발생");
			m.addAttribute("error", "계정 삭제가 제대로 이루어지지 않았습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		m.addAttribute("error", "4");
		return "Library/member/loginMember";
	}

	// 로그인
	@PostMapping("login")
	public String login(HttpServletRequest req, @RequestParam String id, @RequestParam(name = "password") String pw, Model m) throws Exception {
		Login g = null;

		String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
		JSONObject json = re.getJSONResponse(gRecaptchaResponse);

		boolean isSuccess = (boolean)json.get("success");

		// 리캡챠 동의 안되어있으면, 로그인 ㄴㄴ
		if(isSuccess == false) {
			m.addAttribute("error", "5");
			m.addAttribute("id", id);
			m.addAttribute("pw", pw);
			return "Library/member/loginMember";
		}

		try {
			g = daoG.login(id, pw);
			if (g != null) {
				m.addAttribute("login", g.getLid());
				m.addAttribute("grade", g.isGrade());
				m.addAttribute("name", g.getName());
				m.addAttribute("token", g.getToken());
			} else {
				m.addAttribute("error", "1");
				return "Library/member/loginMember";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("로그인 과정에서 문제 발생!!");
			m.addAttribute("error", "로그인에 실패했습니다!!!");
		}
			if (g != null) {
			if (g.getLid() == null) {
				// System.out.println("sql에서 일치하는 아이디를 못 가져옴. 아이디/비번 불일치 ㄱ");
				m.addAttribute("error", "1");
				return "Library/member/loginMember";
			}
				if (g.getLid() != null && g.isUsed() == false) {
				// System.out.println("탈퇴한 계정(" + g.getLid() + ")에서 로그인 시도, 탈퇴 메시지 ㄱ");
				m.addAttribute("error", "2");
				return "Library/member/loginMember";
			}
		}
	m.addAttribute("msg", "0");
	return "Library/Control";
	}
	//// 멤버 끝

	// 장바구니, 책 대여 시작
	// 장바구니 등록
	@GetMapping("addcart")
	public String addcart(@RequestParam String id, @RequestParam int bid, Model m) {
		m.addAttribute("bid", bid);

		// 장바구니 조회해서 등록된 책이라면 장바구니에 추가 x
		// 이미 빌린 책을 조회해서 빌린 책이라면 장바구니에 추가 x
		List<Cart> list = null;
		List<Loan> list1 = null;
		try {
			list = daoC.getAllCart(id); // 장바구니에 있는 책
			list1 = daoL.getnonLoanbooks(id); // 빌리고 아직 반납 안한 책

			for (int i = 0; i < list.size(); i++) {
				Cart check = list.get(i);
				// 등록된 상품이랑 받아온 파라미터 값이 같다면,
				if (check.getLibrary().getBid() == bid) {
					// 장바구니에 담지 말고 오류 출력.
					// System.out.println("장바구니 추가되어 있는 책");
					m.addAttribute("msg", "0");
					return "Library/View";
				}
			}

			for (int i = 0; i < list1.size(); i++) {
				Loan check = list1.get(i);
				// 등록된 상품이랑 받아온 파라미터 값이 같다면,
				if (check.getLibrary().getBid() == bid) {
					// 장바구니에 담지 말고 오류 출력.
					// System.out.println("빌리고 반납 안한 책");
					m.addAttribute("msg", "2");
					return "Library/View";
				}
			}
			daoC.addCart(id, bid);

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 담는중 중 오류 발생");
			m.addAttribute("error", "책을 장바구니에 담지 못했습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		m.addAttribute("msg", "1");
		return "Library/View";
	}

	// 장바구니 등록 후 장바구니 페이지 이동(리턴 값만 다르게)
	@GetMapping("addcarttocart")
	public String addcarttocart(@RequestParam String id, @RequestParam int bid, Model m) {
		m.addAttribute("bid", bid);
		int count;

		// 장바구니 조회해서 등록된 책이라면 장바구니에 추가 x
		// 이미 빌린 책을 조회해서 빌린 책이라면 장바구니에 추가 x
		// 대여 후 장바구니 조회 기능을 넣자. count, list
		List<Cart> list = null;
		List<Loan> list1 = null;
		List<Cart> list2 = null;
		try {
			list = daoC.getAllCart(id); // 장바구니에 있는 책
			list1 = daoL.getnonLoanbooks(id); // 빌리고 아직 반납 안한 책

			for (int i = 0; i < list.size(); i++) {
				Cart check = list.get(i);
				// 등록된 상품이랑 받아온 파라미터 값이 같다면,
				if (check.getLibrary().getBid() == bid) {
					// 장바구니에 담지 말고 오류 출력.
					//System.out.println("장바구니 추가되어 있는 책");
					m.addAttribute("msg", "0");
					return "Library/View";
				}
			}

			for (int i = 0; i < list1.size(); i++) {
				Loan check = list1.get(i);
				// 등록된 상품이랑 받아온 파라미터 값이 같다면,
				if (check.getLibrary().getBid() == bid) {
					// 장바구니에 담지 말고 오류 출력.
					//System.out.println("빌리고 반납 안한 책");
					m.addAttribute("msg", "2");
					return "Library/View";
				}
			}
			daoC.addCart(id, bid); // 장바구니에 넣기


		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 담는중 중 오류 발생");
			m.addAttribute("error", "책을 장바구니에 담지 못했습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}

		// 추가 끝.

		// 장바구니 조회 시작.
		// todo : 리스트는 나오나, 제목, 수량 등 값이 안 나옴.(Cartlist)
		//          방법이 없다면 컨트롤러로 보내고, 컨트롤러에서 다시 조회로 보내자.
		/*try {
			count = daoL.getbookcount(id);
			list2 = daoC.getAllCart(id); // 다시 조회

			m.addAttribute("booklist", list2); // 장바구니 목록
			m.addAttribute("count", count); // 책 수
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("장바구니 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "장바구니 목록이 정상적으로 처리되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller ;
		}
		return "Library/cart";*/
		// 야매 (컨트롤러로 보내고 컨트롤러에서 조회로 보냄.)
		m.addAttribute("msg", "4");
		return controller;
	}

	// 장바구니 조회
	@GetMapping("listcart")
	public String listcart(@RequestParam String id, Model m) {
		int count;
		List<Cart> list;

		try {
			count = daoL.getbookcount(id);
			list = daoC.getAllCart(id);
			m.addAttribute("booklist", list);
			m.addAttribute("count", count);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("장바구니 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "장바구니 목록이 정상적으로 처리되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}
		return "Library/cart";
	}

	// 장바구니 삭제
	@GetMapping("deleteCart")
	public String deleteCart(@RequestParam String id, @RequestParam int bid, Model m) {
		int count;
		List<Cart> list;
		try {
			daoC.delCart(id, bid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("장바구니 삭제 과정에서 문제 발생!!");
			m.addAttribute("error", "장바구니가 삭제되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}
		// 삭제 끝
		// 장바구니 조회 시작.
		try {
			count = daoL.getbookcount(id);
			list = daoC.getAllCart(id); // 다시 조회

			m.addAttribute("booklist", list); // 장바구니 목록
			m.addAttribute("count", count); // 책 수
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("장바구니 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "장바구니 목록이 정상적으로 처리되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}
		return "Library/cart";
	}

	// 책 대여
	@GetMapping("loan")
	public String loan(@RequestParam String id, Model m) {
		List<Cart> list;
		int count;

		// 장바구니 조회 시작.
		try {
			count = daoL.getbookcount(id);
			list = daoC.getAllCart(id);

			m.addAttribute("booklist", list); // 장바구니 목록
			m.addAttribute("count", count); // 책 수
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("장바구니 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "장바구니 목록이 정상적으로 처리되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}

		// 연체기간(Overdue)이 오늘보다 크면 대여 금지.
		Login g;
		try {
			g = daoG.edit(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 대여 과정에서 문제 발생!!");
			m.addAttribute("error", "책 대여 과정에서 문제가 발생했습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		DecimalFormat df = new DecimalFormat("#0");
		Calendar currentCalendar = Calendar.getInstance();
		long tyear = Integer.parseInt(df.format((currentCalendar.get(Calendar.YEAR) * 365)));
		long tmonth = Integer.parseInt(df.format((currentCalendar.get(Calendar.MONTH) + 1) * 30));
		long tday = Integer.parseInt(df.format(currentCalendar.get(Calendar.DATE)));
		long now = tyear + tmonth + tday;

		String Overdue = g.getOverdue();
		if (Overdue != null) {
			String[] Overdue1 = Overdue.split("-");
			Long end = (long) (Integer.parseInt(Overdue1[0]) * 365 + Integer.parseInt(Overdue1[1]) * 30 + Integer.parseInt(Overdue1[2]));

			if (now < end) {
				// 대여 불가!
				m.addAttribute("msg", "1");
				m.addAttribute("overdue", g.getOverdue());
				return "Library/cart";
			}
		}
		// 장바구니에 책이 있더라도 책의 수량이 0이면 책 대여 금지
		try {
			list = daoC.getAllCart(id); // 장바구니에 있는 책

			for (int i = 0; i < list.size(); i++) {
				Cart check = list.get(i);
				// 등록된 상품의 개수가 0이면
				if (check.getLibrary().getStock() == 0) {
					// 대여 하지말고 오류 출력.
					// 나중에 수정하자
					//return "lib?action=listcart&id=" + id + "&msg=0";
					m.addAttribute("msg", "0");
					m.addAttribute("id", id);
					return "Library/cart";
				}
			}
			//System.out.println(" loan id값 : " + id);
			daoL.LoanBook(id);
			daoC.delCartpro(id);
			dao.downcount(id);
			daoG.uploancount(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 대여 과정에서 문제 발생!!");
			m.addAttribute("error", "책 대여 과정에서 문제가 발생했습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		// 대여 끝
		// 대여 페이지 조회 시작
		// todo : 리스트는 나오나, 제목, 수량 등 값이 안 나옴.(Loanlist)
		//          방법이 없다면 컨트롤러로 보내고, 컨트롤러에서 다시 조회로 보내자.
		/*try {
			List<Loan> list3;
			list3 = daoL.getAllLoan(id);
			m.addAttribute("booklist", list3);

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 대여 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "책 대여 목록이 정상적으로 처리되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller ;
		}
		return "Library/loan";*/
		// 야매 (컨트롤러로 보내고 컨트롤러에서 조회로 보냄.)
		m.addAttribute("msg", "5");
		return controller;
	}

	// 책 대여 정보
	@GetMapping("listloan")
	public String listloan(@RequestParam String id, Model m) {
		List<Loan> list = null;
		DecimalFormat df = new DecimalFormat("#0");
		Calendar currentCalendar = Calendar.getInstance();
		int tmonth = Integer.parseInt(df.format((currentCalendar.get(Calendar.MONTH) + 1) * 30));
		int tday = Integer.parseInt(df.format(currentCalendar.get(Calendar.DATE)));
		int now = tmonth + tday;

		m.addAttribute("now", now);

		try {
			list = daoL.getAllLoan(id);
			m.addAttribute("booklist", list);


		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 대여 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "책 대여 목록이 정상적으로 처리되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}
		return "Library/loan";
	}

	// 책 반납
	@GetMapping("ReturnBook")
	public String ReturnBook(@RequestParam String id, @RequestParam int bid, @RequestParam int period, Model m) {
		Login g;

		try {
			dao.upcount(id, bid);
			daoL.ReturnBook(id, bid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 반납 과정에서 문제 발생!!");
			m.addAttribute("error", "책 반납을 못했습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		// 반납 끝
		// 대여 페이지
		try {
			List<Loan> list;
			list = daoL.getAllLoan(id);
			m.addAttribute("booklist", list);

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 대여 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "책 대여 목록이 정상적으로 처리되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}
		// 연체일*3일 만큼 연체 못하도록 ㄱ
		try {
			if (period > 0) {
				daoG.overdue(period, id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("연체 기간 설정 과정에서 문제 발생!!");
			m.addAttribute("error", "연체 기간 설정이 정상적으로 처리되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}

		try {
			g = daoG.edit(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 대여 과정에서 문제 발생!!");
			m.addAttribute("error", "책 대여 과정에서 문제가 발생했습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		DecimalFormat df = new DecimalFormat("#0");
		Calendar currentCalendar = Calendar.getInstance();
		long tyear = Integer.parseInt(df.format((currentCalendar.get(Calendar.YEAR) * 365)));
		long tmonth = Integer.parseInt(df.format((currentCalendar.get(Calendar.MONTH) + 1) * 30));
		long tday = Integer.parseInt(df.format(currentCalendar.get(Calendar.DATE)));
		long now = tyear + tmonth + tday;

		String Overdue = g.getOverdue();
		if (Overdue != null) {
			String[] Overdue1 = Overdue.split("-");
			Long end = (long) (Integer.parseInt(Overdue1[0]) * 365 + Integer.parseInt(Overdue1[1]) * 30 + Integer.parseInt(Overdue1[2]));

			if (now < end) {
				// 대여 불가!
				m.addAttribute("alert", "1");
				m.addAttribute("overdue", g.getOverdue());
				return "Library/loan";
			}
		}
		return "Library/loan";
	}
	// 장 바구니, 책 대여 끝

	// 리뷰 등록기능
	@PostMapping("/review")
	public String addReview(@ModelAttribute Review Review, Model m, @SessionAttribute String sessionId) {

		try {
			//리뷰 등록
			daoR.addReview(Review);
			//리뷰 등록 완료 처리
			daoR.reviewed(Review.getLoan().getId());
			daoG.upreviewcount(sessionId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("리뷰 추가 과정에서 문제 발생!!");
			m.addAttribute("error", "리뷰가 정상적으로 등록되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}

		try {
			List<Loan> list;
			list = daoL.getAllLoan(Review.getLogin().getLid());
			m.addAttribute("booklist", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("책 대여 목록 생성 과정에서 문제 발생!!");
			m.addAttribute("error", "책 대여 목록이 정상적으로 처리되지 않았습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}
		// 리뷰 등록 완료
		m.addAttribute("alert", "0");
		return "Library/loan";
	}

	@GetMapping("/delreview/{id}")
	public String delreview(@PathVariable int id, Model m, @SessionAttribute String sessionId) {
		try {
			Review n = daoR.getBookByid(id);
			// 지워도 됨. 해결함.
			//System.out.println("bid 값 맞니? " + n.getLibrary().getBid());
			m.addAttribute("bid", n.getLibrary().getBid());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("책 정보를 가져오는 과정에서 문제 발생!!");
			m.addAttribute("error", "책 정보를 정상적으로 가져오지 못했습니다!!");
		}
		try {
			daoR.delReview(id);
			daoR.unreviewed(id);
			daoG.downreviewcount(sessionId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("리뷰 삭제 과정에서 문제 발생!!");
			m.addAttribute("error", "리뷰를 삭제하지 못했습니다!!");
			m.addAttribute("msg", "2");
			return controller;
		}
		m.addAttribute("msg", "4");
		return "Library/View";
	}

	// 업데이트
	@PostMapping("/upreview")
	public String upreview(@ModelAttribute Review r, Model m) {
		try {
			daoR.update(r);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("리뷰 변경 중 오류 발생");
			m.addAttribute("error", "리뷰 변경이 제대로 이루어지지 않았습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}

		try {
			Library n = dao.getBook(r.getLibrary().getBid());
			m.addAttribute("book", n);
			List<Review> list = daoR.getReview(r.getLibrary().getBid());
			m.addAttribute("reviewlist", list);
			m.addAttribute("bid", r.getLibrary().getBid());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("책 정보를 가져오는 과정에서 문제 발생!!");
			m.addAttribute("error", "책 정보를 정상적으로 가져오지 못했습니다!!");
		}
		m.addAttribute("msg", "3");
		return "Library/View";
	}

	// 이번달 추천 책 + index
	@GetMapping("/rank")
	public String rank(Model m) {
		List<Login> RClist;
		List<Login> LClist;
		try {
			RClist = daoG.reviewrank();
			LClist = daoG.loanrank();

			for (Login login : RClist) {

				String userName = login.getName();

				if (userName.length() < 3) { // 외자일 경우

					login.setName(userName.substring(0, 1) + "*");

				} else {

					String frsName = userName.substring(0, 1);

					// 사용자 이름 중간글자
					String midName = userName.substring(1, userName.length() - 1);

					// 사용자 이름 중간글자 마스킹
					String cnvMidName = "";
					for (int i = 0; i < midName.length(); i++) {
						cnvMidName += "*"; // 중간 글자 수 만큼 * 표시
					}

					// 사용자 이름 마지막 글자
					String lstName = userName.substring(userName.length() - 1, userName.length());

					// 마스킹 완성된 사용자 이름
					String maskingName = frsName + cnvMidName + lstName;

					login.setName(maskingName);
				}

				String userid = login.getLid();

				String frsName1 = userid.substring(0, 1);

				String midName1 = userid.substring(1, userid.length() - 1);

				String cnvMidName1 = "";
				for (int i = 0; i < midName1.length(); i++) {
					cnvMidName1 += "*";
				}

				String lstName1 = userid.substring(userid.length() - 1, userid.length());

				String maskingName1 = frsName1 + cnvMidName1 + lstName1;

				login.setLid(maskingName1);
			}

			m.addAttribute("RClist", RClist);
			m.addAttribute("LClist", LClist);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("추천 책 출력 과정에서 문제 발생!!");
			m.addAttribute("error", "추천 책 출력을 정상적으로 하지 못했습니다.");
			m.addAttribute("msg", "2");
			return controller;
		}
		return "Library/rank";
	}

	@GetMapping("/klogout")
	public String loginPage()
	{
		return "Library/member/logoutMember";
	}
	@GetMapping("/kakao")
	public String getCI(@RequestParam String code, Model m) throws Exception {

		String access_token = ks.getToken(code);
		Map<String, Object> userInfo = ks.getUserInfo(access_token);
		String agreementInfo = ks.getAgreementInfo(access_token);

		// 와! 문자 나누기!
		String test = agreementInfo.replace("\"","");
		test = test.replace("{","");
		test = test.replace("}","");
		test = test.replace("[","");
		test = test.replace("]","");
		String[] test1 = test.split(",");
		String id = test1[0].substring(3);

		Login g = null;

		try {
			g = daoG.findtoken(id);
			if (g != null) {
				m.addAttribute("login", g.getLid());
				m.addAttribute("grade", g.isGrade());
				m.addAttribute("name", g.getName());
				m.addAttribute("token", g.getToken());

				m.addAttribute("msg", "0");
				return "Library/Control";
			} else {
				// 토큰 없으니 회워가입 ㄱ(사실 토큰아니라 아이디 값임 ㅎ;)
				m.addAttribute("msg", "0");
				m.addAttribute("token", id);
				return "Library/member/addMember";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("카카오 로그인 과정에서 문제 발생!!");
			m.addAttribute("error", "카카오 로그인에 실패했습니다!!!");
		}





		//ci는 비즈니스 전환후 검수신청 -> 허락받아야 수집 가능
		return "Library/member/kakao";
	}
}