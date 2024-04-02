package in.gov.forest.wildlifemis.credential.authentication;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.domian.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@Transactional
public class UserService {
    public static final int MAX_FAILED_ATTEMPTS = 2;
//
//    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    @Autowired
    private AppUserManagementRepository repo;

    public void increaseFailedAttempts(AppUser user) {
        int newFailAttempts = user.getFailedLoginAttempts() + 1;
        repo.updateFailedAttempts(newFailAttempts, user.getUserName());
    }

    public void resetFailedAttempts(String email) {
        repo.updateFailedAttempts(0, email);
    }

    public void lock(AppUser user) {
        user.setAccountLocked(true);
        user.setLockoutTime(LocalDateTime.now().plusHours(24));

        repo.save(user);
    }

    public boolean unlockWhenTimeExpired(AppUser user) {
        if(user.getLockoutTime() == null){
            return true;
        }
//        long lockTimeInMillis = user.getLockTime().getTime();
//        long currentTimeInMillis = System.currentTimeMillis();

        if (user.getLockoutTime().isAfter(LocalDateTime.now())) {
            user.setAccountLocked(false);
            user.setFailedLoginAttempts(0);
            user.setLockoutTime(null);

            repo.save(user);

            return true;
        }

        return false;
    }
}
