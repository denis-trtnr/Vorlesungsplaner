package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.interfaces.ILecturerService;
import dhbw.vs.uniplaner.interfaces.ISemesterService;
import dhbw.vs.uniplaner.service.LecturerService;
import dhbw.vs.uniplaner.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class courseViewController {
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ILecturerService lecturerService;
    @Autowired
    private ILectureService lectureService;
    @Autowired
    private ISemesterService semesterService;

    @RequestMapping("/courseview/{id}")
    public String courseView(Model model, @PathVariable("id") Long id) {
        Course course = courseService.findOne(id).orElseThrow(RuntimeException::new);
        model.addAttribute("course", course);
        List<Lecturer> lecturersOrder = new ArrayList<>();
        Lecture lecture = new Lecture();
        Semester semester = new Semester();
        if(!course.getPlaningOrder().isEmpty()){
            lecturersOrder = course.getPlaningOrder();
        } else {
            for (Lecture lectureX :course.getLectures()) {
                lecturersOrder.addAll(lectureX.getLecturers());
            }
            Set<Lecturer> set = new HashSet<>(lecturersOrder);
            lecturersOrder.clear();
            lecturersOrder.addAll(set);
        }
        model.addAttribute("lecture", lecture);
        model.addAttribute("lecturersList", lecturerService.findAll());
        model.addAttribute("semester", semester);
        model.addAttribute("lecturersOrder", lecturersOrder);

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

    @GetMapping(value = "/remove-lecture")
    public RedirectView removeLecture(@RequestParam(value = "idLecture", required = false) String id,
                                      @RequestParam(value = "idCourse", required = false) String courseId){
        System.out.println(id);
        System.out.println(courseId);
        lectureService.delete(Long.parseLong(id));
        String redirect = "/courseview/" + courseId;
        return new RedirectView(redirect);
    }

    @PostMapping("/save-semester")
    public RedirectView saveSemester(@ModelAttribute Semester semester,
                               @RequestParam(value = "courseId", required = false) String courseId) {
        Optional<Course> courseToFind = courseService.findOne(Long.parseLong(courseId));
        courseToFind.ifPresent(course -> courseService.update(course.addSemester(semester)));
        Course course = courseToFind.orElseThrow(RuntimeException::new);
        semester.setCourse(course);
        semesterService.save(semester);
        String redirect = "/courseview/" + courseId;
        return new RedirectView(redirect);
    }

    @PostMapping("/save-planning-order")
    public RedirectView savePlanningOrder(@RequestParam(value = "lecturersOrder", required = false) String lecturersList,
                                          @RequestParam(value = "semester", required = false) String semesterId,
                                          @RequestParam(value = "courseId", required = false) String courseId){
        Course course = courseService.findOne(Long.parseLong(courseId)).orElseThrow(RuntimeException::new);
        System.out.println("courseId" + course);
        Semester semester = semesterService.findOne(Long.parseLong(semesterId)).orElseThrow(RuntimeException::new);
        String lecturersListCleaned = lecturersList.substring(1,lecturersList.length()-1);
        List<String> searchLecturers = Arrays.asList(lecturersListCleaned.split(","));
        System.out.println(searchLecturers);
        List<Lecturer> lecturersOrder = new ArrayList<>();
        for (String id:searchLecturers) {
            Lecturer lecturer = lecturerService.findOne(Long.parseLong(id)).orElseThrow(RuntimeException::new);
            lecturersOrder.add(lecturer);
        }
        course.setPlaningOrder(lecturersOrder);
        course.setPlaningSemester(semester);
        courseService.update(course);

        String redirect = "/overview";
        return new RedirectView(redirect);
    }

}
