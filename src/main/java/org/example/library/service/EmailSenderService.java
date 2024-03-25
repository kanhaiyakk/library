package org.example.library.service;

public interface EmailSenderService {

    public void sendSimpleEmail(String toEmail,String body, String subject);
}
