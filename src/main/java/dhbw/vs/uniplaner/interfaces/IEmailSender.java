package dhbw.vs.uniplaner.interfaces;

import dhbw.vs.uniplaner.domain.Course;

public interface IEmailSender {
    void send(String to, String name, Course course);
}
