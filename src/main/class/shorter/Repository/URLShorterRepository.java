package shorter.Repository;

import shorter.Entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface URLShorterRepository extends JpaRepository<Url, Integer> {
	Url findByShorter(String shorter);

	List<Url> findByAddr(String url);


}
