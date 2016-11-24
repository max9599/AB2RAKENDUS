package ee.ttu.andmebaasid.toys.service;

import ee.ttu.andmebaasid.toys.domain.Speciality;
import ee.ttu.andmebaasid.toys.repository.SpecialityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Speciality.
 */
@Service
@Transactional
public class SpecialityService {

    private final Logger log = LoggerFactory.getLogger(SpecialityService.class);

    @Inject
    private SpecialityRepository specialityRepository;

    /**
     * Save a speciality.
     *
     * @param speciality the entity to save
     * @return the persisted entity
     */
    public Speciality save(Speciality speciality) {
        log.debug("Request to save Speciality : {}", speciality);
        Speciality result = specialityRepository.save(speciality);
        return result;
    }

    /**
     *  Get all the specialities.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Speciality> findAll() {
        log.debug("Request to get all Specialities");
        List<Speciality> result = specialityRepository.findAll();

        return result;
    }

    /**
     *  Get one speciality by id.
     *
     *  @param code the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Speciality findOne(Integer code) {
        log.debug("Request to get Speciality : {}", code);
        Speciality speciality = specialityRepository.findOneByCode(code);
        return speciality;
    }

    /**
     *  Delete the  speciality by id.
     *
     *  @param code the id of the entity
     */
    public void delete(Integer code) {
        log.debug("Request to delete Speciality : {}", code);
        specialityRepository.deleteOneByCode(code);
    }
}
