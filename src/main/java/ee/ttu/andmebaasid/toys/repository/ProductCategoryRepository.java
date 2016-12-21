package ee.ttu.andmebaasid.toys.repository;

import ee.ttu.andmebaasid.toys.domain.ProductCategory;
import ee.ttu.andmebaasid.toys.domain.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Spring Data JPA repository for the ProductCategory entity.
 */
@Repository("productCategoryRepository")
@SuppressWarnings("unused")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

    @Query("SELECT pc.categoryName FROM ProductCategory pc WHERE pc.productCode = :productCode")
    Set<String> findProductCategories(@Param("productCode") String productCode);

}
