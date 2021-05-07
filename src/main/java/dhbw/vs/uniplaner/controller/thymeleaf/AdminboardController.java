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

import java.util.Optional;

@Controller
public class AdminboardController {

    @Autowired
    private ICourseService courseService;
    @Autowired
    private IDegreeProgramService degreeProgramService;


    /**
     * Searchs for all degreeprograms and adds them to the model.
     * Ads an empty course to the view. Is used for later for adding new courses from the view.
     * @param model Spring MVC Model Attribute
     * @return returns the adminboard.html
     */
    @RequestMapping("/adminboard")
    public String listCourses(Model model) {
        model.addAttribute("programs", degreeProgramService.findAll());
        DegreeProgram degreeProgram = new DegreeProgram();
        model.addAttribute("degreeprogram", degreeProgram);
        Course course = new Course();
        model.addAttribute("course", course);
        return "adminboard";
    }

    /**
     * Takes degreeProgram and ads it to the DB.
     * In case the name contains blanks only it will add an error message
     * @param degreeprogram Degreeprogram object you want to add to the DB
     * @param redir Inherit from other request. Adds Attributes to call hidden divs in template
     * @return redirects to the /adminboard path
     */
    @PostMapping("/save-degreeprogram")
    public String saveDegreeProgram(@ModelAttribute DegreeProgram degreeprogram,
                                    RedirectAttributes redir) {
        if (degreeprogram.getName().trim().length() <= 0 || degreeprogram.getShortName().trim().length() <= 0){
            Boolean stringJustSpace = true;
            redir.addFlashAttribute("stringJustSpace", stringJustSpace);
        } else {
            degreeProgramService.save(degreeprogram);
        }

        return "redirect:/adminboard";
    }

    /**
     * Adds new course to the DB.
     * StartDate can't be before EndDate
     * String can't contain blanks only
     * @param programId Id of the program the course should be added to
     * @param course Course to be added containing CourseName, StartDate and EndDate
     * @param redir redir Inherit from other request. Adds Attributes to call hidden divs in template
     * @return
     */
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
        return "redirect:/adminboard";
    }





}
