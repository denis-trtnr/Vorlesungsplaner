package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.exception.IllegalOperationException;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.ILectureDateService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.interfaces.ILecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;
import java.util.Set;

@Controller
public class TutorboardController {

    @Autowired
    private ILectureDateService lectureDateService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ILectureService lectureService;
    @Autowired
    private ILecturerService lecturerService;

//    @GetMapping("/tutorCourseCalendar/{id}")
//    public  String tutorCalendar(Model model) {
//        LectureDate lecturedate = new LectureDate();
//        model.addAttribute("lecturedate", lecturedate);
//        return "tutor_calendar";
//    }
//    @RequestMapping("/tutor-calendar-course/{id}")
//    public  String tutorCalendar(Model model, @PathVariable("id") Long id) {
//        Optional<Course> findCourse = courseService.findOne(id);
//        findCourse.ifPresent(course -> model.addAttribute("course", course));
//        return "tutor_calendar";
//}

    @PostMapping("/save-lecturedate")
    public RedirectView saveLectureDate(@ModelAttribute LectureDate lecturedate, @RequestParam(value = "lectureId", required = false) Long lectureId, @RequestParam(value = "lecturerId", required = false) Long lecturerId) throws IllegalOperationException {

        Lecture lecture = lectureService.findOne(lectureId).orElseThrow(RuntimeException::new);
        lecture.addLectureDate(lecturedate);
        Lecturer lecturer = lecturerService.findOne(lecturerId).orElseThrow(RuntimeException::new);
        lecturer.addLectureDate(lecturedate);

        lectureDateService.save(lecturedate);

        Long courseId = lecture.getCourse().getId();

        String redirect = "/tutorCourseCalendar/" + courseId + "/" + lecturerId;
        return new RedirectView(redirect);
    }
}
