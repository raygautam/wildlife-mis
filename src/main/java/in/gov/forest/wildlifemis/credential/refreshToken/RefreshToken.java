package in.gov.forest.wildlifemis.credential.refreshToken;

import in.gov.forest.wildlifemis.domian.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "refreshToken_gen")
  @SequenceGenerator(name = "refreshToken_gen", sequenceName = "refreshToken_gen_seq", allocationSize = 1)
  private Long id;

  @OneToOne
  @JoinColumn(name = "appUser", referencedColumnName = "id")
  private AppUser appUser;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private Instant expiryDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RefreshToken that = (RefreshToken) o;
    return Objects.equals(id, that.id) && Objects.equals(appUser, that.appUser) && Objects.equals(token, that.token) && Objects.equals(expiryDate, that.expiryDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, appUser, token, expiryDate);
  }
}
