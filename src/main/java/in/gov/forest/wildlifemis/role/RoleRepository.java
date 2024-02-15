package in.gov.forest.wildlifemis.role;

import in.gov.forest.wildlifemis.domian.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Set<Role> findIdByName(String roleSuperAdmin);
}