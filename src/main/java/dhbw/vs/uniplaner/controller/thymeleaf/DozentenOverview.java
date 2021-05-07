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


}
