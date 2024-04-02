package in.gov.forest.wildlifemis.domian;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity
@AllArgsConstructor
public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditTrail_ger")
    @SequenceGenerator(name = "auditTrail", sequenceName = "auditTrail_ger", initialValue = 1, allocationSize = 1)
    private Long auditTrailId;
    private String ipAddress;
    private String userName;
    private String userAgent;
    private String url;
    private String httpMethod;
    @Column(columnDefinition = "text")
    private String payload;
    private int statusCode;
    private LocalDateTime requestOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuditTrail that = (AuditTrail) o;
        return getAuditTrailId() != null && Objects.equals(getAuditTrailId(), that.getAuditTrailId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
