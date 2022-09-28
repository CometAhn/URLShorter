/**
 * * 기능 - 회원가입 - 회원삭제 - 회원정보 조회 - 회원정보 수정 - 로그인
 */

package library.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.Entity.Login;
import library.dbconnection;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class LoginDAO {

	// 회원가입
	public void regist(Login g) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "insert into login(lid, password, name, gender, birth, email, phone, address, regist_day, grade ) value(?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP(), 0)";
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, g.getLid());
			pstmt.setString(2, g.getPassword());
			pstmt.setString(3, g.getName());
			pstmt.setString(4, g.getGender());
			pstmt.setString(5, g.getBirth());
			pstmt.setString(6, g.getEmail());
			pstmt.setString(7, g.getPhone());
			pstmt.setString(8, g.getAddress());
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

	// 회원 삭제
	public void deleteID(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			String sql = "UPDATE login SET Used = 0 WHERE lid=?";
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

	// 조회
	public Login edit(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Login g = new Login();

		String sql = "SELECT * FROM login WHERE lid=?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String[] brith = rs.getString("birth").split("/"); // 생년월일 나누기 ㅏㅏㅏㅏㅏㅏㅏ
				String[] mail = rs.getString("email").split("@"); // 메일 나누기

				g.setLid(rs.getString("lid"));
				g.setPassword(rs.getString("password"));
				g.setName(rs.getString("name"));
				g.setGender(rs.getString("gender"));
				// todo : 게터 수정해야 할 수도?
				//        일단은 둔다.
				//        프로퍼티 spring.jpa.properties.hibernate.hbm2ddl.auto:update 사용 ㄴㄴ
				//        컬럼 자동 생성됨.
				g.setBirthyy(brith[0]);
				g.setBirthmm(brith[1]);
				g.setBirthdd(brith[2]);
				g.setEmail1(mail[0]);
				g.setEmail2(mail[1]);
				g.setPhone(rs.getString("phone"));
				g.setAddress(rs.getString("address"));
				g.setBirth(rs.getString("regist_day"));
				g.setOverdue(rs.getString("overdue"));
				pstmt.executeQuery();
			} else {

			}
			return g;
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
		return null;
	}

	// 회원 수정
	public void update(Login g) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "UPDATE login SET password=?, name=?, gender=?, birth=?, email=?, phone=?, Address=? WHERE lid=?";
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, g.getPassword());
			pstmt.setString(2, g.getName());
			pstmt.setString(3, g.getGender());
			pstmt.setString(4, g.getBirth());
			pstmt.setString(5, g.getEmail());
			pstmt.setString(6, g.getPhone());
			pstmt.setString(7, g.getAddress());
			pstmt.setString(8, g.getLid());
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

	// 로그인
	public Login login(String id, String pw) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Login g = new Login();

		String sql = "SELECT * FROM login WHERE lid=? and password=?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				g.setLid(rs.getString("lid"));
				g.setGrade(rs.getBoolean("grade"));
				g.setName(rs.getString("name"));
				g.setUsed(rs.getBoolean("used"));
				pstmt.executeQuery();
			} else {

			}
			return g;
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
		return null;
	}


	// 아이디 조회
	public List<Login> getid(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Login> IdList = new ArrayList<>();
		String sql = "select lid from login where lid=?";

		try {
			conn = dbconnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Login g = new Login();
				g.setLid(rs.getString("lid"));
				IdList.add(g);
			}
			return IdList;
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
}
