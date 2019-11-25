package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

	public Optional<Book> findByAuthor(String author);

	public Iterable<Book> findAllByTitle(String title);

	public Optional<Book> findFirstByIsbn(String isbn);

	@Query("select b from Book b where (b.genre = :genre or b.genre is null) and "
			+ "(b.publisher = :publisher or b.publisher is null) and "
			+ "(b.year = :year or b.year is null)")
	public Optional<Book> findFirstByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre, @Param("year")String year);

}
