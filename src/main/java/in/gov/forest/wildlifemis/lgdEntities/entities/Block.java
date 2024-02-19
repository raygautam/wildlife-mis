package in.gov.forest.wildlifemis.lgdEntities.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "blocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Block {
    @Id
    private Long blockCode;

    private String blockName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_code", nullable=false)
    private District district;

//    @OneToMany()
//    private Set<Village> villages;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(blockCode, block.blockCode) && Objects.equals(blockName, block.blockName) && Objects.equals(district, block.district);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockCode, blockName, district);
    }
}
