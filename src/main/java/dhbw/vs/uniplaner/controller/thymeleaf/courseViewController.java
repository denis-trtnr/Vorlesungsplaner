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
    @Autowired
    private IEmailSender emailSender;

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

        String link = "/tutorCourseCalendar/{courseId}" + courseId;
        emailSender.send(userDetails.getUsername(), buildEmail(lecturersOrder.get(0).getEmail(),link));

        String redirect = "/overview";
        return new RedirectView(redirect);
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Plan your lectures</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#DB4513\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi,</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> It is your turn to plan the lectures for Course " + name + ". Please click on link to the get to the planning room: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Plan now</a> </p></blockquote>\n You have two days time plan your lectures. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
