package library.DAO;

import library.Entity.Cart;
import library.Entity.Library;
import library.Entity.Loan;
import library.Entity.Login;
import library.Repository.CartRepository;
import library.Repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class LoanDAO {
	@Autowired
	LoanRepository loanRepository;

	@Autowired
	CartRepository cartRepository;


	// 책 대여
	public void LoanBook(String id) throws Exception {

		// 날짜 구하기용.
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

			Loan newloan = loanRepository.save(loan);
		}
	}
}
