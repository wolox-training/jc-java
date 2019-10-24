package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

	public Optional<Users> findByName(String name);

	public Iterable<Users> findAll();

	public Optional<Users> findById(Long id);

	public Users save(Users user);

	public void delete(Users user);
}
