package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.Lecturer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
}