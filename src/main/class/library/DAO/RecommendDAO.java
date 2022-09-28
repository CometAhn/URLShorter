package library.DAO;

import library.Entity.Library;
import library.Entity.Recommend;
import library.Repository.LibraryRepository;
import library.Repository.RecommendRepository;
import library.dbconnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
