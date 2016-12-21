package ee.ttu.andmebaasid.toys.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A user.
 */
@Entity
@Table(name = "Isik")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "isik_id")
    private Long id;

    @JsonIgnore
    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "parool")
    private String password;

    @Size(max = 50)
    @Column(name = "eesnimi", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "perenimi", length = 50)
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (isikCode != null ? !isikCode.equals(user.isikCode) : user.isikCode != null) return false;
        if (riikCode != null ? !riikCode.equals(user.riikCode) : user.riikCode != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (isikCode != null ? isikCode.hashCode() : 0);
        result = 31 * result + (riikCode != null ? riikCode.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Size(max = 50)

    @Column(name = "isikukood", length = 50)
    private String isikCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIsikCode() {
        return isikCode;
    }

    public void setIsikCode(String isikCode) {
        this.isikCode = isikCode;
    }

    public String getRiikCode() {
        return riikCode;
    }

    public void setRiikCode(String riikCode) {
        this.riikCode = riikCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(max = 3)
    @Column(name = "riik_kood", length = 3)
    private String riikCode;

    @Email
    @Size(max = 100)
    @Column(name = "e_meil",length = 100, unique = true)
    private String email;

}
