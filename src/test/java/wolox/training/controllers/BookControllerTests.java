package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UsersRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsersRepository userRepository;

	@MockBean
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
	}

	@Test
	public void whenFindAll_thenBooksAreReturned() throws Exception {
		List<Book> bookList = new ArrayList<Book>();
		bookList.add(book);
		Mockito.when(bookRepository.findAll()).thenReturn(bookList);
		mvc.perform(get("/bookApi/"))
				.andExpect(status().isOk())
				.andExpect(content().json("[{\"id\": 0,"
						+ "    \"author\": \"Stephen King\","
						+ "    \"genre\": \"Terror\","
						+ "    \"image\": \"image.png\","
						+ "    \"isbn\": \"45788665\","
						+ "    \"pages\": 1502,"
						+ "    \"publisher\": \"Viking Press\","
						+ "    \"subtitle\": \"Worst clown ever\","
						+ "    \"title\": \"It\","
						+ "    \"year\": \"1986\"}]"));
	}

	@Test
	public void whenCreateBook_thenBookIsCreated() throws Exception {
		String json = "{\"author\": \"Stephen King\", \"genre\": \"Terror\","
				+ "\"image\": \"image.png\", \"isbn\": \"45788665\","
				+ "\"pages\": \"1502\", \"publisher\": \"Viking Press\", \"subtitle\": \"Worst clown ever\","
				+ "\"title\": \"It\", \"year\": \"1986\"}";
		mvc.perform(post("/bookApi/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated());
	}

	@Test
	public void whenCreateBookWithEmptyGenre_thenBookIsCreated() throws Exception {
		String json = "{\"author\": \"Stephen King\","
				+ "\"image\": \"image.png\", \"isbn\": \"45788665\","
				+ "\"pages\": \"1502\", \"publisher\": \"Viking Press\", \"subtitle\": \"Worst clown ever\","
				+ "\"title\": \"It\", \"year\": \"1986\"}";
		mvc.perform(post("/bookApi/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated());
	}

	@Test
	public void whenDeleteBook_thenBookIsDeleted() throws Exception {
		Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		mvc.perform(delete("/bookApi/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void whenDeleteNonExistingBook_thenBookIsNotFound() throws Exception {
		Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		mvc.perform(delete("/bookApi/3"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenUpdateBook_thenBookIsUpdated() throws Exception {
		Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		String json = "{\"id\": 1, \"author\": \"Stephen King\", \"genre\": \"Horror\","
				+ "\"image\": \"image.png\", \"isbn\": \"45788665\","
				+ "\"pages\": \"1502\", \"publisher\": \"Viking Press\", \"subtitle\": \"Worst clown ever\","
				+ "\"title\": \"It\", \"year\": \"1986\"}";
		mvc.perform(put("/bookApi/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@Test
	public void whenUpdateBookWithIdMismatch_thenBookIsNotUpdated() throws Exception {
		String json = "{\"id\": 3, \"author\": \"Stephen King\", \"genre\": \"Terror\","
				+ "\"image\": \"image.png\", \"isbn\": \"45788665\","
				+ "\"pages\": \"1502\", \"publisher\": \"Viking Press\", \"subtitle\": \"Worst clown ever\","
				+ "\"title\": \"It\", \"year\": \"1986\"}";
		mvc.perform(put("/bookApi/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}
}
