package ee.ttu.andmebaasid.toys.repository;

import ee.ttu.andmebaasid.toys.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
