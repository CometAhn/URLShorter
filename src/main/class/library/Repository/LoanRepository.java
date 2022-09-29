package library.Repository;

import library.Entity.Cart;
import library.Entity.Loan;
import library.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

	Loan findByLoginLidAndStatus(String lid, boolean status);

	List<Loan> findAllByLoginLidAndStatus(String lid, boolean status);

	List<Loan> findAllByLoginLid(String lid);

	Loan findByLoginLidAndLibraryBidAndStatus(String lid, int bid, boolean status);

	int countByLoginLidAndStatus(String lid, boolean status);


	List<Loan> findAllByLoginLidAndLibraryBid(String lid, int bid);

	Loan findById(int id);
}
