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


	// 책 대여 후 장바구니 삭제
	public void delCartpro(String id) throws SQLException {

		List<Cart> cart = cartRepository.findAllByLoginLid(id);

		for (Cart cartt : cart) {
			cartRepository.delete(cartt);
		}
	}

}
