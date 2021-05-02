package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.interfaces.*;
import dhbw.vs.uniplaner.repository.LectureDateRepository;
import dhbw.vs.uniplaner.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CalendarController {

    @Autowired
    private ILectureService lectureService;
    @Autowired
    private ILecturerService lecturerService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IDegreeProgramService degreeProgramService;

    private LectureDateRepository lectureDateRepository;
    private LecturerRepository lecturerRepository;
    private Long currentCourse = 0L;

    public CalendarController(LectureDateRepository lectureDateRepository) {
        this.lectureDateRepository = lectureDateRepository;
    }

    @RequestMapping("/courseCalendar/{id}")
    public String programOverview(Model model, @PathVariable("id") Long id) {
        Optional<Course> findCourse = courseService.findOne(id);
        findCourse.ifPresent(course -> model.addAttribute("course", course));
        this.currentCourse = id;
        return "calendar_view";
}

    @RequestMapping("/tutorCourseCalendar/{courseId}/{lecturerId}")
    public String tutorCalendar(Model model, @PathVariable("courseId") Long courseId, @PathVariable("lecturerId") Long lecturerId) {
        Optional<Course> findCourse = courseService.findOne(courseId);
        findCourse.ifPresent(course -> model.addAttribute("course", course));
        Optional<Lecturer> findLecturer = lecturerService.findOne(lecturerId);
        findLecturer.ifPresent(lecturer -> model.addAttribute("lecturer", lecturer));

        //Lecturer lecturer = lecturerRepository.getOne(lecturerId);
        Optional<Lecturer> lecturer = lecturerService.findOne(lecturerId);
        Set<Lecture> lectures = lecturer.orElseThrow(RuntimeException::new).getLectures();
        Set<Lecture> correctLectures = new HashSet<>();
        for(Lecture lecture : lectures) {
            if(lecture.getCourse().getId() == (courseId)) {
                correctLectures.add(lecture);
            }
        }
        model.addAttribute("lectures", correctLectures);

        LectureDate lecturedate = new LectureDate();
        model.addAttribute("lecturedate", lecturedate);
        this.currentCourse = courseId;
        return "tutor_calendar";
    }

    // Hier holt sich Fullcalendar die KursEvents
    @GetMapping(path = "/processCourse", produces = {"application/json", "text/json"})
    @ResponseBody
    public ArrayList<Event> processCourse() {
        return lectureService.convertLectureDatesToEvents(lectureDateRepository.findByCourse(this.currentCourse));
    }

//    @PostMapping("/currentCourse")
//    public  String setAktuellerKurs(@ModelAttribute(value = "course") Course course) {
//        this.currentCourse = course.getId();
//        return "redirect:/courseCalendar/";
//    }

}
