/**
 * 로그인 DTO String id 		아이디 String password 비밀번호 String name		이름 String gender	성별 String birthyy	년 String birthmm	월 String birthdd	일 String birth	년+월+일 String mail		이메일 이름@이메일 주소 String mail1	이메일 이름 String mail2	이메일 주소 String phone	전화번호 String
 * address	주소 boolean admin	관리자 여부 boolean used	사용여부, 0 = 탈퇴, 1 = 사용
 */

package library.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String lid;
	private String password;
	private String name;
	private String gender;
	private String birth;
	private String email;
	// 이거 컬럼 아님. get 저장용임.
	// property spring.jpa.properties.hibernate.hbm2ddl.auto:update 사용 안 함.
	private String email1;
	private String email2;
	private String birthyy;
	private String birthmm;
	private String birthdd;
	//
	private String phone;
	private String address;
	private String registDay;
	private boolean grade;
	private boolean used;
	private String overdue;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "login") // 이거 테이블 명 or .java명 인지?
	private List<Review> reviewList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "login") // 이거 테이블 명 or .java명 인지?
	private List<Cart> cartList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "login") // 이거 테이블 명 or .java명 인지?
	private List<Loan> loanList;

	// 이거 컬럼 아님. get 저장용임.
	public String getBirth() {
		return birthyy + "/" + birthmm + "/" + birthdd;
	}

	public String getEmail() {
		return email1 + "@" + email2;
	}
	//
}
