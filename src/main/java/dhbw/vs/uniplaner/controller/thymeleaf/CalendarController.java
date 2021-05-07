package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.interfaces.*;
import dhbw.vs.uniplaner.repository.LectureDateRepository;
import dhbw.vs.uniplaner.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    @RequestMapping("/courseCalendar/{id}")
    public String programOverview(Model model, @PathVariable("id") Long id) {
        Optional<Course> findCourse = courseService.findOne(id);
        findCourse.ifPresent(course -> model.addAttribute("course", course));
        this.currentCourse = id;
        return "calendar_view";
}

    @GetMapping("/dozentenboard")
    @PreAuthorize("hasAuthority('ROLE_LECTURER')")
    public String dozentenboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {

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
        return "dozent_overview";
    }

    @RequestMapping("/tutorCourseCalendar/{courseId}")
    @PreAuthorize("hasAuthority('ROLE_LECTURER')")
    public String tutorCalendar(Model model, @PathVariable("courseId") Long courseId, @AuthenticationPrincipal UserDetails userDetails) {

        Lecturer lecturer = lecturerService.findByEmail(userDetails.getUsername());
        this.currentCourse = courseId;

        Course course = courseService.findOne(courseId).orElseThrow(RuntimeException::new);
        System.out.println(course.getPlaningOrder().isEmpty());
        System.out.println(course.getPlaningOrder().size());
//        if(course.getPlaningOrder().isEmpty()){
//            Boolean noPlaning = true;
//            model.addAttribute("noPlaning", noPlaning);
//            return dozentenboard(model, userDetails);
//        } else if (!userDetails.getUsername().equals(course.getPlaningOrder().get(0).getEmail())){
//            Boolean access = true;
//            model.addAttribute("access", access);
//            return dozentenboard(model, userDetails);
//        }

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

    // Hier holt sich Fullcalendar die KursEvents
    @GetMapping(path = "/currentCourseEvents", produces = {"application/json", "text/json"})
    @ResponseBody
    public ArrayList<Event> getCurrentCourseEvents() {
        return lectureService.convertLectureDatesToEvents(lectureDateRepository.findByCourse(this.currentCourse));
    }

    // Hier holt sich Fullcalendar die TutorEvents
    @GetMapping(path = "/currentTutorEvents", produces = {"application/json", "text/json"})
    @ResponseBody
    public ArrayList<Event> getCurrentTutorEvents() {
        ArrayList<LectureDate> tutorLectureDates = new ArrayList<>();
        tutorLectureDates.addAll(lecturerService.findOne(this.currentTutor).orElseThrow(RuntimeException::new).getLectureDates());
        return lectureService.convertLectureDatesToEvents(tutorLectureDates);
    }

}
