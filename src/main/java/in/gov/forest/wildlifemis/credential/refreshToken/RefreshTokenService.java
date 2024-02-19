package in.gov.forest.wildlifemis.credential.refreshToken;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.JwtCustomException;
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

  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken refreshToken = new RefreshToken();

//    refreshToken.setId(userRepository.findById(userId).orElseThrow(()->new RuntimeException(userId+" Data not found??")).getId());
//    refreshToken.setExpiryDate(Instant.now().plusMillis(60*60*1000));
//    refreshToken.setToken(UUID.randomUUID().toString());

//    refreshToken = refreshTokenRepository.save(
//            RefreshToken.builder()
//                    .appUser(userRepository.getReferenceById(userId))
//                    .expiryDate(Instant.now().plusMillis(60*60*1000))
//                    .token(UUID.randomUUID().toString())
//                    .build()
//    );
    return refreshTokenRepository.save(
            RefreshToken.builder()
                    .appUser(userRepository.getReferenceById(userId))
                    .expiryDate(Instant.now().plusMillis(60*60*1000))
                    .token(UUID.randomUUID().toString())
                    .build()
    );
  }


  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new JwtCustomException("Refresh token was expired. Please make a new signIn request", new Error(token.getToken()));
    }

    return token;
  }

  @Transactional
  public void deleteByUserId(Long userId) {
    refreshTokenRepository.deleteByAppUserId(userId);
  }
}
