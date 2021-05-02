package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.DegreeProgram;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ICourseService courseService;
    @Autowired
    private IDegreeProgramService degreeProgramService;


    @GetMapping("/")
    public String root() {
        return "redirect:/home";
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

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

//    @GetMapping("/user")
//    public String userIndex() {
//        return "user/index";
//    }


//    @RequestMapping("/programOverview/{id}")
//    public String programOverview(Model model, @PathVariable("id") Long id) {
//        Optional<DegreeProgram> degreeProgram = degreeProgramService.findOne(id);
//        degreeProgram.ifPresent(program -> model.addAttribute("program", program));
//        Course course = new Course();
//        model.addAttribute("course", course);
//        return "programOverview";
//    }




//
//    @GetMapping("/addDegree")
//    public String addDegreePrg(Model model) {
//        DegreeProgram degreeProgram = new DegreeProgram();
//        model.addAttribute("degreeprogram", degreeProgram);
//        return "addDegree";
//    }



}
