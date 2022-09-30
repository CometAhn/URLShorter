package library.DAO;

import library.Entity.*;
import library.Repository.CartRepository;
import library.Repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class LoanDAO {

	@Autowired
	LoanRepository loanRepository;
	@Autowired
	CartRepository cartRepository;

	// 책 대여
	public void LoanBook(String id) throws Exception {
		// 현재 날짜 구하기용 및 포맷
		LocalDateTime date = LocalDateTime.now();
		String sdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date);
		String edate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date.plusDays(7));

		List<Cart> cart = cartRepository.findAllByLoginLid(id);

		for (int i = 0; i < cart.size(); i++) {
			Cart cartd = cart.get(i);

			Loan loan = new Loan();
			Login lo = new Login();
			Library l = new Library();

			lo.setLid(cartd.getLogin().getLid());
			l.setBid(cartd.getLibrary().getBid());

			loan.setLogin(lo);
			loan.setLibrary(l);
			loan.setStartDate(sdate);
			loan.setEndDate(edate);
			loan.setStatus(true);
			loan.setPeriod(-7);

			Loan newloan = loanRepository.save(loan);
		}
	}

	// 책 대여 정보 조회
	public List<Loan> getAllLoan(String id) throws Exception {
		DecimalFormat df = new DecimalFormat("#0");
		Calendar currentCalendar = Calendar.getInstance();

		int tmonth = Integer.parseInt(df.format((currentCalendar.get(Calendar.MONTH) + 1) * 30));
		int tday = Integer.parseInt(df.format(currentCalendar.get(Calendar.DATE)));
		int now = tmonth + tday;

		List<Loan> newloan = new ArrayList();
		List<Loan> loan = loanRepository.findAllByLoginLid(id);

		for (Loan loann : loan) {

			String[] month = loann.getEndDate().split("-");
			int end = Integer.parseInt(month[1]) * 30 + Integer.parseInt(month[2].substring(0, 2));

			loann.setPeriod(now - end);

			// 굳이 세이브 해줄 필요는 없는데...
			// period 컬럼 데이터 -7 고정인 거 보기 싫어서 추가.
			// !!! row 값이 많아지면 save 속도 저하 생길 듯. 비효율적인 기능 !!!
			Loan addloan = loanRepository.save(loann);

			newloan.add(loann);

		}
		return newloan;
	}

	// 책 반납 안한 책
	public List<Loan> getnonLoanbooks(String id) throws Exception {
		return loanRepository.findAllByLoginLidAndStatus(id, true);
	}


	// 책 반납
	public void ReturnBook(String id, int bid) throws SQLException {
		LocalDateTime date = LocalDateTime.now();
		String sdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date);

		Loan loan = loanRepository.findByLoginLidAndLibraryBidAndStatus(id, bid, true);

		loan.setStatus(false);
		loan.setReturnDate(sdate);

		Loan addloan = loanRepository.save(loan);
	}

	// 대여 중인 책 수 카운트.
	public int getbookcount(String id) {
		return loanRepository.countByLoginLidAndStatus(id, true);
	}
}
