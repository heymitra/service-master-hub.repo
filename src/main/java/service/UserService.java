package service;

import base.service.BaseService;
import entity.User;
import entity.enumeration.ExpertStatusEnum;
import exception.InvalidPasswordException;
import repository.dto.ExpertDTO;

import java.nio.file.Path;
import java.util.List;

public interface UserService extends BaseService<User, Long> {
    User save(String name, String surname, String email, String password) throws InvalidPasswordException;
    User save(String name, String surname, String email, String password, int score, ExpertStatusEnum expertStatus, Path personalPhotoPath) throws InvalidPasswordException;
    Long findUserIdByEmail(String email);
    User findUserByEmail(String email);
    List<ExpertDTO> safeLoadAllExperts();
}
