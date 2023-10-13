package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Customer;
import entity.Expert;
import entity.Password;
import entity.User;
import entity.enumeration.ExpertStatusEnum;
import exception.FileReadException;
import exception.InvalidPasswordException;
import exception.PhotoSizeException;
import repository.UserRepository;
import service.PasswordService;
import service.UserService;
import repository.dto.ExpertDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository>
        implements UserService {
    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public User save(String name, String surname, String email, String password) throws InvalidPasswordException {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        if (isValidEmail(email)) customer.setEmail(email);
        customer.setRegistrationDateTime(LocalDateTime.now());
        PasswordService passwordService = new PasswordServiceImpl();
        if (passwordService.isValidPassword(password)) {
            Password pass = new Password();
            pass.setPassword(password);
            customer.setPassword(pass);
        } else {
            throw new InvalidPasswordException("Invalid password format.");
        }
        return super.save(customer);
    }

    @Override
    public User save(String name, String surname, String email, String password, int score, ExpertStatusEnum expertStatus, Path personalPhotoPath) throws InvalidPasswordException {
        Expert expert = new Expert();
        expert.setName(name);
        expert.setSurname(surname);
        if (isValidEmail(email)) expert.setEmail(email);
        PasswordService passwordService = new PasswordServiceImpl();
        if (passwordService.isValidPassword(password)) {
            Password pass = new Password();
            pass.setPassword(password);
            expert.setPassword(pass);
        } else {
            throw new InvalidPasswordException("Invalid password format.");
        }
        expert.setRegistrationDateTime(LocalDateTime.now());
        expert.setScore(score);
        expert.setExpertStatus(expertStatus);
        try {
            byte[] personalPhoto = Files.readAllBytes(personalPhotoPath);
            if (personalPhoto.length > 300 * 1024) {
                throw new PhotoSizeException("Photo size exceeds the maximum allowed size of 300 KB.");
            }
            expert.setPersonalPhoto(personalPhoto);
            return super.save(expert);
        } catch (PhotoSizeException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        } catch (IOException e) {
            throw new FileReadException("Unable to read the personal photo file.", e);
        }
    }

    @Override
    public Long findUserIdByEmail(String email) {
        return repository.findUserIdByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public List<ExpertDTO> safeLoadAllExperts() {
        return repository.safeLoadAllExperts();
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
