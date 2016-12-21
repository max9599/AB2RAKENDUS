package ee.ttu.andmebaasid.toys.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ProductOverview.
 */
@Entity
@Immutable // It is a view
@Table(name = "toodete_koondaruanne")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ProductOverview implements Serializable {

    @Id
    @Column(name = "seisund")
    private String statusName;

    @Column(name = "kokku")
    private Long total;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductOverview that = (ProductOverview) o;

        if (statusName != null ? !statusName.equals(that.statusName) : that.statusName != null) return false;
        return total != null ? total.equals(that.total) : that.total == null;

    }

    @Override
    public int hashCode() {
        int result = statusName != null ? statusName.hashCode() : 0;
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }

}
