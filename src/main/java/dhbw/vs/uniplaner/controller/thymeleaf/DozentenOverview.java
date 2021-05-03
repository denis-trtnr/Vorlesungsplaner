package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.Lecture;
import dhbw.vs.uniplaner.domain.LectureDate;
import dhbw.vs.uniplaner.domain.Lecturer;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import dhbw.vs.uniplaner.interfaces.ILecturerService;
import dhbw.vs.uniplaner.web.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class DozentenOverview {
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ILecturerService lecturerService;

    @GetMapping("/dozentenboard")
    @PreAuthorize("hasAuthority('ROLE_LECTURER')")
    public String dozentenboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Lecturer lecturer = lecturerService.findByEmail(userDetails.getUsername());
        Set<Lecture> lectures = lecturer.getLectures();
        Set<Course> courses = new HashSet<>();
        Course course = new Course();
        for(Lecture lecture : lectures) {
            courses.add(lecture.getCourse());
        }
        Lecture lecture = new Lecture();
        model.addAttribute("course",course);
        model.addAttribute("lecture",lecture);
        model.addAttribute("courses",courses);
        model.addAttribute("lectures", lectures);
        model.addAttribute("lecturer", lecturer);
        return "dozent_overview";
    }
}
