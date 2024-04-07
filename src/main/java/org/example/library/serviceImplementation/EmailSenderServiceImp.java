package org.example.library.serviceImplementation;


import org.example.library.exceptions.ApiException;
import org.example.library.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImp implements EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendSimpleEmail(String toEmail, String body, String subject) {
     try{
         SimpleMailMessage message=new SimpleMailMessage();
         message.setFrom("kanhaiya.kk20598@gmail.com");
         message.setTo(toEmail);
         message.setText(body);
         message.setSubject(subject);
         mailSender.send(message);

     }catch(Exception e ){
         throw  new ApiException("invalid mail");
     }

    }
}
