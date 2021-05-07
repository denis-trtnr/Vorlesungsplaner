package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.interfaces.*;
import dhbw.vs.uniplaner.repository.LectureDateRepository;
import dhbw.vs.uniplaner.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CalendarController {

    @Autowired
    private ILectureService lectureService;
    @Autowired
    private ILecturerService lecturerService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IDegreeProgramService degreeProgramService;

    private LectureDateRepository lectureDateRepository;
    private LecturerRepository lecturerRepository;
    private Long currentCourse = 0L;
    private Long currentTutor = 0L;

    public CalendarController(LectureDateRepository lectureDateRepository) {
        this.lectureDateRepository = lectureDateRepository;
    }

    /**
     * Calls a calendar view and displays all lectureDates attached to the picked course
     * @param model Spring MVC model Attribute
     * @param id id is used to identify the course -> CourseId
     * @return returns calendar_view template with a course attached to load data in to the template
     */
    @RequestMapping("/courseCalendar/{id}")
    public String programOverview(Model model, @PathVariable("id") Long id) {
        Optional<Course> findCourse = courseService.findOne(id);
        findCourse.ifPresent(course -> model.addAttribute("course", course));
        this.currentCourse = id;
        return "calendar_view";
    }

    /**
     * Shows lecturers a calendar view with their lectures and their courses where they still have to plan
     * @param model Spring MVC model Attribute
     * @param userDetails Auto-received through Spring Security
     * @return lecturerOverview template with object attached to load lecturer specific data
     */
    @GetMapping("/lecturerboard")
    @PreAuthorize("hasAuthority('ROLE_LECTURER')")
    public String lecturerBoard(Model model,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Lecturer lecturer = lecturerService.findByEmail(userDetails.getUsername());
        System.out.println("T1" + lecturer.getId());
        this.currentTutor = lecturer.getId();
        System.out.println("T2" + currentTutor);

        Set<Lecture> lectures = lecturer.getLectures();
        Set<Course> courses = new HashSet<>();
        Course course = new Course();
        for(Lecture lecture : lectures) {
            courses.add(lecture.getCourse());
        }
        Lecture lecture = new Lecture();
        model.addAttribute("course",course);
        model.addAttribute("lecture",lecture);
        model.addAttribute("courses",courses);
        model.addAttribute("lectures", lectures);
        model.addAttribute("lecturer", lecturer);
        return "lecturerOverview";
    }

    /**
     * Displays a calendar view containing data for just the chosen course.
     * The process of planning has to be started before accessing this method.
     * New lecture dates can be edited
     * Lecturer can edit his*her lecture dates
     * @param model Spring MVC model Attribute
     * @param courseId CourseId to identify and retrieve right data
     * @param userDetails Auto-received through Spring Security
     * @return
     */
    @RequestMapping("/tutorCourseCalendar/{courseId}")
    @PreAuthorize("hasAuthority('ROLE_LECTURER')")
    public String tutorCalendar(Model model,
                                @PathVariable("courseId") Long courseId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Lecturer lecturer = lecturerService.findByEmail(userDetails.getUsername());
        this.currentCourse = courseId;
        Course course = courseService.findOne(courseId).orElseThrow(RuntimeException::new);
        if(course.getPlaningOrder().isEmpty()){
            Boolean noPlaning = true;
            model.addAttribute("noPlaning", noPlaning);
            return lecturerBoard(model, userDetails);
        } else if (!userDetails.getUsername().equals(course.getPlaningOrder().get(0).getEmail())){
            Boolean access = true;
            model.addAttribute("access", access);
            return lecturerBoard(model, userDetails);
        }
        model.addAttribute("course", course);
        model.addAttribute("lecturer", lecturer);
        Set<Lecture> lectures = lecturer.getLectures();
        Set<Lecture> correctLectures = new HashSet<>();
        for(Lecture lecture : lectures) {
            if(lecture.getCourse().getId().equals(courseId)) {
                correctLectures.add(lecture);
            }
        }
        model.addAttribute("lectures", correctLectures);
        ArrayList<LectureDate> correctLectureDates = new ArrayList<>();
        for (Lecture lecture : correctLectures) {
            for (LectureDate lectureDate : lecture.getLectureDates()) {
                correctLectureDates.add(lectureDate);
            }
        }
        ArrayList<Event> correctLectureEvents = lectureService.convertLectureDatesToEvents(correctLectureDates);
        model.addAttribute("lectureDates", correctLectureEvents);
        LectureDate lecturedate = new LectureDate();
        model.addAttribute("lecturedate", lecturedate);
        return "tutor_calendar";
    }

    /**
     * FullCalendar API uses this method to get all lecture dates from the course
     * @return returns lecture dates casted to event objects
     */
    @GetMapping(path = "/currentCourseEvents", produces = {"application/json", "text/json"})
    @ResponseBody
    public ArrayList<Event> getCurrentCourseEvents() {
        return lectureService.convertLectureDatesToEvents(lectureDateRepository.findByCourse(this.currentCourse));
    }

    /**
     * FullCalendar API uses this method to get all lecture dates from the lecturer
     * @return returns lecture dates casted to event objects
     */
    @GetMapping(path = "/currentTutorEvents", produces = {"application/json", "text/json"})
    @ResponseBody
    public ArrayList<Event> getCurrentTutorEvents() {
        ArrayList<LectureDate> tutorLectureDates = new ArrayList<>();
        tutorLectureDates.addAll(lecturerService.findOne(this.currentTutor).orElseThrow(RuntimeException::new).getLectureDates());
        return lectureService.convertLectureDatesToEvents(tutorLectureDates);
    }

}
