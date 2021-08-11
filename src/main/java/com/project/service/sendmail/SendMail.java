package com.project.service.sendmail;

public interface SendMail {
    void sendMail(String toEmail, String subject, String message);
    void confirmMail(String toEmail, String url);
}
