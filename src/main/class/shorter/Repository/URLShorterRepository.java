package shorter.Repository;

import shorter.Entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLShorterRepository extends JpaRepository<Url, Integer> {
    Url findByShorter(String url);


}
