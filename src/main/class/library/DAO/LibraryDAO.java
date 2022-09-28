/**
 * * 기능
 * - 책 등록
 * - 책 목록 조회
 * - 책 상세 보기
 * - 책 삭제
 * - 검색 기능(추가중?)
 */

package library.DAO;

import library.Entity.Library;
import library.Repository.LibraryRepository;
import library.dbconnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryDAO {

	@Autowired
	private LibraryRepository libraryrepository;

	// 책 수량
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
		lib.setBookCover(n.getBookCover());
		Library newlib = libraryrepository.save(lib);
	}

	// 페이지 넘버링용 책 목록 가져오기
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
}
