package service;

import base.service.BaseService;
import entity.SubService;

import java.util.List;
import java.util.Optional;

public interface SubServiceService extends BaseService<SubService, Long> {
    SubService save(String name, double basePrice, String description, Long serviceId);
    Optional<SubService> findBySubServiceName(String subServiceName);
    List<SubService> findSubServicesByServiceId(Long serviceId);
    void addExpertToSubService(Long expertId, Long subServiceId);
    void removeExpertFromSubService(Long expertId, Long subServiceId);
}
