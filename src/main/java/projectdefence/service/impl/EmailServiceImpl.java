package projectdefence.service.impl;


import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import projectdefence.service.EmailService;

import javax.mail.internet.MimeMessage;

import static projectdefence.messages.EmailData.EMAIL_ADDRESS;

@Service
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender emailSender;
    private final SimpleMailMessage template;

    public EmailServiceImpl(JavaMailSender emailSender, SimpleMailMessage template) {

        this.emailSender = emailSender;
        this.template = template;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(EMAIL_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(EMAIL_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
