package com.example.serviceprovider.service.impl;

import com.example.serviceprovider.model.UserToken;
import com.example.serviceprovider.model.User;
import com.example.serviceprovider.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class MailSender {
    private final JavaMailSender javaMailSender;
    private final UserTokenService userTokenService;

    public void sendActivationEmail(User user) {
        String token = generateActivationToken(user);

        String activationLink = "http://localhost:8080/user/activate?token=" + token;

        String emailContent = "Dear " + user.getName() +
                "\nWelcome to the service provider website." +
                "\nTo finalize the registration process, please activate your email address by clicking the following link:" +
                "\n\nYour username is your email address, which you used for registration." +
                "\nYour password is the one you entered during registration." +
                "\n\nActivation Link: " + activationLink;

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Account Activation");
        message.setText(emailContent);
        javaMailSender.send(message);
    }

    private String generateActivationToken(User user) {
        UserToken userToken = new UserToken();
        userToken.setUser(user);

        boolean saved = false;
        String token = null;

        while (!saved) {
            token = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
            userToken.setActivationToken(token);
            try {
                userTokenService.save(userToken);
                saved = true;
            } catch (DataIntegrityViolationException ignored) {
            }
        }
        return(token);
    }
}
