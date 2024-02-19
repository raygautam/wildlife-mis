package in.gov.forest.wildlifemis.lgdEntities.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;


@Entity
@Table(name = "states")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class State {
    @Id
    private Long stateCode;

    private String stateName;

//    @OneToMany()
//    private Set<District> districts;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        State state = (State) o;
        return getStateCode() != null && Objects.equals(getStateCode(), state.getStateCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}