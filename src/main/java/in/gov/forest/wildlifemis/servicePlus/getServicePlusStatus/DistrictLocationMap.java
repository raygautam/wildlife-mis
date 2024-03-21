package in.gov.forest.wildlifemis.servicePlus.getServicePlusStatus;

import in.gov.forest.wildlifemis.lgdEntities.entities.District;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity(name = "service_plus_location_district_map")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DistrictLocationMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long locationId;
    @ManyToOne()
    @JoinColumn(name = "district_code",referencedColumnName = "districtCode")
    private District district;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DistrictLocationMap that = (DistrictLocationMap) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}