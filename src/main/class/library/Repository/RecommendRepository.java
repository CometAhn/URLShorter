package library.Repository;

import library.Entity.Recommend;
import library.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {
	List<Recommend> findByMonth(Integer month);
}
