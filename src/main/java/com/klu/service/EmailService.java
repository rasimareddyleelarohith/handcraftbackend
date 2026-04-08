package com.klu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendSimpleMail(String to, String subject, String text) {
        try {
            if (isBlank(to) || isBlank(subject) || isBlank(text)) {
                return "Error: to, subject, and text are required";
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);

            return "Simple Email Sent Successfully";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String sendMailWithAttachment(String to, String subject, String text, String path) {
        try {
            if (isBlank(to) || isBlank(subject) || isBlank(text)) {
                return "Error: to, subject, and text are required";
            }

            if (isBlank(path)) {
                return "Error: attachment path is required";
            }

            File attachment = new File(path);
            if (!attachment.exists() || !attachment.isFile()) {
                return "Error: attachment file not found";
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(attachment);
            helper.addAttachment(file.getFilename(), file);

            mailSender.send(message);

            return "Email With Attachment Sent Successfully";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

