package dhbw.vs.uniplaner.repository;

import dhbw.vs.uniplaner.domain.Course;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface CourseRepository extends JpaRepository<Course, Long> {
}