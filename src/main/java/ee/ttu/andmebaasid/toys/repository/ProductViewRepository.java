package ee.ttu.andmebaasid.toys.repository;

import ee.ttu.andmebaasid.toys.domain.ProductView;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductView entity.
 */
@SuppressWarnings("unused")
public interface ProductViewRepository extends JpaRepository<ProductView,String> {

}
