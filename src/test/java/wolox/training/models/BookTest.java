package wolox.training.models;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.util.Optional;
import javax.persistence.PersistenceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class BookTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private BookRepository bookRepository;

	private Book book;

	@Before
	public void setUp() {
		book = new Book();
		book.setAuthor("Stephen King");
		book.setGenre("Terror");
		book.setImage("image.png");
		book.setIsbn("45788665");
		book.setPages(1502);
		book.setPublisher("Viking Press");
		book.setSubtitle("Worst clown ever");
		book.setTitle("It");
		book.setYear("1986");
		entityManager.persist(book);
		entityManager.flush();
	}

	@Test
	public void whenCreateBook_thenBookIsPersisted() {
		Book bookCreated = new Book();
		bookCreated.setAuthor("Frank Herbert");
		bookCreated.setGenre("Sci Fi");
		bookCreated.setImage("image.png");
		bookCreated.setIsbn("44795665");
		bookCreated.setPages(412);
		bookCreated.setPublisher("Unknown");
		bookCreated.setSubtitle("The beginning");
		bookCreated.setTitle("Dune");
		bookCreated.setYear("1965");
		entityManager.persist(bookCreated);
		entityManager.flush();
		Book bookFound = bookRepository.findById(bookCreated.getId()).get();
		assert (bookCreated.equals(bookFound));
	}

	@Test(expected = PersistenceException.class)
	public void whenCreateBookWithNullValues_thenBookIsNotPersisted() {
		Book bookNull = new Book();
		entityManager.persist(bookNull);
		entityManager.flush();
	}

	@Test
	public void whenUpdateBook_thenBookIsUpdated() {
		entityManager.persist(book);
		entityManager.flush();
		book.setGenre("Horror");
		entityManager.merge(book);
		Book bookFound = bookRepository.findById(book.getId()).get();
		assert ("Horror").equals(bookFound.getGenre());
	}

	@Test
	public void whenDeleteBook_thenBookIsDeleted() {
		entityManager.persist(book);
		entityManager.flush();
		entityManager.remove(book);
		Optional<Book> bookFound = bookRepository.findById(book.getId());
		assert (!bookFound.isPresent());
	}

}
