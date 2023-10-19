package repository;

import entity.Expert;

import java.util.Optional;

public interface ExpertRepository {
    Optional<Expert> findById(Long id);
}
