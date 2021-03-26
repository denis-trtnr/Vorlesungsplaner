package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.LectureDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface LectureDateRepository extends JpaRepository<LectureDate, Long> {
}