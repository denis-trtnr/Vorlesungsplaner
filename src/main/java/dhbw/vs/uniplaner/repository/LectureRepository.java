package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.Lecture;

import dhbw.vs.uniplaner.domain.LectureDate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@SuppressWarnings("unused")
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT t FROM Lecture t WHERE t.courseLecture.id = ?1")
    ArrayList<LectureDate> findByCourse(Long courseId);

//    @Query("SELECT t FROM Lecture t WHERE t.lecturers.id = ?1")
//    ArrayList<LectureDate> findByLecturer(Long lecturerId);
}