package edu.edgetech.sb2.services;

import edu.edgetech.sb2.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    //  TODO Spring will inject this class into our code to enable emailing
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * TODO so you want to send an email using code
     *  here are a few URLs to help you do that
     *      To get an app password for your mail account
     *      https://support.google.com/accounts/answer/185833
     *  https://www.baeldung.com/spring-email
     *
     *  modify the code to create other methods in which you pass along the recipient, subject and body
     */
    public void sendNotification(User user) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("gtjames@gmail.com");
        mail.setSubject("Houston, Tranquility Base here!");
        mail.setText("We have landed!");

        javaMailSender.send(mail);
    }
}
