package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
}
