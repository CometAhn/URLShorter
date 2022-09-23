/**
 * 로그인 DTO String id 		아이디 String password 비밀번호 String name		이름 String gender	성별 String birthyy	년 String birthmm	월 String birthdd	일 String birth	년+월+일 String mail		이메일 이름@이메일 주소 String mail1	이메일 이름 String mail2	이메일 주소 String phone	전화번호 String
 * address	주소 boolean admin	관리자 여부 boolean used	사용여부, 0 = 탈퇴, 1 = 사용
 */

package library;

public class Login {

	private String lid;
	private String password;
	private String name;
	private String gender;
	private String birthyy;
	private String birthmm;
	private String birthdd;
	private String birth;
	private String email;
	private String email1;
	private String email2;
	private String phone;
	private String address;
	private boolean grade;
	private boolean used;
	private String overdue;

	public String getOverdue() {
		return overdue;
	}

	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isGrade() {
		return grade;
	}

	public void setGrade(boolean grade) {
		this.grade = grade;
	}

	public String getBirthyy() {
		return birthyy;
	}

	public void setBirthyy(String birthyy) {
		this.birthyy = birthyy;
	}

	public String getBirthmm() {
		return birthmm;
	}

	public void setBirthmm(String birthmm) {
		this.birthmm = birthmm;
	}

	public String getBirthdd() {
		return birthdd;
	}

	public void setBirthdd(String birthdd) {
		this.birthdd = birthdd;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birthyy + "/" + birthmm + "/" + birthdd;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String mail1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail() {
		return email1 + "@" + email2;
	}

	public void setEmail(String mail) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
