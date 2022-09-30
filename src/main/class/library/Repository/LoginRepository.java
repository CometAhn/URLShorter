package library.Repository;

import library.Entity.Library;
import library.Entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginRepository extends JpaRepository<Login, String> {
	Login findByLid(String id);

	List<Login> findAllByLid(String id);

	Login findByLidAndPassword(String id, String password);

	List<Login> findAllByEmail(String email);
}
