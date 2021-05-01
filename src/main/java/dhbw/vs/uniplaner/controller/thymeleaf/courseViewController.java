package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.DegreeProgram;
import dhbw.vs.uniplaner.domain.Lecture;
import dhbw.vs.uniplaner.domain.Lecturer;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.interfaces.ILecturerService;
import dhbw.vs.uniplaner.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class courseViewController {
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ILecturerService lecturerService;
    @Autowired
    private ILectureService lectureService;

    @RequestMapping("/courseview/{id}")
    public String courseView(Model model, @PathVariable("id") Long id) {
        Optional<Course> findCourse = courseService.findOne(id);
        findCourse.ifPresent(course -> model.addAttribute("course", course));
        Lecture lecture = new Lecture();
        model.addAttribute("lecture", lecture);
        model.addAttribute("lecturers", lecturerService.findAll());
        return "courseView";
    }

    @RequestMapping(value = "/save-lecture", method = RequestMethod.POST)
    public RedirectView saveLecture(@RequestParam(value = "lecturersId", required = false) List<String> lecturersId,
                              @RequestParam(value = "courseId", required = false) String courseId,
                              @ModelAttribute Lecture lecture){
        for (String id:lecturersId) {
            Optional<Lecturer> lecturer = lecturerService.findOne(Long.parseLong(id));
            lecturer.ifPresent(lecture::addLecturer);
        }
        lectureService.save(lecture);
        Optional<Course> course = courseService.findOne(Long.parseLong(courseId));
        course.ifPresent(thecourse -> courseService.update(thecourse.addLecture(lecture)));

        System.out.println(lecture);


        String redirect = "/courseview/" + courseId;
        return new RedirectView(redirect);
    }

    @RequestMapping(value = "/remove-lecture/{id}", method = RequestMethod.DELETE)
    public RedirectView removeLecture(){
        return new RedirectView("/overview");
    }

}
