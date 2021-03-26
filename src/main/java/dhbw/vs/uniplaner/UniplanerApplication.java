package dhbw.vs.uniplaner;


import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.Lecture;
import dhbw.vs.uniplaner.repository.CourseRepository;
import dhbw.vs.uniplaner.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniplanerApplication implements CommandLineRunner {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private LectureRepository lectureRepository;


	public static void main(String[] args) {
		SpringApplication.run(UniplanerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Course course = new Course();
		course.setCourseName("WWI2022H");
		course.setStartingYear(2022L);
		course = courseRepository.save(course);
		System.out.println("course="+ course.toString());
		Lecture lecture = new Lecture();
		lecture.setLectureName("Einführung");
		lecture.setDuration(53L);
		lecture =lectureRepository.save(lecture);
		System.out.println("lecture="+ lecture.toString());



	}

}
