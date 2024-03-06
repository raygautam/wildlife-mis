package in.gov.forest.wildlifemis.testing;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FishFarmerDetails implements Serializable {
  @Id
  @Column(name = "fish_farmer_id", nullable = false)
  private String fish_farmer_id;

  //  private String fish_farmer_id;
  private String gender;

  private Integer level_of_education_id;

  private String land_ownership_type;

  private String mobile_no;

  @Temporal(TemporalType.TIMESTAMP)
  private Date created_at;

  private String type_of_farmer;

  private Integer language_id;

  private String epic_head_of_family;

  private Integer alternate_mobile_no;

  private String epic_no;

  private String bank_branch_name;

  @Temporal(TemporalType.TIMESTAMP)
  private Date updated_at;

  private String farmer_id;
  @Temporal(TemporalType.TIMESTAMP)
  private Date last_update;

  private String bank_name;

  private Boolean is_head_of_family;

  private Integer village_code;


  private String bank_account_no;

  private String locality;

  private Integer farmer_category_id;

  private String cscpfcmobno;

  private Integer land_ownership_type_id;

  @Temporal(TemporalType.TIMESTAMP)
  private Date registration_date;

  private String bank_ifsc_code;

  private String father_mother_husband_name;

  private Integer district_code;

  private Date dob;

  private Integer block_code;

  private String name;

  private String id_card_number;

  private String id_card_type;

  private String age;

  private String alternatete_mobile_no;

  private Integer range_id;
  private Integer division_id;

}
