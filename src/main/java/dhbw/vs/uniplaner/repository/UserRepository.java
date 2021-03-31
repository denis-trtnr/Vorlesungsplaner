package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.UniUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UniUser, Long > {
    UniUser findByEmail(String mail);
}
