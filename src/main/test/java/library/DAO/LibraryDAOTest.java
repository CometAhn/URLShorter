package library.DAO;

import library.Entity.Library;
import library.Entity.Loan;
import library.Entity.Login;
import library.Repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class LibraryDAOTest {

	@Autowired
	LibraryRepository libraryRepository;


	// 오류
	// mappedBy reference an unknown target entity property: library.Entity.Cart.booklist in library.Entity.Library.cartList

	@Test
	public void read() {
		List<Library> librarylist = libraryRepository.findByBid(2);
		for (Library lib : librarylist) {
			lib.getLoanList().stream().forEach(loan -> {
				Login login = loan.getLogin();
				System.out.println(login);
			});
		}
	}
}