package dhbw.vs.uniplaner;


import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootApplication
public class UniplanerApplication implements CommandLineRunner {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private DegreeProgramRepository degreeProgramRepository;
	@Autowired
	private LectureDateRepository lectureDateRepository;

	public static void main(String[] args) {
		SpringApplication.run(UniplanerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Course course = new Course();
		Lecture lecture = new Lecture();
		Course course2 = new Course();
		DegreeProgram degreeProgram1 = new DegreeProgram();
		Role role = new Role();
		DegreeProgram degreeProgram2 = new DegreeProgram();

		degreeProgram1.setName("Wirtschaftsinformatik");
		degreeProgram1.setShortName("WI");
		degreeProgram1.addCourse(course);
		course.setDegreeProgram(degreeProgram1);
		degreeProgram1.addCourse(course2);
		course.setDegreeProgram(degreeProgram1);
		degreeProgram1 = degreeProgramRepository.save(degreeProgram1);
		System.out.println("dP="+ degreeProgram1.toString());
		degreeProgram2.setName("Informatik");
		degreeProgram2.setShortName("Inf");
		degreeProgram2 = degreeProgramRepository.save(degreeProgram2);
		System.out.println("dP="+ degreeProgram2.toString());


		course.setCourseName("WWI2022H");
		course.setStartingYear(2022L);
		course = courseRepository.save(course);
		System.out.println("course="+ course.toString());
		course2.setCourseName("WWI2022G");
		course2.setStartingYear(2021L);
		course2 = courseRepository.save(course2);
		System.out.println("course="+ course2.toString());

		lecture.setLectureName("Einführung");
		lecture.setDuration(53L);
		lecture =lectureRepository.save(lecture);
		System.out.println("lecture="+ lecture.toString());
		role.setRoleName("ROLE_USER");
		roleRepository.save(role);

		LectureDate lectureDate = new LectureDate();
		lectureDate.setStart(LocalDateTime.of(2021, Month.APRIL,23,9,0));
		lectureDate.setEnd(LocalDateTime.of(2021,Month.APRIL,23,12,15));
		lectureDate = lectureDateRepository.save(lectureDate);
		System.out.println("LectureDate1: " + lectureDate.getStart() + " - " + lectureDate.getEnd());
		LectureDate lectureDate2 = new LectureDate();
		lectureDate2.setStart(LocalDateTime.of(2021, Month.APRIL,21,13,15));
		lectureDate2.setEnd(LocalDateTime.of(2021,Month.APRIL,21,16,30));
		lectureDate2 = lectureDateRepository.save(lectureDate2);
		System.out.println("LectureDate2: " + lectureDate2.getStart() + " - " + lectureDate2.getEnd());

	}

}
