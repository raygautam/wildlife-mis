package in.gov.forest.wildlifemis.lgdEntities.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "villages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Village {
    @Id
    private Long villageCode;

    private String villageName;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="block_id", nullable=true)
//    private Block block;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sub_district_id", nullable=false)
    private SubDistrict subDistrict;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Village village = (Village) o;
        return getVillageCode() != null && Objects.equals(getVillageCode(), village.getVillageCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
