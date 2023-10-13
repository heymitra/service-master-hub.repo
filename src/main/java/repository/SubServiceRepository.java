package repository;

import base.repository.BaseRepository;
import entity.Expert;
import entity.SubService;

import java.util.List;
import java.util.Optional;

public interface SubServiceRepository extends BaseRepository<SubService,Long> {
    Optional<SubService> findBySubServiceName(String subServiceName);
    SubService save(SubService subService);
    List<SubService> findSubServicesByServiceId(Long serviceId);
    void addExpert(Expert expert, SubService subService);
    void removeExpert(Expert expert, SubService subService);
}
