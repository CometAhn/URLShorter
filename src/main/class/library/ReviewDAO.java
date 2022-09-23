
package library;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewDAO {
	// 리뷰 목록 가져오기

	public List<Review> getReview(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from review where BID=?";
		List<Review> list = new ArrayList<>();

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Review n = new Review();
				n.setTitle(rs.getString("Title"));
				n.setDate(rs.getString("Date"));
				n.setBid(rs.getInt("Bid"));
				n.setScore(rs.getString("Score"));
				n.setContents(rs.getString("Contents"));
				n.setLid(rs.getString("lid"));
				n.setId(rs.getInt("id"));
				list.add(n);
			}
			return list;
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

	// 리뷰 추가
	public void addReview(Review r) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "insert into review(lid, bid, title, contents, date, score) value(?, ?, ?, ?, CURRENT_TIMESTAMP(), ?) ";
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, r.getLid());
			pstmt.setInt(2, r.getBid());
			pstmt.setString(3, r.getTitle());
			pstmt.setString(4, r.getContents());
			pstmt.setString(5, r.getScore());
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

	// 리뷰 작성 완료
	public void reviewed(String id, int bid) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update loan set reviewed=1 where lid=? and bid=? and status=0";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, bid);
			pstmt.executeUpdate();

			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("리뷰 작성 완료 DB에러");
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

	// 리뷰 수정
	public void update(Review r) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "UPDATE review SET title=?, contents=?,date=CURRENT_TIMESTAMP(), score=? where id=?";
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, r.getTitle());
			pstmt.setString(2, r.getContents());
			pstmt.setString(3, r.getScore());
			pstmt.setInt(4, r.getId());
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

	// 리뷰 삭제
	public void delReview(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from review where id=?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

			/*if (pstmt.executeUpdate() == 0) {
				throw new SQLException("DB에러");
			}*/
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