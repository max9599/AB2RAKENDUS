package ee.ttu.andmebaasid.toys.repository;

import ee.ttu.andmebaasid.toys.domain.Speciality;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Speciality entity.
 */
@Repository
public interface SpecialityRepository extends JpaRepository<Speciality,Long> {

    public Speciality findOneByCode(Integer code);
    public void deleteOneByCode(Integer code);

}
