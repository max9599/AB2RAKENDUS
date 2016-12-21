package ee.ttu.andmebaasid.toys.web.rest;

import com.codahale.metrics.annotation.Timed;
import ee.ttu.andmebaasid.toys.domain.ProductOverview;
import ee.ttu.andmebaasid.toys.domain.ProductView;

import ee.ttu.andmebaasid.toys.repository.ProductCategoryRepository;
import ee.ttu.andmebaasid.toys.repository.ProductOverviewRepository;
import ee.ttu.andmebaasid.toys.repository.ProductViewRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing ProductView.
 */
@RestController
@RequestMapping("/api")
public class ProductViewResource {

    private final Logger log = LoggerFactory.getLogger(ProductViewResource.class);

    @Inject
    private ProductViewRepository productViewRepository;

    @Inject
    private ProductCategoryRepository productCategoryRepository;

    @Inject
    private ProductOverviewRepository productOverviewRepository;

    /**
     * POST  /product-views : Create a new productView.
     *
     * @param productView the productView to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productView, or with status 400 (Bad Request) if the productView has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-views")
    @Timed
    public ResponseEntity<ProductView> createProductView(@RequestBody ProductView productView) throws URISyntaxException {
        log.debug("REST request to save ProductView : {}", productView);
        if (productView.getCode() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productView", "idexists", "A new productView cannot already have an ID")).body(null);
        }
        ProductView result = productViewRepository.save(productView);
        return ResponseEntity.created(new URI("/api/product-views/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert("productView", result.getCode()))
            .body(result);
    }

    /**
     * PUT  /product-views : Updates an existing productView.
     *
     * @param productView the productView to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productView,
     * or with status 400 (Bad Request) if the productView is not valid,
     * or with status 500 (Internal Server Error) if the productView couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-views")
    @Timed
    public ResponseEntity<ProductView> updateProductView(@RequestBody ProductView productView) throws URISyntaxException {
        log.debug("REST request to update ProductView : {}", productView);
        if (productView.getCode() == null) {
            return createProductView(productView);
        }
        ProductView result = productViewRepository.save(productView);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productView", productView.getCode()))
            .body(result);
    }

    /**
     * GET  /product-views : get all the productViews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productViews in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/product-views")
    @Timed
    public ResponseEntity<List<ProductView>> getAllProductViews(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductViews");
        Page<ProductView> page = productViewRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-views");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-views/:id : get the "id" productView.
     *
     * @param code the code of the productView to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productView, or with status 404 (Not Found)
     */
    @GetMapping("/product-views/{code}")
    @Timed
    public ResponseEntity<ProductView> getProductView(@PathVariable String code) {
        log.debug("REST request to get ProductView : {}", code);
        ProductView productView = productViewRepository.findOne(code);
        return Optional.ofNullable(productView)
            .map(result -> {
                result.setCategories(productCategoryRepository.findProductCategories(code));
                return new ResponseEntity<>(
                    result,
                    HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/product-overview")
    @Timed
    public ResponseEntity<List<ProductOverview>> getProductOverview() {
        log.debug("REST request to get ProductOverview");
        List<ProductOverview> productView = productOverviewRepository.findAll();
        return Optional.ofNullable(productView)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /product-views/:id : delete the "id" productView.
     *
     * @param id the id of the productView to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-views/{code}")
    @Timed
    public ResponseEntity<Void> deleteProductView(@PathVariable String code) {
        log.debug("REST request to delete ProductView : {}", code);
        productViewRepository.delete(code);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productView", code)).build();
    }

}
