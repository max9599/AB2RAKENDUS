package ee.ttu.andmebaasid.toys.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * A ProductView.
 */
@Entity
@Immutable // It is a view
@Table(name = "toodete_nimekiri")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "EndProduct",
        query = "SELECT count(*) FROM f_lopeta_toode(:code)"
    )
})
public class ProductView implements Serializable {

    @Id
    @Column(name = "toode_kood")
    private String code;

    @Column(name = "toote_nimetus")
    private String name;

    @Column(name = "kirjeldus")
    private String description;

    @Column(name = "kaal")
    private Double weight;

    @Column(name = "korgus")
    private Double height;

    @Column(name = "pikkus")
    private Double length;

    @Column(name = "laius")
    private Double width;

    @Column(name = "hind")
    private Double price;

    @Column(name = "min_soovitatav_vanus")
    private Integer minAge;

    @Column(name = "max_soovitatav_vanus")
    private Integer maxAge;

    @Column(name = "tootja_riik")
    private String countryName;

    @Column(name = "pildi_url")
    private String imageURL;

    @Column(name = "reg_aeg")
    private LocalDate registrationDate;

    @Column(name = "toote_seisund")
    private String productState;

    @Column(name = "registreerija_nimi")
    private String registratorName;

    @Column(name = "registreerija_e_meil")
    private String registratorEmail;

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    @Transient
    @JsonProperty
    private Set<String> categories;

    public String getCode() {
        return code;
    }

    public String getRegistratorEmail() {
        return registratorEmail;
    }

    public void setRegistratorEmail(String registratorEmail) {
        this.registratorEmail = registratorEmail;
    }

    public ProductView code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public ProductView name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProductView description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public ProductView weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public ProductView height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLength() {
        return length;
    }

    public ProductView length(Double length) {
        this.length = length;
        return this;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public ProductView width(Double width) {
        this.width = width;
        return this;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getPrice() {
        return price;
    }

    public ProductView price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public ProductView minAge(Integer minAge) {
        this.minAge = minAge;
        return this;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public ProductView maxAge(Integer maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getCountryName() {
        return countryName;
    }

    public ProductView countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public ProductView imageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public ProductView registrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getProductState() {
        return productState;
    }

    public ProductView productState(String productState) {
        this.productState = productState;
        return this;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public String getRegistratorName() {
        return registratorName;
    }

    public ProductView registratorName(String registratorName) {
        this.registratorName = registratorName;
        return this;
    }

    public void setRegistratorName(String registratorName) {
        this.registratorName = registratorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductView productView = (ProductView) o;
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
            ", weight='" + weight + "'" +
            ", height='" + height + "'" +
            ", length='" + length + "'" +
            ", width='" + width + "'" +
            ", price='" + price + "'" +
            ", minAge='" + minAge + "'" +
            ", maxAge='" + maxAge + "'" +
            ", countryName='" + countryName + "'" +
            ", imageURL='" + imageURL + "'" +
            ", registrationDate='" + registrationDate + "'" +
            ", productState='" + productState + "'" +
            ", registratorName='" + registratorName + "'" +
            '}';
    }
}
