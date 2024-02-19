package in.gov.forest.wildlifemis.lgdEntities.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperatedBlock {
    @Id
    private Integer operatedBlockId;
    private String operatedBlockName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_code")
    @ToString.Exclude
    private District district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "range_id")
    @ToString.Exclude
    private Range range;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OperatedBlock that = (OperatedBlock) o;
        return getOperatedBlockId() != null && Objects.equals(getOperatedBlockId(), that.getOperatedBlockId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
