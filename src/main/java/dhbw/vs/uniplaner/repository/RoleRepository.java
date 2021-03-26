package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.Role;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface RoleRepository extends JpaRepository<Role, Long> {
}