package ee.ttu.andmebaasid.toys.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Speciality.
 */
@Entity
@Table(name = "AMET")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Speciality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "amet_kood", nullable = false, unique = true)
    private Integer code;

    @NotNull
    @Column(name = "nimetus", nullable = false)
    private String name;

    @Column(name = "kirjeldus")
    private String description;


    public Integer getCode() {
        return code;
    }

    public Speciality code(Integer code) {
        this.code = code;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Speciality name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Speciality description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Speciality speciality = (Speciality) o;
        return !(speciality.code == null || code == null) && Objects.equals(code, speciality.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "Speciality{" +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
