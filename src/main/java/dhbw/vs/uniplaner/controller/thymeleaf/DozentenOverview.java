package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.Lecturer;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import dhbw.vs.uniplaner.interfaces.ILecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DozentenOverview {
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ILecturerService lecturerService;

    @GetMapping("/dozentenboard")
    public String userIndex(Model model, @RequestParam(value="email",required=true) String email) {
        model.addAttribute("lecturer", lecturerService.findByEmail(email));
        return "dozent_overview";
    }

}
