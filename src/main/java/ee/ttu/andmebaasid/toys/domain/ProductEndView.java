package ee.ttu.andmebaasid.toys.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ProductEndView.
 */
@Entity
@Immutable // It is a view
@Table(name = "toodete_lopetamine")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ProductEndView implements Serializable {

    @Id
    @Column(name = "toode_kood")
    private String code;

    @Column(name = "toote_nimetus")
    private String name;

    @Column(name = "kirjeldus")
    private String description;

    @Column(name = "hind")
    private Double price;

    @Column(name = "toote_seisund")
    private String productState;

    @Column(name = "reg_aeg")
    private LocalDate registrationDate;

    public String getCode() {
        return code;
    }

    public ProductEndView code(String code) {
        this.code = code;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public ProductEndView name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProductEndView description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public ProductEndView registrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getProductState() {
        return productState;
    }

    public ProductEndView productState(String productState) {
        this.productState = productState;
        return this;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductEndView productView = (ProductEndView) o;
        if(productView.code == null || code == null) {
            return false;
        }
        return Objects.equals(code, productView.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "ProductView{" +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", price='" + price + "'" +
            ", registrationDate='" + registrationDate + "'" +
            ", productState='" + productState + "'" +
            '}';
    }
}
