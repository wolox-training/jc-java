package wolox.training.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

public interface BookRepository extends Repository {

	public Book findBookByAuthor(String author);

	public List<Book> findAll();

	public List<Book> findBookByTitle(String title);

	public Book findBookById(long id);

	public Book save(Book book);

	public void deleteBookById(long id);

}
