/**
 * * 기능 - 회원가입 - 회원삭제 - 회원정보 조회 - 회원정보 수정 - 로그인
 */

package library.DAO;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import library.Entity.Library;
import library.Entity.Loan;
import library.Entity.Login;
import library.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginDAO {

	@Autowired
	LoginRepository loginRepository;

	// 회원가입
	public void regist(Login g) throws Exception {

		LocalDateTime date = LocalDateTime.now();
		String sdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date);

		g.setUsed(true);
		g.setRegistDay(sdate);
		g.setReviewCount(0);
		g.setLoanCount(0);

		// 토큰 값 없으면 0으로.
		if(g.getToken().equals("")){
			g.setToken("0");
		}

		Login newlogin = loginRepository.save(g);
	}

	// 회원 삭제
	public void deleteID(String id) throws Exception {
		Login login = loginRepository.findByLid(id);

		loginRepository.delete(login);
	}

	// 조회
	public Login edit(String id) throws Exception {
		return loginRepository.findByLid(id);
	}

	// 회원 수정
	public void update(Login g) throws Exception {
		Login login = loginRepository.findByLid(g.getLid());

		login.setPassword(g.getPassword());
		login.setName(g.getName());
		login.setGender(g.getGender());
		login.setBirth(g.getBirth());
		login.setEmail(g.getEmail());
		login.setPhone(g.getPhone());
		login.setAddress(g.getAddress());

		Login newlogin = loginRepository.save(login);
	}

	// 로그인
	public Login login(String id, String pw) throws Exception {
		return loginRepository.findByLidAndPassword(id, pw);
	}


	// 아이디 조회
	public List<Login> getid(String id) throws SQLException {
		return loginRepository.findAllByLid(id);
	}

	// 이메일 조회
	public List<Login> getemail(String email) throws SQLException {
		return loginRepository.findAllByEmail(email);
	}

	// 일정 기간 책 대여 하지마!
	public void overdue(int period, String id) throws Exception {
		// 음수일 경우, 0으로
		if (period < 0) {
			period = 0;
		}

		// 오늘 날짜 값 구하기 위해서 사용.
		// 번거롭다!
		DecimalFormat df = new DecimalFormat("#0");
		Calendar cdate = Calendar.getInstance();
		long tyear = Integer.parseInt(df.format((cdate.get(Calendar.YEAR) * 365)));
		long tmonth = Integer.parseInt(df.format((cdate.get(Calendar.MONTH) + 1) * 30));
		long tday = Integer.parseInt(df.format(cdate.get(Calendar.DATE)));
		long now = tyear + tmonth + tday;

		// 현재 날짜 구하기용.
		LocalDateTime date = LocalDateTime.now();
		String sdate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
		String edate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date.plusDays(period * 3));

		Login login = loginRepository.findByLid(id);

		String Overdue = login.getOverdue();

		if (Overdue == null) {
			//System.out.println("Overdue는 널이다");
			//System.out.println("sdate값 잘 나오니?" + sdate);

			login.setOverdue(sdate);
			Login newlogin = loginRepository.save(login);
		} else {
			// Overdue 날짜 값 구하기 위해서 사용.
			// 번거롭다!
			String[] Overdue1 = Overdue.split("-");
			Long end = (long) (Integer.parseInt(Overdue1[0]) * 365 + Integer.parseInt(Overdue1[1]) * 30 + Integer.parseInt(Overdue1[2]));

			if (now > end) {
				//System.out.println("기존 overdue값보다 오늘 날짜가 더 크다!");
				//System.out.println("sdate값 잘 나오니?" + sdate);

				login.setOverdue(sdate);
				Login newlogin = loginRepository.save(login);
			} else {
				//System.out.println("기존 overdue값보다 오늘 날짜가 더 작다!");

				// cdate 재활용.
				// cdate에 overdue 값으로 넣기
				cdate.set(Integer.parseInt(Overdue1[0]), Integer.parseInt(Overdue1[1]) - 1, Integer.parseInt(Overdue1[2]));

				// period*3이 원하는 데이트 값.
				cdate.add(Calendar.DATE, period * 3);

				// 값 포맷 rdate.format(cal.getTime())
				SimpleDateFormat rdate = new SimpleDateFormat("YYYY-MM-dd");

				login.setOverdue(rdate.format(cdate.getTime()));
				Login newlogin = loginRepository.save(login);
			}
		}
	}

	// 리뷰카운트 하나 줄이자
	public void downreviewcount(String id) throws Exception {
		Login login = loginRepository.findByLid(id);

		login.setReviewCount(login.getReviewCount() - 1);
		Login newlogin = loginRepository.save(login);
	}

	// 리뷰카운트 하나 늘리자
	public void upreviewcount(String id) throws Exception {
		Login login = loginRepository.findByLid(id);

		login.setReviewCount(login.getReviewCount() + 1);
		Login newlogin = loginRepository.save(login);
	}

	// 대여카운트 하나 늘리자
	public void uploancount(String id) throws Exception {
		Login login = loginRepository.findByLid(id);

		login.setLoanCount(login.getLoanCount() + 1);
		Login newlogin = loginRepository.save(login);
	}

	public List<Login> reviewrank() throws Exception {
		return loginRepository.findTop3ByOrderByReviewCountDesc();
	}

	public List<Login> loanrank() throws Exception {
		return loginRepository.findTop3ByOrderByLoanCountDesc();
	}

	public Login findtoken(String token) throws Exception {
		return loginRepository.findByToken(token);
	}
}
