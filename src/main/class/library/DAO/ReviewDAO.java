
package library.DAO;

import library.Entity.Library;
import library.Entity.Loan;
import library.Entity.Login;
import library.Entity.Review;
import library.Repository.LoanRepository;
import library.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReviewDAO {
	// 리뷰 목록 가져오기
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	LoanRepository loanRepository;

	public List<Review> getReview(int id) throws SQLException {
		return reviewRepository.findAllByLibraryBid(id);
	}

	// 리뷰 추가
	public void addReview(Review r) throws Exception {
		// 현재 날짜 구하기용.
		LocalDateTime date = LocalDateTime.now();
		String sdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date);

		Review re = new Review();
		Login lo = new Login();
		Library l = new Library();
		Loan la = new Loan();

		lo.setLid(r.getLogin().getLid());
		l.setBid(r.getLibrary().getBid());
		la.setId(r.getLoan().getId());

		re.setLogin(lo);
		re.setLibrary(l);
		re.setLoan(la);
		re.setTitle(r.getTitle());
		re.setContents(r.getContents());
		re.setScore(r.getScore());
		re.setDate(sdate);

		Review newreview = reviewRepository.save(re);
	}

	// 리뷰 작성 완료
	public void reviewed(int id) throws SQLException {
		Loan loan = loanRepository.findById(id);

		loan.setReviewed(true);

		Loan newloan = loanRepository.save(loan);
	}

	// 리뷰 수정
	public void update(Review r) throws Exception {
		// 현재 날짜 구하기용.
		LocalDateTime date = LocalDateTime.now();
		String sdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date);

		Review review = reviewRepository.findById(r.getId());

		review.setTitle(r.getTitle());
		review.setContents(r.getContents());
		review.setScore(r.getScore());
		review.setDate(sdate);

		Review addreview = reviewRepository.save(review);
	}

	// 리뷰 삭제
	public void delReview(int id) throws SQLException {
		Review re = reviewRepository.findByLoanId(id);
		reviewRepository.delete(re);
	}

	// 리뷰 아이디 값으로 조회
	public Review getBookByid(int id) throws SQLException {
		return reviewRepository.findByLoanId(id);
	}


	// 리뷰 작성 완료 취소
	public void unreviewed(int id) throws SQLException {
		Loan loan = loanRepository.findById(id);

		loan.setReviewed(false);

		Loan newloan = loanRepository.save(loan);
	}
}