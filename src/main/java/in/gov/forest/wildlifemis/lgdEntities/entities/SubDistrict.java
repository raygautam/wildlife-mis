package in.gov.forest.wildlifemis.lgdEntities.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubDistrict {
    @Id
    private Long subDistrictCode;
    private String subDistrictName;

    @ManyToOne
    @JoinColumn(name = "district_district_code")
    private District district;

    @OneToOne
    private Block block;
}
