package ee.ttu.andmebaasid.toys.web.rest;

import com.codahale.metrics.annotation.Timed;
import ee.ttu.andmebaasid.toys.domain.Speciality;
import ee.ttu.andmebaasid.toys.service.SpecialityService;
import ee.ttu.andmebaasid.toys.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Speciality.
 */
@RestController
@RequestMapping("/api")
public class SpecialityResource {

    private final Logger log = LoggerFactory.getLogger(SpecialityResource.class);

    @Inject
    private SpecialityService specialityService;

    /**
     * POST  /specialities : Create a new speciality.
     *
     * @param speciality the speciality to create
     * @return the ResponseEntity with status 201 (Created) and with body the new speciality, or with status 400 (Bad Request) if the speciality has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/specialities")
    @Timed
    public ResponseEntity<Speciality> createSpeciality(@Valid @RequestBody Speciality speciality) throws URISyntaxException {
        log.debug("REST request to save Speciality : {}", speciality);
        Speciality result = specialityService.save(speciality);
        return ResponseEntity.created(new URI("/api/specialities/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert("speciality", result.getCode().toString()))
            .body(result);
    }

    /**
     * PUT  /specialities : Updates an existing speciality.
     *
     * @param speciality the speciality to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated speciality,
     * or with status 400 (Bad Request) if the speciality is not valid,
     * or with status 500 (Internal Server Error) if the speciality couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/specialities")
    @Timed
    public ResponseEntity<Speciality> updateSpeciality(@Valid @RequestBody Speciality speciality) throws URISyntaxException {
        log.debug("REST request to update Speciality : {}", speciality);
        if (speciality.getCode() == null) {
            return createSpeciality(speciality);
        }
        Speciality result = specialityService.save(speciality);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("speciality", speciality.getCode().toString()))
            .body(result);
    }

    /**
     * GET  /specialities : get all the specialities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of specialities in body
     */
    @GetMapping("/specialities")
    @Timed
    public List<Speciality> getAllSpecialities() {
        log.debug("REST request to get all Specialities");
        return specialityService.findAll();
    }

    /**
     * GET  /specialities/:id : get the "id" speciality.
     *
     * @param code the id of the speciality to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the speciality, or with status 404 (Not Found)
     */
    @GetMapping("/specialities/{code}")
    @Timed
    public ResponseEntity<Speciality> getSpeciality(@PathVariable Integer code) {
        log.debug("REST request to get Speciality : {}", code);
        Speciality speciality = specialityService.findOne(code);
        return Optional.ofNullable(speciality)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /specialities/:id : delete the "id" speciality.
     *
     * @param code the id of the speciality to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/specialities/{code}")
    @Timed
    public ResponseEntity<Void> deleteSpeciality(@PathVariable Integer code) {
        log.debug("REST request to delete Speciality : {}", code);
        specialityService.delete(code);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("speciality", code.toString())).build();
    }

}
