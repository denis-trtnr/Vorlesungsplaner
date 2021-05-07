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
        Course course = courseService.findOne(courseId).orElseThrow(RuntimeException::new);
        System.out.println(course.getPlaningOrder().isEmpty());
        System.out.println(course.getPlaningOrder().size());
        if(course.getPlaningOrder().isEmpty()){
            Boolean noPlaning = true;
            model.addAttribute("noPlaning", noPlaning);
            return dozentenboard(model, userDetails);
        } else if (!userDetails.getUsername().equals(course.getPlaningOrder().get(0).getEmail())){
            Boolean access = true;
            model.addAttribute("access", access);
            return dozentenboard(model, userDetails);
        }

        model.addAttribute("course", course);
        Lecturer lecturer = lecturerService.findByEmail(userDetails.getUsername());
        model.addAttribute("lecturer", lecturer);
        Set<Lecture> lectures = lecturer.getLectures();
        Set<Lecture> correctLectures = new HashSet<>();
        for(Lecture lecture : lectures) {
            if(lecture.getCourse().getId() == (courseId)) {
                correctLectures.add(lecture);
            }
        }
        model.addAttribute("lectures", correctLectures);
        LectureDate lecturedate = new LectureDate();
        model.addAttribute("lecturedate", lecturedate);
        this.currentCourse = courseId;
        return "tutor_calendar";
    }

    // Hier holt sich Fullcalendar die KursEvents
    @GetMapping(path = "/processCourse", produces = {"application/json", "text/json"})
    @ResponseBody
    public ArrayList<Event> processCourse() {
        return lectureService.convertLectureDatesToEvents(lectureDateRepository.findByCourse(this.currentCourse));
    }

//    @PostMapping("/currentCourse")
//    public  String setAktuellerKurs(@ModelAttribute(value = "course") Course course) {
//        this.currentCourse = course.getId();
//        return "redirect:/courseCalendar/";
//    }

}
