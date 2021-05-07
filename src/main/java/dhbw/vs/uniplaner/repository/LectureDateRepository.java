package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.LectureDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@SuppressWarnings("unused")
public interface LectureDateRepository extends JpaRepository<LectureDate, Long> {

    @Query("SELECT t FROM LectureDate t WHERE t.lecture.courseLecture.id = ?1")
    ArrayList<LectureDate> findByCourse(Long courseId);

}