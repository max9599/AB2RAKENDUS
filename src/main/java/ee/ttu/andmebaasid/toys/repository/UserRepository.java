package ee.ttu.andmebaasid.toys.repository;

import ee.ttu.andmebaasid.toys.domain.User;

import java.time.ZonedDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    @Override
    void delete(User t);

}
