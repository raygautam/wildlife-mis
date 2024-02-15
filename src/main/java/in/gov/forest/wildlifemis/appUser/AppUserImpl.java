package in.gov.forest.wildlifemis.appUser;//
////import com.bezkoder.springjwt.models.User;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.io.Serial;
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
//public class AppUserImpl implements UserDetails {
//  @Serial
//  private static final long serialVersionUID = 1L;
//
//  private final Long id;
//
//  private final String username;
//
//  private final String email;
//
//  @JsonIgnore
//  private final String password;
//
//  private final String stateName;
//  private final String divisionName;
//  private final String districtName;
//  private final String rangeName;
//  private final List<GrantedAuthority> authorities;
//
////  private final Boolean isActive;
//
//
////  public UserDetailsImpl(Long id, String username, String email, String password, String stateName, String divisionName, String districtName, String rangeName, Boolean isActive, Collection<? extends GrantedAuthority> authorities) {
////    this.id = id;
////    this.username = username;
////    this.email = email;
//////    this.password = password;
////    this.stateName = stateName;
////    this.divisionName = divisionName;
////    this.districtName = districtName;
////    this.rangeName = rangeName;
////    this.isActive = isActive;
////    this.authorities = authorities;
////  }
//
////  public UserDetailsImpl(UserDetail_t userDetail_t){
////    this.id = userDetail_t.getUserId();
////    this.username = userDetail_t.getUserName();
////    this.email = userDetail_t.getEmailId();
////    this.stateName = userDetail_t.getState()!=null?userDetail_t.getState().getStateName():null;
////    this.divisionName = userDetail_t.getDivision()!=null?userDetail_t.getDivision().getDivisionName():null;
////    this.districtName = userDetail_t.getDistrict()!=null?userDetail_t.getDistrict().getDistrictName():null;
////    this.rangeName = userDetail_t.getRange()!=null?userDetail_t.getRange().getRangeName():null;
////    this.authorities =userDetail_t.getRoleId().stream()
////        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
////        .collect(Collectors.toList());
////  }
//
//
//  public AppUserImpl(Long id, String username, String email, String password, String stateName, String divisionName, String districtName, String rangeName, List<GrantedAuthority> authorities) {
//    this.id = id;
//    this.username = username;
//    this.email = email;
//    this.password = password;
//    this.stateName = stateName;
//    this.divisionName = divisionName;
//    this.districtName = districtName;
//    this.rangeName = rangeName;
//    this.authorities = authorities;
//  }
//
//  public static AppUserImpl build(UserDetail_t userDetail_t) {
//    List<GrantedAuthority> authorities = userDetail_t.getRoleId().stream()
//        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
//        .collect(Collectors.toList());
//
//    return new AppUserImpl(
//            userDetail_t.getUserId(),
//            userDetail_t.getUserName(),
//            userDetail_t.getEmailId(),
//            userDetail_t.getPassword(),
//            userDetail_t.getState() != null ? userDetail_t.getState().getStateName() : null,
//            userDetail_t.getDivision() != null ? userDetail_t.getDivision().getDivisionName() : null,
//            userDetail_t.getDistrict() != null ? userDetail_t.getDistrict().getDistrictName() : null,
//            userDetail_t.getRange() != null ? userDetail_t.getRange().getRangeName() : null,
//            authorities
//
////    return new UserDetailsImpl(
////            userDetail_t.getUserId(),
////            userDetail_t.getUserName(),
////            userDetail_t.getEmailId(),
////            userDetail_t.getPassword(),
////            userDetail_t.getState().getStateName(),
////            userDetail_t.getDivision().getDivisionName(),
////            userDetail_t.getDistrict().getDistrictName(),
////            userDetail_t.getRange().getRangeName(),
////            authorities
//    );
//  }
//
//  @Override
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return authorities;
//  }
//
//  public Long getId() {
//    return id;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  @Override
//  public String getPassword() {
//    return password;
//  }
//
//  @Override
//  public String getUsername() {
//    return username;
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return true;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return true;
//  }
//
//  public String getStateName() {
//    return stateName;
//  }
//
//  public String getDivisionName() {
//    return divisionName;
//  }
//
//  public String getDistrictName() {
//    return districtName;
//  }
//
//  public String getRangeName() {
//    return rangeName;
//  }
//
//  public Boolean getActive() {
//    return null;
//  }
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//    AppUserImpl that = (AppUserImpl) o;
//    return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(stateName, that.stateName) && Objects.equals(divisionName, that.divisionName) && Objects.equals(districtName, that.districtName) && Objects.equals(rangeName, that.rangeName) && Objects.equals(authorities, that.authorities);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(id, username, email, stateName, divisionName, districtName, rangeName, authorities);
//  }
//}
