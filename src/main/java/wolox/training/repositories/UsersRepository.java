package wolox.training.repositories;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

	public Iterable<User> findAllByName(String name);

	public Optional<User> findByName(String name);

	@Query("select u from User u where (u.name is null or u.name like %:name%) and (u.birthday is null or (u.birthday >= :startDate and u.birthday <= :endDate))")
	public Optional<User> findFirstBybirthDateBetweenAndNameContainingIgnoreCase(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("name") String name);
}
