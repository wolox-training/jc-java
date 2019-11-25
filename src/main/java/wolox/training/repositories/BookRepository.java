package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

	@Query("select b from Book b where (:genre = '' or b.genre = :genre) and"
			+ "(:author = '' or b.author = :author) and (:image = '' or b.image = :image) and "
			+ "(:title = '' or b.title = :title) and (:subtitle = '' or b.subtitle = :subtitle) and"
			+ "(:publisher = '' or b.publisher = :publisher) and (:year = '' or b.year = :year) and"
			+ "(:isbn = '' or b.isbn = :isbn) and (:pages = 0 or b.pages = :pages)")
	public List<Book> getAll(@Param("genre") String genre, @Param("author") String author, @Param("image") String image, @Param("title") String title,
			@Param("subtitle") String subtitle, @Param("publisher") String publisher, @Param("year") String year, @Param("isbn") String isbn,
			@Param("pages") int pages);

	public Optional<Book> findByAuthor(String author);

	public Iterable<Book> findAllByTitle(String title);

	public Optional<Book> findFirstByIsbn(String isbn);

	@Query("select b from Book b where (b.genre = :genre or b.genre is null) and "
			+ "(b.publisher = :publisher or b.publisher is null) and "
			+ "(b.year = :year or b.year is null)")
	public Optional<Book> findFirstByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre, @Param("year")String year);

}
