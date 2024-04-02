package in.gov.forest.wildlifemis.appUser;


import in.gov.forest.wildlifemis.domian.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserManagementRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUserName(String userName);

//    boolean existsByEmailId(String emailId);

    Optional<AppUser> findByUserName(String username);
    Optional<AppUser> findByUserNameAndIsActive(String userName,Boolean isActive);
    @Query(value = "UPDATE users SET failed_login_attempts = ?1 WHERE user_name = ?2",nativeQuery = true)
    @Modifying
    public void updateFailedAttempts(int failAttempts, String email);
}