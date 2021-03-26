package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.DegreeProgram;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface DegreeProgramRepository extends JpaRepository<DegreeProgram, Long> {
}