/**
 * * 기능 - 장바구니 목록 추가. - 장바구니 목록 조회. - 장바구니 목록 삭제. - 책 대여. - 책 대여 후 장바구니 삭제. - 책 대여 후 책 목록에서 책 개수 줄이기. - 대여한 책 정보 조회. - 책 반납. - 책 반납 후 책 모곩에서 책 개수 늘리기.
 * <p>
 * * 장바구니 데이터베이스(cart) varchar ID 	회원 아이디 int BID		책 아이디
 * <p>
 * * 대여 상태 데이터베이스(loan) varchar ID			회원 아이디 int BID				책 아이디 varchar StartDate	빌린 날짜 varchar EndDate		반납해야할 날짜 varchar ReturnDate	반납한 날짜 int status			상태, 0 반납, 1 대여중 한 아이디의 process가 3을 초과할 경우 대여 불가.  count 사용하자.
 */

package library.DAO;

import java.text.DecimalFormat;
import java.util.Calendar;

import library.Entity.*;
import library.Repository.CartRepository;
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
public class CartDAO {
	@Autowired
	CartRepository cartRepository;

	// 장바구니 추가
	public void addCart(String id, int bid) throws Exception {
		Cart cart = new Cart();
		Login lo = new Login();
		Library l = new Library();

		lo.setLid(id);
		l.setBid(bid);

		cart.setLogin(lo);
		cart.setLibrary(l);

		Cart newcart = cartRepository.save(cart);
	}

	// 장바구니 조회
	public List<Cart> getAllCart(String id) throws Exception {

		return cartRepository.findAllByLoginLid(id);
	}

	// 장바구니 목록 삭제
	public void delCart(String id, int bid) throws SQLException {


		Cart cart = cartRepository.findByLoginLidAndLibraryBid(id, bid);

		cartRepository.delete(cart);
	}


