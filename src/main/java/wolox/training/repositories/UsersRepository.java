package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

	public Iterable<User> findAllByName(String name);

}
