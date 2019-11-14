package wolox.training.models;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.time.LocalDate;
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
import wolox.training.repositories.UsersRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UsersRepository userRepository;

	private User user;

	@Before
	public void setUp() {
		user = new User();
		user.setUserName("jose");
		user.setName("Jose Luis Casanova");
		user.setBirthDate(LocalDate.parse("1995-04-27"));
		entityManager.persist(user);
		entityManager.flush();
	}

	@Test(expected = NullPointerException.class)
	public void whenCreateUserWithoutUserName_thenThrowException() {
		user.setUserName(null);
		entityManager.persist(user);
		entityManager.flush();
	}

	@Test(expected = PersistenceException.class)
	public void whenCreateUserWithNullValues_thenUserIsNotPersisted() {
		User userNull = new User();
		entityManager.persist(userNull);
		entityManager.flush();
	}

	@Test
	public void whenUpdateUser_thenUserIsUpdated() {
		entityManager.persist(user);
		entityManager.flush();
		user.setName("Jose Casanova");
		entityManager.persist(user);
		entityManager.flush();
		User userUpdated = userRepository.findById(user.getId()).get();
		assert ("Jose Casanova").equals(userUpdated.getName());
	}

	@Test
	public void whenDeleteUser_thenUserIsDeleted() {
		entityManager.persist(user);
		entityManager.flush();
		entityManager.remove(user);
		Optional<User> searchUser = userRepository.findById(user.getId());
		assert (!searchUser.isPresent());
	}

	@Test
	public void whenFindByIdWhichExists_thenUserIsReturned() throws Exception {
		assertEquals (Optional.of(user), userRepository.findById(user.getId()));
	}
}
