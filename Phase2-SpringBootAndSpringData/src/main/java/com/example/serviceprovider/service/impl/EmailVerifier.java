package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EmailVerifier {
    private final JavaMailSender javaMailSender;

    private final Map<String, String> verificationCodeMap = new ConcurrentHashMap<>();

    public EmailVerifier(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendActivationEmail(User user) {
        // Generate verification code
        String verificationCode = generateVerificationCode();

        // Link verification code to user using email as the key
        verificationCodeMap.put(user.getEmail(), verificationCode);

        // Generate activation link with secure token
        String activationLink =
                "http://localhost:8080/user/activate?email=" +
                        user.getEmail() + "&token=" + verificationCode;

        // Create email content
        String emailContent = "Click the following link to activate your account: " + activationLink;

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Account Activation");
        message.setText(emailContent);
        javaMailSender.send(message);
    }

    public boolean verifyCode(String email, String code) {
        // Retrieve the stored verification code
        String storedCode = verificationCodeMap.get(email);

        // Verify that the stored code matches the provided code
        return storedCode != null && storedCode.equals(code);
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }
}
