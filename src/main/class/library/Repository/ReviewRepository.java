package library.Repository;

import library.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {


	Review findById(int id);

	Review findByLibraryBid(int id);

	List<Review> findAllByLibraryBid(int id);

	Review findByLoanId(int id);

}
