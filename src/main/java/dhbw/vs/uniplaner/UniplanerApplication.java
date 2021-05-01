package dhbw.vs.uniplaner;


import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.interfaces.IRoleService;
import dhbw.vs.uniplaner.interfaces.IUserService;
import dhbw.vs.uniplaner.repository.*;
import dhbw.vs.uniplaner.service.UserService;
import dhbw.vs.uniplaner.web.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.lang.model.element.Element;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UniplanerApplication implements CommandLineRunner {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private LecturerRepository lecturerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LectureDateRepository lectureDateRepository;
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;



	public static void main(String[] args) {
		SpringApplication.run(UniplanerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		roleService.createRoles();

		Course course = new Course();
		course.setCourseName("WWI2022H");
		course.setStartingYear(2022L);
		course = courseRepository.save(course);
		System.out.println("course="+ course.toString());
		Course course2 = new Course();
		course2.setCourseName("WWI2022G");
		course2.setStartingYear(2021L);
		course2 = courseRepository.save(course2);
		System.out.println("course="+ course2.toString());

		createUserOrAdmin("m","m","m","m",roleService.getStudent());
		createUserOrAdmin("x","x","x","x",roleService.getStudent());

		//Lecture erstellen
		Lecture lecture = new Lecture();
		lecture.setLectureName("Einführung");
		lecture.setDuration(53L);
		course.addLecture(lecture);

		//Create random Dates to lecture
		Set<LectureDate> lectureDatesSet1 = createListOfDates(5);

		//Erstelle Dozent und füg ihn zur List hinzu
		Lecturer dozent1 = createDozent("e","e","e","e");
		Lecturer dozent2 = createDozent("f","f","f","f");
		Set<Lecturer> dozenten_1 = new HashSet<>();
		dozenten_1.add(dozent1);
		dozenten_1.add(dozent2);

		//Map die drei zusammen
		mapLs(lecture, lectureDatesSet1, dozenten_1);

	}

	private void mapLs(Lecture lecture, Set<LectureDate> lectureDates, Set<Lecturer> lecturers) {
		for(Lecturer lecturer:lecturers) {
			lecture.addLecturer(lecturer);
			lectureRepository.save(lecture);
			for (LectureDate lectureDate : lectureDates) {
				lecture.addLectureDate(lectureDate);
				lecturer.addLectureDate(lectureDate);
				lectureDateRepository.save(lectureDate);
			}
			lecturerRepository.save(lecturer);
		}
	}

	private Set<LectureDate> createListOfDates(int count) {
		Set<LectureDate> lectureDates = new HashSet<>();
		for(int i = 0; i<count;i++) {
			LectureDate lectureDate = new LectureDate();
			lectureDate.setStart(LocalDateTime.of(2021, Month.APRIL,23+i,9,0));
			lectureDate.setEnd(LocalDateTime.of(2021,Month.APRIL,23+i,12,15));
			lectureDates.add(lectureDate);
		}
		return lectureDates;
	}

	private void createUserOrAdmin(String firstname, String lastname, String email, String password, Role role) {
		UniUser user = new UniUser();
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));;
		user.addRole(role);
		userRepository.save(user);
	}

	private Lecturer createDozent(String firstname, String lastname, String email, String password) {
		createUserOrAdmin(firstname,lastname,email,password,roleService.getDozent());
		Lecturer dozent = new Lecturer();
		dozent.setEmail(password);
		dozent.setFirstName(firstname);
		dozent.setLastName(lastname);
		return dozent;
	}
}
