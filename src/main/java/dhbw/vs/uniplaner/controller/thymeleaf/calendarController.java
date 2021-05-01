package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.DegreeProgram;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import dhbw.vs.uniplaner.interfaces.ILectureDateService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class calendarController {

    @Autowired
    private ILectureService lectureService;
    @Autowired
    private ILectureDateService lectureDateService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IDegreeProgramService degreeProgramService;


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

    @RequestMapping("/courseCalendar/{id}") // hier muss eine Liste von events übergeben werden
    public String programOverview(Model model, @PathVariable("id") Long id) {
        Optional<Course> findCourse = courseService.findOne(id);
        findCourse.ifPresent(course -> model.addAttribute("course", course));
        return "calendar_view";
    }

//    @GetMapping(path = "/lecturedates-course/{id}")
//    public ResponseEntity<List<Event>> getAllLectureDatesByCourse(@PathVariable Long id) {
////        Optional<Course> course = courseService.findOne(id);
////        course.ifPresent(allLectures = course);
////        return ResponseEntity.ok(courseService. lectureDateService.findByCourse(courseService.getid));
////        System.out.println(id);
//
//        return null;
//@GetMapping(path = "/lecturedatesOfCourse", produces = {"applicationn/json", "text/json"})
//@ResponseBody
//public ResponseEntity<List<Event>> getAllLectureDatesByCourse() {
//    return lectureService.createEventsfromLectureDates(lectureDateService.fin);
//    }
}
