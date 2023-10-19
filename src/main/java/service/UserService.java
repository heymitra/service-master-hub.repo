package service;

import base.service.BaseService;
import entity.Customer;
import entity.Expert;
import entity.User;
import exception.InvalidFormatException;
import repository.dto.ExpertDTO;

import java.util.List;

public interface UserService extends BaseService<User, Long> {
    User save(Customer customer);
    User save(Expert expert);
    User findUserByEmail(String email);
    List<ExpertDTO> safeLoadAllExperts();
    void changePassword(User user, String newPass) throws InvalidFormatException;
}