	// 책 대여 후 삭제 함수
	public void delCartpro(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from cart where login_lid=?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	// 책 개수 하나 줄이자
	public void downcount(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;

		String selectsql = "SELECT stock, booklist.bid From booklist inner join loan on booklist.bid = loan.library_bid where login_lid = ? and status = 1 group by booklist.bid";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(selectsql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int stock = rs.getInt("Stock") - 1;
				int bid = rs.getInt("booklist.bid");
				String updatesql = "update booklist set stock=? where bid=?";

				pstmt1 = conn.prepareStatement(updatesql);
				pstmt1.setInt(1, stock);
				pstmt1.setInt(2, bid);
				pstmt1.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	// 책 대여 정보 조회
	public List<AllinOne> getAllLoan(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AllinOne> all = new ArrayList();

		String sql = "select booklist.bid, title, DATE_FORMAT(start_date, '%Y-%m-%d') as StartDate, DATE_FORMAT(end_date, '%Y-%m-%d') as EndDate, status, Writer, reviewed from booklist inner join loan on loan.library_bid = booklist.bid where login_lid = ? order by booklist.bid";

		try {
			DecimalFormat df = new DecimalFormat("#0");
			Calendar currentCalendar = Calendar.getInstance();
			int tmonth = Integer.parseInt(df.format((currentCalendar.get(Calendar.MONTH) + 1) * 30));
			int tday = Integer.parseInt(df.format(currentCalendar.get(Calendar.DATE)));
			int now = tmonth + tday;

			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AllinOne a = new AllinOne();
				a.setBid(rs.getInt("BID"));
				a.setTitle(rs.getString("Title"));
				a.setWriter(rs.getString("Writer"));
				a.setStartDate(rs.getString("StartDate"));
				a.setEndDate(rs.getString("EndDate"));
				a.setStatus(rs.getBoolean("Status"));
				a.setReviewed(rs.getBoolean("reviewed"));

				String[] month = rs.getString("EndDate").split("-");
				int end = Integer.parseInt(month[1]) * 30 + Integer.parseInt(month[2].substring(0, 2));

				a.setPeriod(now - end);


				all.add(a);
			}
			//return BookList;
			return all;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}

	// 책 반납 안한 책
	public List<AllinOne> getnonLoanbooks(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AllinOne> BookList = new ArrayList<>();

		String sql = "select * from booklist inner join loan on loan.library_bid = booklist.bid where login_lid = ? and status=1";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AllinOne n = new AllinOne();
				n.setBid(rs.getInt("BID"));
				n.setTitle(rs.getString("Title"));
				n.setStartDate(rs.getString("start_date"));
				n.setEndDate(rs.getString("end_date"));
				n.setStatus(rs.getBoolean("Status"));
				BookList.add(n);
			}
			return BookList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}

	// 책 반납
	public void ReturnBook(String id, int bid) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update loan set status=0, return_date=CURRENT_TIMESTAMP() where login_lid=? and library_bid=?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, bid);
			pstmt.executeUpdate();

			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("책 반납 DB에러");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	// 책 개수 하나 늘리자
	public void upcount(String id, int bid) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;

		String selectsql = "SELECT stock, booklist.bid From booklist inner join loan on booklist.bid = loan.library_bid where login_lid = ? and status = 1 and booklist.bid = ? group by booklist.bid";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(selectsql);
			pstmt.setString(1, id);
			pstmt.setInt(2, bid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int stock = rs.getInt("stock") + 1;
				bid = rs.getInt("booklist.bid");
				String updatesql = "update booklist set stock=? where bid=?";

				pstmt1 = conn.prepareStatement(updatesql);
				pstmt1.setInt(1, stock);
				pstmt1.setInt(2, bid);
				pstmt1.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	// 대여 중인 책 수 카운트.
	public int getbookcount(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;
		String sql;

		sql = "select count(*) from loan where login_lid=? and status = 1";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}

		} catch (Exception ex) {
			System.out.println("getbookcount() 에러 : " + ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return x;
	}

	// 일정 기간 책 대여 하지마!
	public void overdue(int period, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;

		String selectsql = "SELECT * From login where lid = ?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(selectsql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			// 오늘 날짜 값 구하기 위해서 사용.
			// 번거롭다!
			DecimalFormat df = new DecimalFormat("#0");
			Calendar currentCalendar = Calendar.getInstance();
			long tyear = Integer.parseInt(df.format((currentCalendar.get(Calendar.YEAR) * 365)));
			long tmonth = Integer.parseInt(df.format((currentCalendar.get(Calendar.MONTH) + 1) * 30));
			long tday = Integer.parseInt(df.format(currentCalendar.get(Calendar.DATE)));
			long now = tyear + tmonth + tday;

			while (rs.next()) {
				String Overdue = rs.getString("overdue");

				//          조건 다시 걸어야함.(해결함!!!)
				//			널이 아닐경우, 기존 Overdue에 연체기간만 더해서 오늘보다 Overdue가 낮을 수 있음.
				//			해결방법 : Overdue가 널이 아니고,
				//							Overdue(end)가 오늘(now)보다 작을 경우 (현재 날짜 + 연체일)
				//							아니라면 (Overdue + 연체일)로 ㄱㄱ
				if (Overdue == null) {
					//System.out.println("Overdue는 널이다");
					String updatesql = "update login set overdue=DATE_FORMAT(DATE_ADD(CURRENT_TIMESTAMP, interval ? * 3 DAY), '%Y-%m-%d') where lid=?";
					pstmt1 = conn.prepareStatement(updatesql);
					pstmt1.setInt(1, period);
					pstmt1.setString(2, id);
					pstmt1.executeUpdate();
				} else {
					// Overdue 날짜 값 구하기 위해서 사용.
					// 번거롭다!
					String[] Overdue1 = Overdue.split("-");
					Long end = (long) (Integer.parseInt(Overdue1[0]) * 365 + Integer.parseInt(Overdue1[1]) * 30 + Integer.parseInt(Overdue1[2]));

					if (now > end) {
						System.out.println("기존 overdue값보다 오늘 날짜가 더 크다!");
						//System.out.println("Overdue는 널이 아니다");
						String updatesql = "update login set overdue=DATE_FORMAT(DATE_ADD(CURRENT_TIMESTAMP, interval ? * 3 DAY), '%Y-%m-%d') where lid=?";
						pstmt1 = conn.prepareStatement(updatesql);
						pstmt1.setInt(1, period);
						pstmt1.setString(2, id);
						pstmt1.executeUpdate();
					} else {
						System.out.println("기존 overdue값보다 오늘 날짜가 더 작다!");
						//System.out.println("Overdue는 널이 아니다");
						String updatesql = "update login set overdue=DATE_FORMAT(DATE_ADD(Overdue, interval ? * 3 DAY), '%Y-%m-%d') where lid=?";
						pstmt1 = conn.prepareStatement(updatesql);
						pstmt1.setInt(1, period);
						pstmt1.setString(2, id);
						pstmt1.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
}
