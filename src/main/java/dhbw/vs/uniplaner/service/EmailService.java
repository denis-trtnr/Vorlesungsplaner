package dhbw.vs.uniplaner.service;

import dhbw.vs.uniplaner.interfaces.IEmailSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements IEmailSender {

    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

    private JavaMailSender mailSender = null;
    
    @Override
    @Async
    public void send(String to, String email) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Plan your lecture Dates");
            helper.setFrom("admin@fallstudie.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Failed to send email",e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
