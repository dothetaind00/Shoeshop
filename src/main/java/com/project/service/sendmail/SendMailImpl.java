package com.project.service.sendmail;

import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailImpl implements SendMail {

    private static final Logger logger = LoggerFactory.getLogger(SendMailImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

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
}
