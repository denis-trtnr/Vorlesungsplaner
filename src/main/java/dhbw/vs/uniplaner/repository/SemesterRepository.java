package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.Semester;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface SemesterRepository extends JpaRepository<Semester, Long> {
}