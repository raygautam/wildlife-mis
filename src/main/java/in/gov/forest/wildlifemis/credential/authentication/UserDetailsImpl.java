package in.gov.forest.wildlifemis.credential.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.gov.forest.wildlifemis.domian.AppUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;

//    private final String stateName;
//    private final String divisionName;
//    private final String districtName;
//    private final String rangeName;
    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(AppUser userDetail_t) {

    List<GrantedAuthority> authorities = userDetail_t.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    return new UserDetailsImpl(
            userDetail_t.getId(),
            userDetail_t.getUserName(),
           null,
            authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
