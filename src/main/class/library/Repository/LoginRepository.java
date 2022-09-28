package library.Repository;

import library.Entity.Library;
import library.Entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, String> {


	Login findByLid(String id);
}
