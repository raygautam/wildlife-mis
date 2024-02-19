package in.gov.forest.wildlifemis.credential.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.gov.forest.wildlifemis.domian.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;

    private final String serviceName;

    private final String divisionName;

    private final String rangeName;
//    private final String stateName;

    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password, String serviceName, String divisionName, String rangeName, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.serviceName = serviceName;
        this.divisionName = divisionName;
        this.rangeName = rangeName;
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public static UserDetailsImpl build(AppUser userDetail_t) {

    List<GrantedAuthority> authorities = userDetail_t.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    return new UserDetailsImpl(
            userDetail_t.getId(),
            userDetail_t.getUserName(),
            userDetail_t.getPassword(),
            userDetail_t.getService().getServiceName(),
            userDetail_t.getDivision().getName(),
            userDetail_t.getRange().getRangeName(),
            authorities
        );
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
