package repository;

import base.repository.BaseRepository;
import entity.User;
import repository.dto.ExpertDTO;

import java.util.List;

public interface UserRepository extends BaseRepository<User,Long> {
    User findUserByEmail(String email);
    void changePassword(User user, String newPass);
}
