package in.gov.forest.wildlifemis.auditTrail;


import in.gov.forest.wildlifemis.domian.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {
}