package ee.ttu.andmebaasid.toys.web.rest;

import com.codahale.metrics.annotation.Timed;
import ee.ttu.andmebaasid.toys.domain.ProductEndView;
import ee.ttu.andmebaasid.toys.domain.ProductView;
import ee.ttu.andmebaasid.toys.repository.FunctionalDao;
import ee.ttu.andmebaasid.toys.repository.ProductEndViewRepository;
import ee.ttu.andmebaasid.toys.web.rest.util.HeaderUtil;
import ee.ttu.andmebaasid.toys.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProductEndView.
 */
@RestController
@RequestMapping("/api")
public class ProductEndViewResource {

    private final Logger log = LoggerFactory.getLogger(ProductEndViewResource.class);

    @Inject
    private ProductEndViewRepository productViewRepository;

    @Inject
    private FunctionalDao functionalDao;

    /**
     * GET  /product-views : get all the productEndViews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productEndViews in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/product-end-views")
    @Timed
    public ResponseEntity<List<ProductEndView>> getAllProductEndViews(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductEndViews");
        Page<ProductEndView> page = productViewRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-end-views");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-views/:id : get the "id" productView.
     *
     * @param code the code of the productView to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productView, or with status 404 (Not Found)
     */
    @GetMapping("/product-end-views/{code}")
    @Timed
    public ResponseEntity<ProductEndView> getProductView(@PathVariable String code) {
        log.debug("REST request to get ProductEndView : {}", code);
        ProductEndView productView = productViewRepository.findOne(code);
        return Optional.ofNullable(productView)
            .map(result -> {
                return new ResponseEntity<>(
                    result,
                    HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //
    // Lõpetamise protseduur ...

    /**
     * DELETE  /product-end-views/:code : delete the "code" productView.
     *
     * @param code the code of the productView to end
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-end-views/{code}")
    @Timed
    public ResponseEntity<Void> endProduct(@PathVariable String code) {
        log.debug("REST request to end ProductView : {}", code);
        functionalDao.endProduct(code);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productEndView", code)).build();
    }

}
