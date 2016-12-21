package ee.ttu.andmebaasid.toys.repository;

import ee.ttu.andmebaasid.toys.domain.ProductEndView;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ProductEndView entity.
 */
@SuppressWarnings("unused")
public interface ProductEndViewRepository extends JpaRepository<ProductEndView,String> {

}
