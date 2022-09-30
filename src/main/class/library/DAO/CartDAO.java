package library.DAO;

import library.Entity.*;
import library.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
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
