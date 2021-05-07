package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.exception.IllegalOperationException;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.ILectureDateService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.interfaces.ILecturerService;
import dhbw.vs.uniplaner.service.LectureDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
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

    @GetMapping(value = "/remove-lectureDate")
    public RedirectView removeLecture(@RequestParam(value = "lectureDateId", required = false) Long lectureDateId,
                                      @RequestParam(value = "courseId", required = false) String courseId,
                                      @AuthenticationPrincipal UserDetails userDetails){

        LectureDate lectureDate = lectureDateService.findOne(lectureDateId).orElseThrow(RuntimeException::new);

        Lecture lecture = lectureDate.getLecture();
        lecture.removeLectureDate(lectureDate);
        Lecturer lecturer = lecturerService.findByEmail(userDetails.getUsername());
        lecturer.removeLectureDate(lectureDate);

        lectureService.update(lecture);
        lecturerService.update(lecturer);
        lectureDateService.update(lectureDate);

        String redirect = "/tutorCourseCalendar/" + courseId;
        return new RedirectView(redirect);
    }

    @PostMapping("/update-lecturedate")
    public RedirectView editLectureDate(@ModelAttribute LectureDate lecturedate,
                                        @RequestParam(value = "lectureDateId", required = false) Long lectureDateId,
                                        @RequestParam(value = "lectureId_edit", required = false) Long lectureId_edit,
                                        @RequestParam(value = "start_edit", required = false) String start_edit,
                                        @RequestParam(value = "end_edit", required = false) String end_edit,
                                        @RequestParam(value = "courseId", required = false) String courseId,
                                        @AuthenticationPrincipal UserDetails userDetails) throws IllegalOperationException {

        LectureDate lectureDate = lectureDateService.findOne(lectureDateId).orElseThrow(RuntimeException::new);
        LocalDateTime newStart = LocalDateTime.parse(start_edit);
        LocalDateTime newEnd = LocalDateTime.parse(end_edit);

        lectureDate.setStart(newStart);
        lectureDate.setEnd(newEnd);

        if(lectureDate.getLecture().getId() != lectureId_edit) {
            Lecture oldLecture = lectureService.findOne(lectureDate.getLecture().getId()).orElseThrow(RuntimeException::new);
            oldLecture.removeLectureDate(lectureDate);

            Lecture newLecture = lectureService.findOne(lectureId_edit).orElseThrow(RuntimeException::new);
            newLecture.addLectureDate(lectureDate);
        }

        lectureDateService.save(lecturedate);

        String redirect = "/tutorCourseCalendar/" + courseId;
        return new RedirectView(redirect);
    }
}
