package in.gov.forest.wildlifemis.lgdEntities.entities;

import in.gov.forest.wildlifemis.domian.Service;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Division {
    @Id
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Service service;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
//    @JsonIgnoreProperties(value = {"division", "hibernateLazyInitializer"})
    private State state;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Division division = (Division) o;
        return getId() != null && Objects.equals(getId(), division.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}