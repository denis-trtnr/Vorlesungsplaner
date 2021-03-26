package dhbw.vs.uniplaner.interfaces;

import dhbw.vs.uniplaner.domain.Course;

import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.repository.CourseRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface ICourseService {

    public Course save(Course course);

    public void delete(Long id);

    public List<Course> findAll();

    public Optional<Course> findOne(Long id);
}
