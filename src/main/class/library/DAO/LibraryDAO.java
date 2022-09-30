package library.DAO;

import library.Entity.Library;
import library.Entity.Loan;
import library.Repository.LibraryRepository;
import library.Repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class LibraryDAO {

	@Autowired
	private LibraryRepository libraryrepository;
	@Autowired
	private LoanRepository loanRepository;

	// 책 수량(페이지 넘버링용)
	public int getlistcount(String items, String text) {

		if (items.equals("Title")) {
			return libraryrepository.countByTitleContaining(text);
		} else if (items.equals("Writer")) {
			return libraryrepository.countByWriterContaining(text);
		} else if (items.equals("Category")) {
			return libraryrepository.countByCategoryContaining(text);
		} else if (items.equals("Publisher")) {
			return libraryrepository.countByPublisherContaining(text);
		}
		return libraryrepository.countBy();
	}

	// 책 등록(administrator)
	public void addBook(Library n) throws Exception {
		Library lib = new Library();
		lib.setTitle(n.getTitle());
		lib.setWriter(n.getWriter());
		lib.setDescription(n.getDescription());
		lib.setCategory(n.getCategory());
		lib.setPublisher(n.getPublisher());
		lib.setStock(n.getStock());
		lib.setBookCover("/img/" + n.getBookCover());
		lib.setDate(LocalDate.now().toString());
		Library newlib = libraryrepository.save(lib);
	}

	// 책 목록
	// 페이지 넘버링 해야함 -> 야매로 함!
	public List<Library> getAll(int page, int limit, String items, String text) throws Exception {

		if (items == null && text == null) {
			return libraryrepository.findAll();
		} else {
			if (items.equals("Title")) {
				return libraryrepository.findByTitleContaining(text);
			} else if (items.equals("Writer")) {
				return libraryrepository.findByWriterContaining(text);
			} else if (items.equals("Category")) {
				return libraryrepository.findByCategoryContaining(text);
			} else if (items.equals("Publisher")) {
				return libraryrepository.findByPublisherContaining(text);
			}
		}
		return libraryrepository.findAll();
	}


	// 선택된 책 상세 보기
	public Library getBook(int id) throws SQLException {
		return libraryrepository.findByBid(id);
	}


	// 책 삭제(admin)
	public void delBook(int id) throws SQLException {
		Library library = libraryrepository.findByBid(id);
		libraryrepository.delete(library);
	}


	// 책 개수 하나 줄이자
	public void downcount(String id) throws Exception {
		// 여러개를 동시에 대여하니까 List로 처리.
		List<Loan> loanlist = loanRepository.findAllByLoginLidAndStatus(id, true);

		for (Loan loan : loanlist) {
			Library l = libraryrepository.findByBid(loan.getLibrary().getBid());
			l.setStock(l.getStock() - 1);

			Library newl = libraryrepository.save(l);
		}
	}

	// 책 개수 하나 늘리자
	public void upcount(String id, int bid) throws Exception {
		// 하나 씩 반납하니까 List 사용 안 함.
		Loan loan = loanRepository.findByLoginLidAndLibraryBidAndStatus(id, bid, true);

		Library l = libraryrepository.findByBid(loan.getLibrary().getBid());
		l.setStock(l.getStock() + 1);

		Library newl = libraryrepository.save(l);
	}
}
