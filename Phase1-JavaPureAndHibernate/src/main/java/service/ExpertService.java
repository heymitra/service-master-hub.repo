package service;

import entity.Expert;

import java.util.Optional;

public interface ExpertService {
    Optional<Expert> findById(Long id);
}
