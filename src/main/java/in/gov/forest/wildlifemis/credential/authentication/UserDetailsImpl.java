package in.gov.forest.wildlifemis.credential.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.gov.forest.wildlifemis.domian.AppUser;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;

    private final Long serviceId;

    private final Integer divisionId;

    private final Integer rangeId;

    private Boolean accountLocked; ////default value false

    private Integer failedLoginAttempts; //default value 0

    private LocalDateTime lockoutTime;
//    private final String stateName;

    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password, Long serviceId, Integer divisionId, Integer rangeId, Boolean accountLocked, Integer failedLoginAttempts, LocalDateTime lockoutTime, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.serviceId = serviceId;
        this.divisionId = divisionId;
        this.rangeId = rangeId;
        this.accountLocked = accountLocked;
        this.failedLoginAttempts=failedLoginAttempts;
        this.lockoutTime=lockoutTime;
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
//        String serviceName=userDetail_t.getService()!=null && userDetail_t.getService().getServiceName() != null ? userDetail_t.getService().getServiceName() : "";
//        String divisionName= userDetail_t.getDivision()!=null && userDetail_t.getDivision().getName() != null ? userDetail_t.getDivision().getName():"";
//        String rangeName=userDetail_t.getRange()!=null && userDetail_t.getRange().getRangeName() != null ? userDetail_t.getRange().getRangeName():"";

        log.info("");
        return new UserDetailsImpl(
            userDetail_t.getId(),
            userDetail_t.getUserName(),
            userDetail_t.getPassword(),
            userDetail_t.getService()!=null ? userDetail_t.getService().getId() :null,
            userDetail_t.getDivision() != null ? userDetail_t.getDivision().getId():null,
            userDetail_t.getRange() != null ? userDetail_t.getRange().getRangeId():null,
            userDetail_t.getAccountLocked(),
            userDetail_t.getFailedLoginAttempts(),
            userDetail_t.getLockoutTime(),
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
