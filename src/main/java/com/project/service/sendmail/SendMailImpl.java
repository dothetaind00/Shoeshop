package com.project.service.sendmail;

import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class SendMailImpl implements SendMail {

    private static final Logger logger = LoggerFactory.getLogger(SendMailImpl.class);

    @Qualifier("getMailSender")
    @Autowired
    private JavaMailSender javaMailSender;

    //send mail to contact
    @Override
    public void sendMail(String toEmail, String subject, String fullname) {

        var mailMessage = new SimpleMailMessage();

        StringBuilder mailContent = new StringBuilder();
        mailContent.append("Sender Name : " + fullname + "\n");
        mailContent.append("Sender Email : " + toEmail + "\n");
        mailContent.append("Subject : " + subject + "\n");
        mailContent.append("Content : Xin chào quý khách hàng thân thiện :>");

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(mailContent.toString());

        try {
            javaMailSender.send(mailMessage);
        } catch (MailException ex) {
            ex.printStackTrace();
            logger.error("Cannot send email ", ex.getMessage());
        }
    }

    @Override
    public void confirmMail(String toEmail, String url, String username) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setFrom("contact@shoestore.com", "Shoe Store");
            helper.setTo(toEmail);

            String subject = "Here's the link to active your account";

            String content = "<p>Hello, " + username + "</p>"
                    + "<p>You have requested to active your account.</p>"
                    + "<p>Click the link below to active your account:</p>"
                    + "<p>" + url + "</p>"
                    + "<br>"
                    + "<p>Ignore this email if you do not register user, "
                    + "or you have not made the request.</p>";

            //message.setContent(content,"text/html");
            helper.setSubject(subject);
            helper.setText(content, true);

            this.javaMailSender.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
