package in.gov.forest.wildlifemis.ngtCommitteeType;

import in.gov.forest.wildlifemis.domian.NGTCommitteeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NGTCommitteeTypeRepository extends JpaRepository<NGTCommitteeType, Long> {
    List<NGTCommitteeType> findByOrderById();

    List<NGTCommitteeType> findByOrderByName();
}