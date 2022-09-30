package library.DAO;

import library.Entity.Recommend;
import library.Repository.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendDAO {
	@Autowired
	private RecommendRepository recommendRepository;

	// 이달의 추천 책
	public List<Recommend> recommend(Integer month) throws Exception {
		return recommendRepository.findByMonth(month);
	}
}
