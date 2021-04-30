package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.DegreeProgram;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.model.IModel;

import java.util.Optional;

@Controller
public class AdminboardController {

    @Autowired
    private ICourseService courseService;
    @Autowired
    private IDegreeProgramService degreeProgramService;

@RequestMapping("/overview")
public String listCourses(Model model) {
    model.addAttribute("programs", degreeProgramService.findAll());
    DegreeProgram degreeProgram = new DegreeProgram();
    model.addAttribute("degreeprogram", degreeProgram);
    Course course = new Course();
    model.addAttribute("course", course);
    return "overview";
}

    @PostMapping("/save-degreeprogram")
    public String saveDegreeProgram(@ModelAttribute DegreeProgram degreeprogram) {
        degreeProgramService.save(degreeprogram);
        return "redirect:/overview";
    }

    @PostMapping("/save-course")
    public String saveCourse(@ModelAttribute Course course) {
        Optional<DegreeProgram> degreeProgram = degreeProgramService.findOne(course.getDegreeProgramID());
        degreeProgram.ifPresent(course::setDegreeProgram);
        courseService.save(course);
        return "redirect:/overview";
    }





}
