package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.exception.IllegalOperationException;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.ILectureDateService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.interfaces.ILecturerService;
import dhbw.vs.uniplaner.service.LectureDateService;
import dhbw.vs.uniplaner.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.SQLOutput;
import java.util.ArrayList;
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
    public RedirectView saveLectureDate(@ModelAttribute LectureDate lecturedate,
                                        @RequestParam(value = "lectureId", required = false) Long lectureId,
                                        RedirectAttributes redir,
                                        @AuthenticationPrincipal UserDetails userDetails) throws IllegalOperationException {
        Lecture lecture = lectureService.findOne(lectureId).orElseThrow(RuntimeException::new);
        Long courseId = lecture.getCourse().getId();
        Course course = courseService.findOne(courseId).orElseThrow(RuntimeException::new);
        Set<Lecture> allLectures = course.getLectures();
        String redirect = "/tutorCourseCalendar/" + courseId;

        for (Lecture courseLecture: allLectures) {
            for (LectureDate courseLectureDate :courseLecture.getLectureDates()) {
                if(lecturedate.getStart().getDayOfYear() == courseLectureDate.getEnd().getDayOfYear()
                        && (lecturedate.getStart().getHour()+(lecturedate.getStart().getMinute()/60)) - (courseLectureDate.getEnd().getHour()+(courseLectureDate.getEnd().getMinute()/60)) < 0
                        && (courseLectureDate.getStart().getHour()+(courseLectureDate.getStart().getMinute()/60)) - (lecturedate.getEnd().getHour()+(lecturedate.getEnd().getMinute()/60)) < 0
                        || lecturedate.getStart().getDayOfYear() == courseLectureDate.getEnd().getDayOfYear()
                        && (lecturedate.getEnd().getHour()+(lecturedate.getEnd().getMinute()/60)) - (courseLectureDate.getStart().getHour()+(courseLectureDate.getStart().getMinute()/60)) > 0
                        && (courseLectureDate.getEnd().getHour()+(courseLectureDate.getEnd().getMinute()/60)) - (lecturedate.getStart().getHour()+(lecturedate.getStart().getMinute()/60)) > 0
                        ||lecturedate.getStart().isAfter(courseLectureDate.getStart()) && lecturedate.getStart().isBefore(courseLectureDate.getEnd())
                        || lecturedate.getEnd().isAfter(courseLectureDate.getStart()) && lecturedate.getStart().isBefore(courseLectureDate.getEnd()) ) {
                    Boolean dateOverlap = true;
                    redir.addFlashAttribute("dateOverlap", dateOverlap);
                    return new RedirectView(redirect);
                }
            }
        }
        lecture.addLectureDate(lecturedate);
        Lecturer lecturer = lecturerService.findByEmail(userDetails.getUsername());
        lecturer.addLectureDate(lecturedate);
        lectureDateService.save(lecturedate);
        lecturerService.update(lecturer);
        lectureService.update(lecture);


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
    public RedirectView editLectureDate(@RequestParam(value = "lectureDateId", required = false) Long lectureDateId,
                                        @RequestParam(value = "lectureId_edit", required = false) Long lectureId_edit,
                                        @RequestParam(value = "start_edit", required = false) String start_edit,
                                        @RequestParam(value = "end_edit", required = false) String end_edit,
                                        @RequestParam(value = "courseId", required = false) String courseId,
                                        RedirectAttributes redir) throws IllegalOperationException {

        LectureDate lectureDate = lectureDateService.findOne(lectureDateId).orElseThrow(RuntimeException::new);
        LocalDateTime newStart = LocalDateTime.parse(start_edit);
        LocalDateTime newEnd = LocalDateTime.parse(end_edit);
        System.out.println("Here");
        Course course = courseService.findOne(Long.parseLong(courseId)).orElseThrow(RuntimeException::new);
        Set<Lecture> allLectures = course.getLectures();
        String redirect = "/tutorCourseCalendar/" + courseId;
        System.out.println("Here2");
        for (Lecture courseLecture: allLectures) {
            for (LectureDate courseLectureDate :courseLecture.getLectureDates()) {
                System.out.println(courseLectureDate.getId());
                System.out.println(lectureDate.getId());
                System.out.println("JA HIER ISA");
                if(!lectureDate.getId().equals(courseLectureDate.getId())){
                    System.out.println("Hier iss noch");
                    System.out.println(newStart.getDayOfYear());
                    System.out.println(courseLectureDate.getEnd().getDayOfYear());
                    if(newStart.getDayOfYear() == courseLectureDate.getEnd().getDayOfYear()
                            && (newStart.getHour()+(newStart.getMinute()/60)) - (courseLectureDate.getEnd().getHour()+(courseLectureDate.getEnd().getMinute()/60)) < 0
                            && (courseLectureDate.getStart().getHour()+(courseLectureDate.getStart().getMinute()/60)) - (newEnd.getHour()+(newEnd.getMinute()/60)) < 0
                            || newStart.getDayOfYear() == courseLectureDate.getEnd().getDayOfYear()
                            && (newEnd.getHour()+(newEnd.getMinute()/60)) - (courseLectureDate.getStart().getHour()+(courseLectureDate.getStart().getMinute()/60)) > 0
                            && (courseLectureDate.getEnd().getHour()+(courseLectureDate.getEnd().getMinute()/60)) - (newStart.getHour()+(newStart.getMinute()/60)) > 0
                            ||newStart.isAfter(courseLectureDate.getStart()) && newStart.isBefore(courseLectureDate.getEnd())
                            || newEnd.isAfter(courseLectureDate.getStart()) && newStart.isBefore(courseLectureDate.getEnd()) ) {
                        System.out.println("JA NE HIER GEHT");
                        Boolean dateOverlap = true;
                        redir.addFlashAttribute("dateOverlap", dateOverlap);
                        return new RedirectView(redirect);
                    }
                }
            }
        }

        lectureDate.setStart(newStart);
        lectureDate.setEnd(newEnd);
        System.out.println("Here3");
        if(lectureDate.getLecture().getId() != lectureId_edit) {
            Lecture oldLecture = lectureService.findOne(lectureDate.getLecture().getId()).orElseThrow(RuntimeException::new);
            oldLecture.removeLectureDate(lectureDate);

            Lecture newLecture = lectureService.findOne(lectureId_edit).orElseThrow(RuntimeException::new);
            newLecture.addLectureDate(lectureDate);
        }

        lectureDateService.save(lectureDate);

        return new RedirectView(redirect);
    }
}
