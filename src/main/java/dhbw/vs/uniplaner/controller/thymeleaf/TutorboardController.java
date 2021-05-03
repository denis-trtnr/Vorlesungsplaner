package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.exception.IllegalOperationException;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.ILectureDateService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.interfaces.ILecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/save-lecturedate")
    @PreAuthorize("hasAuthority('ROLE_LECTURER')")
    public RedirectView saveLectureDate(@ModelAttribute LectureDate lecturedate, @RequestParam(value = "lectureId", required = false) Long lectureId, @AuthenticationPrincipal UserDetails userDetails) throws IllegalOperationException {

        Lecture lecture = lectureService.findOne(lectureId).orElseThrow(RuntimeException::new);
        lecture.addLectureDate(lecturedate);
        Lecturer lecturer = lecturerService.findByEmail(userDetails.getUsername());
        lecturer.addLectureDate(lecturedate);

        lectureDateService.save(lecturedate);

        Long courseId = lecture.getCourse().getId();

        String redirect = "/tutorCourseCalendar/" + courseId;
        return new RedirectView(redirect);
    }
}
