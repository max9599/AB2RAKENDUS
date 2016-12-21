package ee.ttu.andmebaasid.toys.repository;

import ee.ttu.andmebaasid.toys.domain.ProductEndView;
import ee.ttu.andmebaasid.toys.domain.ProductOverview;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ProductEndView entity.
 */
@SuppressWarnings("unused")
public interface ProductOverviewRepository extends JpaRepository<ProductOverview,String> {

}
