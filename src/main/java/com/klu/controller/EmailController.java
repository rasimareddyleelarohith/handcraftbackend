package com.klu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klu.service.EmailService;

@RestController
@RequestMapping("/mail")
@CrossOrigin(originPatterns = { "http://localhost:*", "http://127.0.0.1:*" }, allowedHeaders = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String sendSimpleMail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {

        return emailService.sendSimpleMail(to, subject, text);
    }

    @PostMapping("/send")
    public String sendSimpleMailFromBody(@RequestBody EmailRequest request) {
        return emailService.sendSimpleMail(request.getTo(), request.getSubject(), request.getText());
    }

    @PostMapping(value = "/send-attachment", params = { "to", "subject", "text", "path" })
    public String sendMailWithAttachment(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text,
            @RequestParam String path) {

        return emailService.sendMailWithAttachment(to, subject, text, path);
    }

    @PostMapping("/send-attachment")
    public String sendMailWithAttachment(@RequestBody EmailRequest request) {
        return emailService.sendMailWithAttachment(
                request.getTo(),
                request.getSubject(),
                request.getText(),
                request.getPath()
        );
    }

    public static class EmailRequest {
        private String to;
        private String subject;
        private String text;
        private String path;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
