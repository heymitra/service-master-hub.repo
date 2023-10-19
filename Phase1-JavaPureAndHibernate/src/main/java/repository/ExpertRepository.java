package repository;

import base.repository.BaseRepository;
import entity.Expert;
import entity.enumeration.ExpertStatusEnum;
import repository.dto.ExpertDTO;

import java.util.List;
import java.util.Optional;

public interface ExpertRepository extends BaseRepository<Expert, Long> {
    Optional<Expert> findById(Long id);
    List<ExpertDTO> findExpertsByStatus(ExpertStatusEnum expertStatus);
    List<ExpertDTO> safeLoadAllExperts();
}
