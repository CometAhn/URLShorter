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
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class LoginDAO {

	@Autowired
	LoginRepository loginRepository;

	// 회원가입
	public void regist(Login g) throws Exception {
		Login login = new Login();

		login.setLid(g.getLid());
		login.setPassword(g.getPassword());
		login.setName(g.getName());
		login.setGender(g.getGender());
		login.setBirth(g.getBirth());
		login.setEmail(g.getEmail());
		login.setPhone(g.getPhone());
		login.setAddress(g.getAddress());
		login.setUsed(true);
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

		return loginRepository.findByLid(id);
	}

	// 회원 수정
	public void update(Login g) throws Exception {
		
		Login login = loginRepository.findByLid(g.getLid());

		login.setPassword(g.getPassword());
		login.setName(g.getName());
		login.setGender(g.getGender());
		login.setBirth(g.getBirth());
		login.setEmail(g.getEmail());
		login.setPhone(g.getPhone());
		login.setAddress(g.getAddress());

		Login newlogin = loginRepository.save(login);
	}

	// 로그인
	public Login login(String id, String pw) throws Exception {

		return loginRepository.findByLidAndPassword(id, pw);
	}


	// 아이디 조회
	public List<Login> getid(String id) throws SQLException {

		return loginRepository.findAllByLid(id);
	}
}
