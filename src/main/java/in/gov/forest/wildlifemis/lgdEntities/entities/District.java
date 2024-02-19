package in.gov.forest.wildlifemis.lgdEntities.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "districts")
@Builder
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    private Long districtCode;

    private String districtName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="state_id", nullable=false)
    private State state;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "district_range",
            joinColumns = @JoinColumn(name = "district_code"),
            inverseJoinColumns = @JoinColumn(name = "range_id"))
    private Set<Range> ranges = new HashSet<>();

//    @OneToMany()
////    @JsonIgnore
//    private Set<Block> block;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        District district = (District) o;
        return getDistrictCode() != null && Objects.equals(getDistrictCode(), district.getDistrictCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
