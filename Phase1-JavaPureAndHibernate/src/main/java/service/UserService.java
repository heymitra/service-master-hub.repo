package service;

import base.service.BaseService;
import entity.Customer;
import entity.Expert;
import entity.User;
import exception.InvalidFormatException;

public interface UserService extends BaseService<User, Long> {
    User save(Customer customer);
    User save(Expert expert);
    User findUserByEmail(String email);
    void changePassword(User user, String newPass) throws InvalidFormatException;
}
