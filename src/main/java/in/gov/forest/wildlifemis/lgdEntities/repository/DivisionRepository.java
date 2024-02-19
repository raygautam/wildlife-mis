package in.gov.forest.wildlifemis.lgdEntities.repository;


import in.gov.forest.wildlifemis.lgdEntities.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {
}