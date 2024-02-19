package in.gov.forest.wildlifemis.comman;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRefreshRequest {
  private String refreshToken;
}
