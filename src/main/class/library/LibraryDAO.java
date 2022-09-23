/**
 * * 기능
 * - 책 등록
 * - 책 목록 조회
 * - 책 상세 보기
 * - 책 삭제
 * - 검색 기능(추가중?)
 */

package library;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryDAO {

	// 책 수량
	public int getlistcount(String items, String text) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;
		String sql;

		if (items == null && text == null)
			sql = "select count(*) from booklist";
		else
			sql = "SELECT count(*) FROM booklist where " + items + " like '%" + text + "%'";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				x = rs.getInt(1);

		} catch (Exception ex) {
			System.out.println("getlistcount() 에러 : " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return x;
	}

	// 책 등록(administrator)
	public void addBook(Library n) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "insert into booklist(title, writer, description, category, publisher, stock, book_cover, date ) value(?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP())";
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, n.getTitle());
			pstmt.setString(2, n.getWriter());
			pstmt.setString(3, n.getDescription());
			pstmt.setString(4, n.getCategory());
			pstmt.setString(5, n.getPublisher());
			pstmt.setInt(6, n.getStock());
			pstmt.setString(7, n.getBookCover());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	// 페이지 넘버링용 책 목록 가져오기
	public List<Library> getAll(int page, int limit, String items, String text) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Library> BookList = new ArrayList<>();

		int total_record = getlistcount(items, text);
		int start = (page - 1) * limit;
		int index = start + 1;

		String sql;

		if (items == null && text == null)
			sql = "select * from booklist ORDER BY BID ASC";
		else
			sql = "SELECT * FROM booklist where " + items + " like '%" + text + "%' ORDER BY BID ASC ";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();

			while (rs.absolute(index)) {
				Library n = new Library();
				n.setBid(rs.getInt("bid"));
				n.setTitle(rs.getString("title"));
				n.setWriter(rs.getString("writer"));
				n.setDescription(rs.getString("description"));
				n.setCategory(rs.getString("category"));
				n.setPublisher(rs.getString("publisher"));
				n.setStock(rs.getInt("stock"));
				n.setDate(rs.getString("date"));
				n.setBookCover(rs.getString("book_cover"));

				BookList.add(n);

				if (index < (start + limit) && index <= total_record)
					index++;
				else
					break;

			}
			return BookList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}

	// 책 목록 전체 가져오기(안 쓸 듯?)
	public List<Library> getAll() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Library> BookList = new ArrayList<>();

		String sql = "select * from booklist";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Library n = new Library();
				n.setBid(rs.getInt("bid"));
				n.setTitle(rs.getString("title"));
				n.setWriter(rs.getString("writer"));
				n.setDescription(rs.getString("description"));
				n.setCategory(rs.getString("category"));
				n.setPublisher(rs.getString("publisher"));
				n.setStock(rs.getInt("stock"));
				n.setDate(rs.getString("date"));

				BookList.add(n);
			}
			return BookList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}

	// 이달의 추천 책
	public List<Library> recommend(String month) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Library> BookList = new ArrayList<>();

		String sql = "SELECT booklist.bid, title, writer, description, category, publisher, stock, book_cover, date From booklist inner join recommend on booklist.bid = recommend.bid where month = ?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, month);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Library n = new Library();
				n.setBid(rs.getInt("bid"));
				n.setTitle(rs.getString("title"));
				n.setWriter(rs.getString("writer"));
				n.setDescription(rs.getString("description"));
				n.setCategory(rs.getString("category"));
				n.setPublisher(rs.getString("publisher"));
				n.setStock(rs.getInt("stock"));
				n.setDate(rs.getString("date"));
				n.setBookCover(rs.getString("book_cover"));

				BookList.add(n);
			}
			return BookList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}

	// 선택된 책 상세 보기
	public Library getBook(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Library n = new Library();
		String sql = "select * from booklist where bid=?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				n.setBid(rs.getInt("bid"));
				n.setTitle(rs.getString("title"));
				n.setWriter(rs.getString("writer"));
				n.setDescription(rs.getString("description"));
				n.setCategory(rs.getString("category"));
				n.setPublisher(rs.getString("publisher"));
				n.setStock(rs.getInt("stock"));
				n.setBookCover(rs.getString("book_cover"));
				n.setDate(rs.getString("date"));
				pstmt.executeQuery();
			}
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}

	public Library getBookByid(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Library n = new Library();
		String sql = "SELECT * From booklist inner join review on booklist.bid = review.bid where id = ?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				n.setBid(rs.getInt("bid"));
				n.setTitle(rs.getString("title"));
				n.setWriter(rs.getString("writer"));
				n.setDescription(rs.getString("description"));
				n.setCategory(rs.getString("category"));
				n.setPublisher(rs.getString("publisher"));
				n.setStock(rs.getInt("stock"));
				n.setBookCover(rs.getString("book_cover"));
				n.setDate(rs.getString("date"));
				pstmt.executeQuery();
			}
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}


	// 책 삭제(admin)
	public void delBook(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from booklist where bid=?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("DB에러");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
}
