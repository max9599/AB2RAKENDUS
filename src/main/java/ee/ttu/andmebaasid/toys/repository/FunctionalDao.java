package ee.ttu.andmebaasid.toys.repository;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Transactional
@Component("functionalDao")
public class FunctionalDao {

    @Inject
    private EntityManager entityManager;

    // Changes status of product to Lõpetatud
    public boolean endProduct(String productCode) {
        Query query = entityManager.createNamedQuery("EndProduct")
            .setParameter("code", productCode);
        return ((Number) query.getSingleResult()).intValue() > 0;
    }

}
