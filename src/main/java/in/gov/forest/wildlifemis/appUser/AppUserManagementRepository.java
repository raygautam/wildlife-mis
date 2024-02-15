package in.gov.forest.wildlifemis.appUser;


import in.gov.forest.wildlifemis.domian.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserManagementRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUserName(String userName);

//    boolean existsByEmailId(String emailId);

    Optional<AppUser> findByUserName(String username);
}