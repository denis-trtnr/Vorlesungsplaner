package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.DegreeProgram;
import dhbw.vs.uniplaner.domain.Lecture;
import dhbw.vs.uniplaner.domain.LectureDate;
import dhbw.vs.uniplaner.interfaces.ILectureDateService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class TutorboardController {

    @Autowired
    private ILectureDateService lectureDateService;
    @Autowired
    private ILectureService lectureService;

    @GetMapping("/tutor-calendar")
    public  String tutorCalendar(Model model) {
        LectureDate lecturedate = new LectureDate();
        model.addAttribute("lecturedate", lecturedate);
        return "tutor_calendar";
    }

    @PostMapping("/save-lecturedate")
    public String saveDegreeProgram(@ModelAttribute LectureDate lecturedate) {
//        Optional<Lecture> lecture = lectureService.findOne(lecturedate.getTitle().getId());
//        lecture.ifPresent(lecturedate::setTitle);
        lectureDateService.save(lecturedate);
        return "redirect:/tutor-calendar";
    }
}
