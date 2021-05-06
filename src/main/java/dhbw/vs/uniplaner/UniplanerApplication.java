package dhbw.vs.uniplaner;


import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.exception.IllegalOperationException;
import dhbw.vs.uniplaner.interfaces.IRoleService;
import dhbw.vs.uniplaner.interfaces.IUserService;
import dhbw.vs.uniplaner.repository.*;
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
import java.util.ArrayList;
import java.util.TreeMap;

@SpringBootApplication
public class UniplanerApplication implements CommandLineRunner {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private LectureRepository lectureRepository;
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
	@Autowired
	private DegreeProgramRepository degreeProgramRepository;
	@Autowired
	private LecturerRepository lecturerRepository;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UniplanerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		roleService.createRoles();

		TreeMap<Long,TreeMap<Long,Lecture>> hello;

		Course course = new Course();
		Lecture lecture1 = new Lecture();
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
//		degreeProgram2.setName("Informatik");
//		degreeProgram2.setShortName("Inf");
//		degreeProgram2 = degreeProgramRepository.save(degreeProgram2);
//		System.out.println("dP="+ degreeProgram2.toString());


		course.setCourseName("WWI2022H");
		course.setStartingYear(2019L);
		course = courseRepository.save(course);
		System.out.println("course="+ course.toString());
		course2.setCourseName("WWI2022G");
		course2.setStartingYear(2020L);
		course2 = courseRepository.save(course2);
		System.out.println("course="+ course2.toString());

		createUserOrAdmin("m","m","m","m",roleService.getStudent());
		createUserOrAdmin("x","x","x","x",roleService.getStudent());




		course.addLecture(lecture1);

		lecture1 =lectureRepository.save(lecture1);
		course = courseRepository.save(course);
		course2 = courseRepository.save(course2);
		System.out.println("course="+ course.toString());
		System.out.println("course="+ course2.toString());
		System.out.println("lecture="+ lecture1.toString());

		//Lecture erstellen
		Lecture lecture = new Lecture();
		lecture.setLectureName("Einführung");
		lecture.setDuration(53L);
		course.addLecture(lecture);

		Lecture lectureX = new Lecture();
		lectureX.setLectureName("Programmierung");
		lectureX.setDuration(53L);
		course.addLecture(lectureX);

		//Create random Dates to lecture
		Set<LectureDate> lectureDatesSet1 = createListOfDates(4);

		//Erstelle Dozent und füg ihn zur List hinzu
		Lecturer dozent1 = createDozent("Max","Mustermann","dozent1@gmail.com","123");
		Lecturer dozent2 = createDozent("Maria","Musterfrau","dozent2@gmail.com","123");
		Lecturer lecturer = createDozent("basti", "Richter", "basti@gmx.com", "123");
		Lecturer lecturer1 = createDozent("Erich", "Heumüller", "erich@gmx.com", "123");
		Lecturer lecturer2 = createDozent("Thomas", "Specht", "thomas@gmx.com", "123");
		Set<Lecturer> dozenten_1 = new HashSet<>();
		dozenten_1.add(dozent1);
		dozenten_1.add(dozent2);
		dozenten_1.add(lecturer);
		dozenten_1.add(lecturer1);
		dozenten_1.add(lecturer2);

		//Map die drei zusammen
		mapLs(lecture, lectureDatesSet1, dozenten_1);
		mapLs(lectureX, lectureDatesSet1, dozenten_1);
	}

	private void mapLs(Lecture lecture, Set<LectureDate> lectureDates, Set<Lecturer> lecturers) throws IllegalOperationException {
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
		dozent.setEmail(email);
		dozent.setFirstName(firstname);
		dozent.setLastName(lastname);
		return dozent;
	}
}
