package library.Repository;

import library.Entity.Library;
import library.Entity.Review;
import library.dbconnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {


	Review findById(int id);

	Review findByLibraryBid(int id);

	List<Review> findAllByLibraryBid(int id);

	Review findByLoanId(int id);

}
