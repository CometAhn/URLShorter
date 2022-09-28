package library.Repository;

import library.Entity.Cart;
import library.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	List<Cart> findAllByLoginLid(String lid);

	Cart findByLoginLid(String lid);

	Cart findByLoginLidAndLibraryBid(String lid, int bid);
}
