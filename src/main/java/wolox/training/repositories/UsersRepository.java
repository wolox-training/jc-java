package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

	public Iterable<Users> findAllByName(String name);

}
