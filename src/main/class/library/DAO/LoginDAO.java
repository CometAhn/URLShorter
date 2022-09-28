/**
 * * 기능 - 회원가입 - 회원삭제 - 회원정보 조회 - 회원정보 수정 - 로그인
 */

package library.DAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import library.Entity.Library;
import library.Entity.Login;
import library.Repository.LoginRepository;
import library.dbconnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class LoginDAO {

	@Autowired
	LoginRepository loginRepository;

	// 회원가입
	public void regist(Login g) throws Exception {
		System.out.println("lid 값이?" + g.getLid());
		Login login = new Login();

		login.setLid(g.getLid());
		login.setPassword(g.getPassword());
		login.setName(g.getName());
		login.setGender(g.getGender());
		login.setBirth(g.getBirth());
		login.setEmail(g.getEmail());
		login.setPhone(g.getPhone());
		login.setAddress(g.getAddress());
		login.setGender("1");
		login.setRegistDay(String.valueOf(LocalDateTime.now()));

		Login newlogin = loginRepository.save(login);
	}

	// 회원 삭제
	public void deleteID(String id) throws Exception {

		Login login = loginRepository.findByLid(id);

		loginRepository.delete(login);
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

				// 이거 딴 방법 써야함! jsp에서  나눌까?그게 나을듯?
				String[] brith = rs.getString("birth").split("/"); // 생년월일 나누기 ㅏㅏㅏㅏㅏㅏㅏ
				String[] mail = rs.getString("email").split("@"); // 메일 나누기

				g.setLid(rs.getString("lid"));
				g.setPassword(rs.getString("password"));
				g.setName(rs.getString("name"));
				g.setGender(rs.getString("gender"));
				// todo : 게터 수정해야 함. 대체 수단을 찾아야함.
				/*
				g.setBirthyy(brith[0]);
				g.setBirthmm(brith[1]);
				g.setBirthdd(brith[2]);
				g.setEmail1(mail[0]);
				g.setEmail2(mail[1]);
				 */
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
