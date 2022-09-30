package library.DAO;

import library.Entity.Library;
import library.Entity.Loan;
import library.Entity.Login;
import library.Entity.Review;
import library.Repository.LibraryRepository;
import library.Repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Transactional
@SpringBootTest
class LibraryDAOTest {

	@Autowired
	LibraryRepository libraryRepository;
	@Autowired
	ReviewRepository reviewRepository;


	// 오류
	// mappedBy reference an unknown target entity property: library.Entity.Cart.booklist in library.Entity.Library.cartList
	// 해결 ㅇㅇ. mapped 명칭 잘못됨.
	@Test
	public void read() {
		//reviewRepository.countByLoanId();
	}
}
