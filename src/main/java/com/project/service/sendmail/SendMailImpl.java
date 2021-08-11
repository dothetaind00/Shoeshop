package com.project.service.sendmail;

import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
        mailContent.append("Sender Email : "+ toEmail + "\n");
        mailContent.append("Subject : "+ subject + "\n");
        mailContent.append("Content : Xin chào quý khách hàng thân thiện :>");

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(mailContent.toString());

        try{
            javaMailSender.send(mailMessage);
        }catch (MailException ex){
            ex.printStackTrace();
            logger.error("Cannot send email ",ex.getMessage());
        }
    }

    @Override
    public void confirmMail(String toEmail, String url) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            StringBuilder htmlMsg = new StringBuilder();
            htmlMsg.append("<meta charset=\"UTF-8\">");
            htmlMsg.append("<h3>Chao mung ban den boi website</h3>");
            htmlMsg.append("<p>Click vao duong dan de kick hoat tai khoan</p>");
            htmlMsg.append(url);

            mimeMessage.setContent(htmlMsg.toString(),"text/html");

            mimeMessageHelper.setSubject("Active account");
            mimeMessageHelper.setTo(toEmail);

            this.javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
