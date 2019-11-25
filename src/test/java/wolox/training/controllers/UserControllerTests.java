package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UsersRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsersRepository userRepository;

	@MockBean
	private BookRepository booksRepository;

	private User oneTestUser;
	private Book oneTestBook;

	@Before
	public void setUp() {
		oneTestBook = new Book();
		oneTestBook.setAuthor("Stephen King");
		oneTestBook.setGenre("Terror");
		oneTestBook.setImage("https://imagesforus.com/1dsafr12.png");
		oneTestBook.setIsbn("45788665");
		oneTestBook.setPages(1502);
		oneTestBook.setPublisher("Viking Press");
		oneTestBook.setSubtitle("Worst clown ever");
		oneTestBook.setTitle("It");
		oneTestBook.setYear("1986");
		oneTestUser = new User();
		oneTestUser.setUserName("jose");
		oneTestUser.setName("Jose Luis Casanova");
		oneTestUser.setBirthDate(LocalDate.parse("1995-04-27"));
		oneTestUser.addBook(oneTestBook);
	}

	@Test
	public void whenFindByIdWhichExists_thenUserIsReturned() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(
				java.util.Optional.ofNullable(oneTestUser));
		String url = ("/userApi/1");
		mvc.perform(get(url)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(
				"{\"id\":0,\"userName\":\"jose\",\"name\":\"Jose Luis Casanova\","
				+ "\"birthDate\":\"1995-04-27\",\"books\":[{\"id\":0,\"genre\":\"Terror\","
				+ "\"author\":\"Stephen King\",\"image\":\"https://imagesforus.com/1dsafr12.png\","
				+ "\"publisher\":\"Viking Press\",\"year\":\"1986\",\"pages\":1502,\"isbn\":\"45788665\"}]}"
			));
	}

	@Test
	public void whenCreateUser_thenUserIsCreated() throws Exception {
		String json = "{\"username\": \"jose\", \"name\": \"Jose Luis Casanova\", "
				+ "\"birthday\": \"1995-04-27\"}";
		mvc.perform(post("/userApi/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated());
	}

	@Test
	public void whenCreateUserWithNullValues_thenUserIsNotCreated() throws Exception {
		mvc.perform((post("userApi/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"jose\", \"name\": \"Jose Luis Casanova\"}")))
				.andExpect(status().isNotFound());
		mvc.perform((post("userApi/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"Jose Luis Casanova\", \"birthday\": \"1995-04-27\"}")))
				.andExpect(status().isNotFound());
		mvc.perform((post("userApi/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"jose\", \"birthday\": \"1990-04-27\"}")))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenUpdateUser_thenUserIsUpdated() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oneTestUser));
		String json = "{\"id\": 1, \"username\": \"jose\", \"name\": \"Jose Casanova\", "
				+ "\"birthday\": \"1995-04-27\"}";
		mvc.perform(put("/userApi/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@Test
	public void whenUpdateUserWithIdMismatch_thenUserIsNotUpdated() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oneTestUser));
		String json = "{\"id\": 1, \"username\": \"jose\", \"name\": \"Jose Casanova\", "
				+ "\"birthday\": \"1995-04-27\"}";
		mvc.perform(put("/userApi/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenDeleteUser_thenUserIsDeleted() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oneTestUser));
		mvc.perform(delete("/userApi/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void whenDeleteNonExistingUser_thenUserIsNotFound() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oneTestUser));
		mvc.perform(delete("/userApi/2"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenAddRepeatedBookToUser_thenBookIsNotAdded() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oneTestUser));
		Mockito.when(booksRepository.findById(1L)).thenReturn(Optional.of(oneTestBook));
		mvc.perform(put("/userApi/1/1"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void whenAddNonExistingBookToUser_thenBookIsNotAdded() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oneTestUser));
		mvc.perform(put("/userApi/1/3"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenAddBookToNonExistingUser_thenBookIsNotAdded() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oneTestUser));
		Mockito.when(booksRepository.findById(1L)).thenReturn(Optional.of(oneTestBook));
		mvc.perform(put("/userApi/3/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void whenAddBookToUser_thenBookIsAdded() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oneTestUser));
		Mockito.when(booksRepository.findById(1L)).thenReturn(Optional.of(oneTestBook));
		mvc.perform(put("/userApi/1/2"))
				.andExpect(status().isNotFound());
	}

}
