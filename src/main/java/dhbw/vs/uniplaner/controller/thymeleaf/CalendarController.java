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

    @RequestMapping("/courseCalendar/{id}")
    public String programOverview(Model model, @PathVariable("id") Long id) {
        Optional<Course> findCourse = courseService.findOne(id);
        findCourse.ifPresent(course -> model.addAttribute("course", course));
        this.currentCourse = id;
        return "calendar_view";
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
