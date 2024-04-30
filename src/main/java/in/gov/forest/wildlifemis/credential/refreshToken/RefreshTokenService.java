package in.gov.forest.wildlifemis.credential.refreshToken;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.DataIntegrityViolationException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.JwtCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

  //  @Value("${wildLife.app.jwtRefreshExpirationDateInMs}")
//  private Long refreshTokenDurationMs;
  private final RefreshTokenRepository refreshTokenRepository;
  private final AppUserManagementRepository userRepository;

  public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AppUserManagementRepository userRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  @Transactional
  public RefreshToken createRefreshToken(Long userId) {
//    try{
//      Boolean exists = refreshTokenRepository.existsByAppUserId(userId);
//      if(exists){
//          refreshTokenRepository.findByAppUserId(userId);
//      }
//      RefreshToken refreshToken=refreshTokenRepository.findByAppUserId(userId);

      return refreshTokenRepository.save(
              RefreshToken.builder()
                      .appUser(userRepository.getReferenceById(userId))
                      .expiryDate(Instant.now().plusMillis(30*60*60*1000))//change to days
                      .token(UUID.randomUUID().toString())
                      .build()
      );
//    }catch (DataIntegrityViolationException  e){
//      throw new DataIntegrityViolationException(e.getMessage());
//    }
//    return refreshTokenRepository.save(
//            RefreshToken.builder()
//                    .appUser(userRepository.getReferenceById(userId))
//                    .expiryDate(Instant.now().plusMillis(60*60*1000))
//                    .token(UUID.randomUUID().toString())
//                    .build()
//    );
  }


  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new JwtCustomException("Refresh token was expired. Please make a new signIn request", new Error("","Refresh token was expired. Please make a new signIn request"));
    }

    return token;
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteByUserId(Long userId) {
      refreshTokenRepository.deleteByAppUserId(userId);
  }
}
