/**
 * * 책 장바구니 DTO
 * String id 	회원 아이디.
 * int bid 	책 아이디.
 * <p>
 * * 데이터베이스(cart)
 * varchar ID	회원 아이디
 * int BID	 	책 아이디
 */

package library;

public class Cart {


	private int id;
	private String lid;
	private int bid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}
}
