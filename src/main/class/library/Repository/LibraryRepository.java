package library.Repository;

import library.Entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Integer> {
	List<Library> findByTitleContaining(String text);

	List<Library> findByCategoryContaining(String text);

	List<Library> findByWriterContaining(String text);

	List<Library> findByPublisherContaining(String text);

	List<Library> findByBid(int bid);
}
