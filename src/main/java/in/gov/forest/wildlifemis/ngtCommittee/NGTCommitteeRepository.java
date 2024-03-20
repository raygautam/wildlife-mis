package in.gov.forest.wildlifemis.ngtCommittee;

import in.gov.forest.wildlifemis.domian.NGTCommittee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface NGTCommitteeRepository extends JpaRepository<NGTCommittee, Long> {
    List<NGTCommittee> findByIsActiveOrderByCreatedDateDesc(Boolean aTrue);

    List<NGTCommittee> findByNgtCommitteeTypeIdAndIsActiveOrderByCreatedDateDesc(Long ngtCommitteeTypeId, Boolean aTrue);
}