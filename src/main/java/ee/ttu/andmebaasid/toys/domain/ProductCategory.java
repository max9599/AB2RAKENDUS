package ee.ttu.andmebaasid.toys.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ProductCategory view.
 */
@Entity
@Immutable // It is a view
@Table(name = "toodete_kategooriad")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@IdClass(ProductCategory.PCPK.class)
public class ProductCategory implements Serializable {

    public class PCPK implements Serializable {
        String productCode;
        String categoryName;
        public PCPK(String productCode, String categoryName) {
            this.productCode = productCode;
            this.categoryName = categoryName;
        }
    }

    @Id
    @Column(name = "toode_kood")
    private String productCode;

    @Id
    @Column(name = "nimetus")
    private String categoryName;

    public String getProductCode() {
        return productCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductCategory productCategory = (ProductCategory) o;
        if (productCategory.productCode == null || productCode == null) {
            return false;
        }
        return Objects.equals(productCategory.productCode, productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productCode);
    }

    @Override
    public String toString() {
        return "ProductView{" +
            ", code='" + productCode + "'" +
            ", name='" + categoryName + "'" +
            '}';
    }
}
