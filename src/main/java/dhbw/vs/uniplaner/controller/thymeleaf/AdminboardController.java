package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.DegreeProgram;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.model.IModel;

import java.util.List;
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
    public String saveDegreeProgram(@ModelAttribute DegreeProgram degreeprogram,
                                    RedirectAttributes redir) {
        if (degreeprogram.getName().trim().length() <= 0 || degreeprogram.getShortName().trim().length() <= 0){
            Boolean stringJustSpace = true;
            redir.addFlashAttribute("stringJustSpace", stringJustSpace);
        } else {
            degreeProgramService.save(degreeprogram);
        }

        return "redirect:/overview";
    }

    @PostMapping("/save-course")
    public String saveCourse(@RequestParam(value = "programId", required = false) Long programId,
                             @ModelAttribute Course course,
                             RedirectAttributes redir) {
        if(course.getStartDate().isAfter(course.getEndDate()) ||course.getStartDate().isEqual(course.getEndDate())){
            Boolean endIsBeforeStart = true;
            redir.addFlashAttribute("endIsBeforeStart", endIsBeforeStart);
        }else if (course.getCourseName().trim().length() <= 0){
            Boolean stringJustSpace = true;
            redir.addFlashAttribute("stringJustSpace", stringJustSpace);
        } else {
            Optional<DegreeProgram> degreeProgram = degreeProgramService.findOne(programId);
            degreeProgram.ifPresent(course::setDegreeProgram);
            courseService.save(course);
        }
        return "redirect:/overview";
    }





}
