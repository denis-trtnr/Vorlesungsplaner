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
	private SemesterRepository semesterRepository;
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

	public static void main(String[] args) {
		SpringApplication.run(UniplanerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		roleService.createRoles();


		Course course = new Course();
		Lecture lecture1 = new Lecture();
		Course course2 = new Course();
		DegreeProgram degreeProgram1 = new DegreeProgram();
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
		course.setStartDate(LocalDate.now());
		course = courseRepository.save(course);
		System.out.println("course="+ course.toString());
		course2.setCourseName("WWI2022G");
		course2.setStartDate(LocalDate.now());
		course2 = courseRepository.save(course2);
		System.out.println("course="+ course2.toString());
		//Semester

		Semester semester1 = new Semester();
		semester1.setStartDate(LocalDate.now());
		semester1.setEndDate(LocalDate.of(2021, Month.JUNE,23));
		semester1.setName("Semester 1");
		semester1.setSemesterNumber(1L);
		course.addSemester(semester1);
		semesterRepository.save(semester1);
		Semester semester2 = new Semester();
		semester2.setStartDate(LocalDate.now());
		semester2.setEndDate(LocalDate.of(2021, Month.JUNE,20));
		semester2.setName("Semester 3");
		semester2.setSemesterNumber(3L);
		course2.addSemester(semester2);
		semesterRepository.save(semester2);



		//User
		createUserOrAdmin("Michael","Fritz","fritz@gmail.com","123",roleService.getStudent());
		createUserOrAdmin("Thorsten","Müller","mueller@gmail.com","123",roleService.getStudent());
		//Admin
		createUserOrAdmin("Admin","Admin","admin@gmail.com","123",roleService.getAdmin());

		//Lecture erstellen
		Lecture lecture = new Lecture();
		lecture.setLectureName("Einführung");
		lecture.setDuration(53L);
		lecture.setModulName("Einführung");
		course.addLecture(lecture);

		Lecture lecture2 = new Lecture();
		lecture2.setLectureName("BWL");
		lecture2.setDuration(53L);
		lecture2.setModulName("Wirtschaft");
		course.addLecture(lecture2);

		Lecture lecture3 = new Lecture();
		lecture3.setLectureName("VWL");
		lecture3.setDuration(53L);
		lecture3.setModulName("Wirtschaft");
		course2.addLecture(lecture3);

		Lecture lectureX = new Lecture();
		lectureX.setLectureName("Programmierung");
		lectureX.setDuration(53L);
		lectureX.setModulName("Softwarearchitektur");
		course.addLecture(lectureX);

		Lecture lectureZ = new Lecture();
		lectureZ.setLectureName("Datenbanken");
		lectureZ.setDuration(53L);
		lectureZ.setModulName("Datenbanken");
		course2.addLecture(lectureZ);

		//Create random Dates to lecture
		Set<LectureDate> lectureDatesSet1 = createListOfDates(4,6);
		Set<LectureDate> lectureDatesSet2 = createListOfDates(5,10);
		Set<LectureDate> lectureDatesSet3 = createListOfDates(2,13);
		Set<LectureDate> lectureDatesSet4 = createListOfDates(3,15);
		Set<LectureDate> lectureDatesSet5 = createListOfDates(2,18);

		//Erstelle Dozent und füg ihn zur List hinzu
		Lecturer dozent1 = createDozent("Max","Mustermann","dozent1@gmail.com","123");
		Lecturer dozent2 = createDozent("Maria","Musterfrau","dozent2@gmail.com","123");
		Lecturer lecturer = createDozent("basti", "Richter", "basti@gmx.com", "123");
		Lecturer lecturer1 = createDozent("Erich", "Heumüller", "erich@gmx.com", "123");
		Lecturer lecturer2 = createDozent("Thomas", "Specht", "thomas@gmx.com", "123");
		Lecturer lecturer3 = createDozent("Lukas", "Paffen", "paffen@gmx.com", "123");
		Set<Lecturer> dozenten_1 = new HashSet<>();
		dozenten_1.add(dozent1);
		dozenten_1.add(dozent2);
		dozenten_1.add(lecturer);
		dozenten_1.add(lecturer1);
		Set<Lecturer> dozenten_2 = new HashSet<>();
		dozenten_2.add(lecturer3);
		Set<Lecturer> dozenten_3 = new HashSet<>();
		dozenten_3.add(lecturer2);
		dozenten_3.add(lecturer3);

		//Map die drei zusammen
		mapLs(lecture, lectureDatesSet1, dozenten_1);
		mapLs(lectureX, lectureDatesSet2, dozenten_1);
		mapLs(lectureZ, lectureDatesSet3, dozenten_1);
		mapLs(lecture2, lectureDatesSet4,dozenten_2);
		mapLs(lecture3, lectureDatesSet5,dozenten_3);
		lecturerRepository.saveAll(dozenten_1);
		lecturerRepository.saveAll(dozenten_2);
		lecturerRepository.saveAll(dozenten_3);
	}

	private void mapLs(Lecture lecture, Set<LectureDate> lectureDates, Set<Lecturer> lecturers) throws IllegalOperationException {
		lectureRepository.save(lecture);
		for(Lecturer lecturer:lecturers) {
			lecture.addLecturer(lecturer);
			for (LectureDate lectureDate : lectureDates) {
				lecture.addLectureDate(lectureDate);
				lecturer.addLectureDate(lectureDate);
				lectureDateRepository.save(lectureDate);
			}
			//lecturerRepository.save(lecturer);
		}
	}

	private Set<LectureDate> createListOfDates(int count, int time) {
		Set<LectureDate> lectureDates = new HashSet<>();
		for(int i = 0; i<count;i++) {
			LectureDate lectureDate = new LectureDate();
			lectureDate.setStart(LocalDateTime.of(2021, Month.MAY,13+i,time,0));
			lectureDate.setEnd(LocalDateTime.of(2021,Month.MAY,13+i,time+2,15));
			lectureDates.add(lectureDate);
		}
		return lectureDates;
	}

	private void createUserOrAdmin(String firstname, String lastname, String email, String password, Role role) {
		UniUser user = new UniUser();
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
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
