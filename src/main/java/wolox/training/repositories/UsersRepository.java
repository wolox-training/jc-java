package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<User, Long> {

	@Query("select u from User u where (:name = '' or u.name = :name) and"
			+ "(:userName = '' or u.userName = :userName) and (:birthDate is null or u.birthDate = :birthDate)")
	public Page<User> getAll(@Param("name") String name, @Param("userName") String userName, @Param("birthDate") LocalDate birthDate, Pageable pageable);

	public Page<User> findAllByName(String name, Pageable pageable);

	public Optional<User> findByName(String name);

	@Query("select u from User u where (u.name is null or u.name like %:name%) and (u.birthDate is null or (u.birthDate >= :startDate and u.birthDate <= :endDate))")
	public Optional<User> findFirstBybirthDateBetweenAndNameContainingIgnoreCase(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("name") String name);
}
