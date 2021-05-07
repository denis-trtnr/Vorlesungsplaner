package dhbw.vs.uniplaner.controller.thymeleaf;

import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.Lecture;
import dhbw.vs.uniplaner.domain.Lecturer;
import dhbw.vs.uniplaner.interfaces.IEmailSender;
import dhbw.vs.uniplaner.interfaces.ICourseService;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.interfaces.ILecturerService;
import dhbw.vs.uniplaner.interfaces.ISemesterService;
import dhbw.vs.uniplaner.service.LecturerService;
import dhbw.vs.uniplaner.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.lang.reflect.Array;
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
    @Autowired
    private IEmailSender emailSender;

    List<Lecturer> list;
    ArrayList<List<Lecturer>> list1 = new ArrayList<List<Lecturer>>();
    TreeMap<Long,List<Lecturer>> list2 = new TreeMap<Long, List<Lecturer>>();

    /**
     * Call a course and adds all lectures, lecturers and semesters to the model
     * @param model Spring MVC model Attribute
     * @param id id is used to identify the course -> CourseId
     * @return returns courseView template
     */
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

    /**
     *
     * @param lecturersId lecturersId to identify the lecturers doing this lecture
     * @param courseId courseId to identify the course the lecture should be added to
     * @param lecture lecture Object, usually taken from a form
     * @param redir RedirectAttributes object to add errors to the model temporary
     * @param model Spring MVC model Attribute
     * @return redirects to /courseview/{courseId}
     */
    @RequestMapping(value = "/save-lecture", method = RequestMethod.POST)
    public RedirectView saveLecture(@RequestParam(value = "lecturersId", required = false) List<String> lecturersId,
                              @RequestParam(value = "courseId", required = false) String courseId,
                              @ModelAttribute Lecture lecture,
                                    RedirectAttributes redir,
                                    Model model){

        RedirectView redirect = new RedirectView("/courseview/" + courseId);
        lectureService.save(lecture);

        if (lecture.getLectureName().trim().length() <= 0 ||lecture.getModulName().trim().length() <= 0){
            Boolean stringJustSpace = true;
            redir.addFlashAttribute("stringJustSpace", stringJustSpace);
        } else {
            for (String id:lecturersId) {
                Lecturer lecturer = lecturerService.findOne(Long.parseLong(id)).orElseThrow(RuntimeException::new);
                lecture.addLecturer(lecturer);
                lecturerService.update(lecturer);
            }
            lectureService.update(lecture);
            Optional<Course> course = courseService.findOne(Long.parseLong(courseId));
            course.ifPresent(thecourse -> courseService.update(thecourse.addLecture(lecture)));

        }
        return redirect;
    }

    /**
     * Takes a lecture which then is removed from the DB
     * @param id the lectureId of the lecture which should deleted
     * @param courseId last used courseId to redirect to /courseview
     * @return redirects to /courseview/{courseId}
     */
    @GetMapping(value = "/remove-lecture")
    public RedirectView removeLecture(@RequestParam(value = "idLecture", required = false) String id,
                                      @RequestParam(value = "idCourse", required = false) String courseId){
        System.out.println(id);
        System.out.println(courseId);
        lectureService.delete(Long.parseLong(id));
        String redirect = "/courseview/" + courseId;
        return new RedirectView(redirect);
    }

    /**
     *
     * @param semester
     * @param courseId
     * @param redir
     * @return
     */
    @PostMapping("/save-semester")
    public RedirectView saveSemester(@ModelAttribute Semester semester,
                               @RequestParam(value = "courseId", required = false) String courseId,
                                     RedirectAttributes redir) {
        if(semester.getStartDate().isAfter(semester.getEndDate()) ||semester.getStartDate().isEqual(semester.getEndDate())){
            Boolean endIsBeforeStart = true;
            redir.addFlashAttribute("endIsBeforeStart",endIsBeforeStart);
        } else {
            Course course = courseService.findOne(Long.parseLong(courseId)).orElseThrow(RuntimeException::new);
            courseService.update(course.addSemester(semester));
            semester.setCourse(course);
            semesterService.save(semester);
        }

        String redirect = "/courseview/" + courseId;
        return new RedirectView(redirect);
    }

    @PostMapping("/save-planning-order")
    public RedirectView savePlanningOrder(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestParam(value = "lecturersOrder", required = false) String lecturersList,
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
        System.out.println(lecturersOrder);
        int counts = lecturersOrder.size();
        List<Lecturer> values = course.getPlaningOrder();
        list2.put(course.getId(),values);
        List<Lecturer> syncedList = list2.get(course.getId());
        Thread thread = new Thread(() -> {
            synchronized (syncedList) {
                try{
                    for(int i = 0; i<counts;i++) {
                        emailSender.send(values.get(0).getEmail(), "Thomas",course);
                        syncedList.wait(120000000);
                        list2.get(course.getId()).remove(0);
                        course.setPlaningOrder(list2.get(course.getId()));
                        courseService.save(course);
                        System.out.println(course.getPlaningOrder());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        String redirect = "/adminboard";
        return new RedirectView(redirect);
    }


    @GetMapping("/finish-planning/{courseId}")
    public RedirectView finishPlanning1(@PathVariable Long courseId, @AuthenticationPrincipal UserDetails userDetails) {
        Lecturer lecturer = lecturerService.findByEmail(userDetails.getUsername());
        Course course = courseService.findOne(courseId).orElseThrow(RuntimeException::new);
        String redirect = "/lecturerboard";
        if(course.getPlaningOrder().isEmpty()) {
            redirect = redirect+"?error";
        } else if(!course.getPlaningOrder().get(0).equals(lecturer)) {
            redirect = redirect+"?error";
        } else {
            List<Lecturer> syncedList = list2.get(course.getId());
            synchronized (syncedList) {
                list2.get(course.getId()).notify();
            }
        }
        return new RedirectView(redirect);
    }
}
