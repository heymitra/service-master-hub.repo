package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Customer;
import entity.Expert;
import entity.User;
import entity.enumeration.ExpertStatusEnum;
import exception.InvalidFormatException;
import repository.UserRepository;
import service.UserService;
import repository.dto.ExpertDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository>
        implements UserService {
    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public User save(Customer customer)  {
        try {
            if (isValidEmail(customer.getEmail()) || isValidPassword(customer.getPassword()));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        customer.setRegistrationDateTime(LocalDateTime.now());
        return super.save(customer);
    }

    @Override
    public User save(Expert expert) {
        try {
            if (isValidEmail(expert.getEmail()) || isValidPassword(expert.getPassword()));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        expert.setRegistrationDateTime(LocalDateTime.now());
        expert.setScore(0);
        expert.setExpertStatus(ExpertStatusEnum.NEW);
        return super.save(expert);
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public List<ExpertDTO> safeLoadAllExperts() {
        return repository.safeLoadAllExperts();
    }

    private boolean isValidEmail(String email) throws InvalidFormatException {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()) {
            throw new InvalidFormatException("Invalid email format.");
        } else
            return true;
    }

    private boolean isValidPassword(String password) throws InvalidFormatException {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        if (!password.matches(passwordRegex)){
            throw new InvalidFormatException("Invalid password or email format.");
        }
        else
            return true;
    }

    @Override
    public void changePassword(User user, String newPass) throws InvalidFormatException {
        if (!isValidPassword(newPass))
            throw new InvalidFormatException("Invalid password format.");
        repository.changePassword(user, newPass);
    }
}
