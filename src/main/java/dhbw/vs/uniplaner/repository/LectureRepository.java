package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.Lecture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface LectureRepository extends JpaRepository<Lecture, Long> {
}