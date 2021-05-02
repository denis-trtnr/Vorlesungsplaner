package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.DegreeProgram;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import dhbw.vs.uniplaner.interfaces.ILectureDateService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.repository.LectureDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CalendarController {

    @Autowired
    private ILectureService lectureService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IDegreeProgramService degreeProgramService;

    private LectureDateRepository lectureDateRepository;
    private Long currentCourse = 0L;

    public CalendarController(LectureDateRepository lectureDateRepository) {
        this.lectureDateRepository = lectureDateRepository;
    }


    @RequestMapping("/home")
    public String listCourses(Model model) {
        model.addAttribute("programs", degreeProgramService.findAll());
        DegreeProgram degreeProgram = new DegreeProgram();
        model.addAttribute("degreeprogram", degreeProgram);
        Course course = new Course();
        model.addAttribute("course", course);
        return "home";
    }

//    @GetMapping("/course-calendar")
//    public String calenderView() { return "calendar_view";}

//    @RequestMapping("/courseCalendar/{id}") // hier muss eine Liste von events übergeben werden
//    public String programOverview(Model model, @PathVariable("id") Long id) {
//        Optional<Course> findCourse = courseService.findOne(id);
//        findCourse.ifPresent(course -> model.addAttribute("course", course));
//        return "calendar_view";
//    }

//    @GetMapping(path = "/lecturedates-course/{id}")
//    public ResponseEntity<List<Event>> getAllLectureDatesByCourse(@PathVariable Long id) {
////        Optional<Course> course = courseService.findOne(id);
////        course.ifPresent(allLectures = course);
////        return ResponseEntity.ok(courseService. lectureDateService.findByCourse(courseService.getid));
////        System.out.println(id);
//
//        return null;

//    @GetMapping(path = "/lecturedatesOfCourse", produces = {"applicationn/json", "text/json"})
//    @ResponseBody
//    public ResponseEntity<List<Event>> getAllLectureDatesByCourse() {
//        return lectureService.getEventsFromLectureDates(lectureDateService.getByCourse(courseid));
//    }
@RequestMapping("/courseCalendar/{id}") // hier muss eine Liste von events übergeben werden
public String programOverview(Model model, @PathVariable("id") Long id) {
    Optional<Course> findCourse = courseService.findOne(id);
    findCourse.ifPresent(course -> model.addAttribute("course", course));
    this.currentCourse = id;
    return "calendar_view";
}

//    @PostMapping("/currentCourse")
//    public  String setAktuellerKurs(@ModelAttribute(value = "course") Course course) {
//        this.currentCourse = course.getId();
//        return "redirect:/courseCalendar/";
//    }

    @GetMapping(path = "/processCourse", produces = {"applicationn/json", "text/json"})
    @ResponseBody
    public ArrayList<Event> processCourse() {
        return lectureService.getEventsFromLectureDates(lectureDateRepository.findByCourse(this.currentCourse));
    }
}
