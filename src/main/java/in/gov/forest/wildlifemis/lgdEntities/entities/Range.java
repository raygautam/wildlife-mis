package in.gov.forest.wildlifemis.lgdEntities.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.gov.forest.wildlifemis.domian.ForestService;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Range {
    @Id
    @Column(name = "range_id", nullable = false)
    private Integer rangeId;

    @Column(name = "range_name", nullable = false)
    private String rangeName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "division_id", nullable = false)
    @JsonIgnoreProperties(value = {"range", "hibernateLazyInitializer"})
    private Division division;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ForestService service;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "ranges")
    @ToString.Exclude
    private Set<District> districts = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Range range = (Range) o;
        return getRangeId() != null && Objects.equals(getRangeId(), range.getRangeId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
