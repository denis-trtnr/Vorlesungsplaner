package dhbw.vs.uniplaner.service;


import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.repository.CourseRepository;
import dhbw.vs.uniplaner.domain.Course;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService implements ICourseService {

    Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course save(Course course) {
        logger.debug("Request to save Course {}", course);
        return courseRepository.save(course);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Course {}", id);
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> findAll() {
        logger.debug("Request to get all Course");
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> findOne(Long id) {
        logger.debug("Request to find Course {}", id);
        return courseRepository.findById(id);
    }
}
