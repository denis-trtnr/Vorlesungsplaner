package dhbw.vs.uniplaner.controller.thymeleaf;

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

@Controller
public class addElementsController {

    @Autowired
    private ICourseService courseService;
    @Autowired
    private IDegreeProgramService degreeProgramService;

//    @GetMapping("/create-degreeprogram")
//    public String createProjectForm(Model model) {
//        DegreeProgram degreeProgram = new DegreeProgram();
//        model.addAttribute("degreeprogram", degreeProgram);
//        return "create-degreeprogram";
//    }





}
