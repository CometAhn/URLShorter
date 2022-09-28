package library.DAO;

import library.Repository.RecommendRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecommendDAOTest {

	@Autowired
	private RecommendRepository recommendRepository;

	@Test
	public void read() {

		int month = 9;
		System.out.println(recommendRepository.findByMonthContaining(month));
	}
}