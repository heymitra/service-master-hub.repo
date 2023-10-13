package repository;

import base.repository.BaseRepository;
import entity.User;
import repository.dto.ExpertDTO;

import java.util.List;

public interface UserRepository extends BaseRepository<User,Long> {
    Long findUserIdByEmail(String email);
    User findUserByEmail(String email);
    List<ExpertDTO> safeLoadAllExperts();
}
