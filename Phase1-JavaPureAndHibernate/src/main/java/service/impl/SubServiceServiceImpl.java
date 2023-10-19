package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Expert;
import entity.SubService;
import exception.SubServiceAlreadyExistsException;
import exception.SubServiceNotFoundException;
import repository.SubServiceRepository;
import service.SubServiceService;
import util.ApplicationContext;

import java.util.List;
import java.util.Optional;

public class SubServiceServiceImpl extends BaseServiceImpl<SubService, Long, SubServiceRepository>
        implements SubServiceService {
    public SubServiceServiceImpl(SubServiceRepository repository) {
        super(repository);
    }

    @Override
    public SubService save(SubService subService) {
        String subServiceName = subService.getSubServiceName();
        Optional<SubService> existingSubService = findBySubServiceName(subServiceName);
        if (existingSubService.isPresent()) {
            throw new SubServiceAlreadyExistsException(subServiceName);
        }
        return repository.save(subService);
    }

    @Override
    public Optional<SubService> findBySubServiceName(String subServiceName) {
        return repository.findBySubServiceName(subServiceName);
    }

    @Override
    public List<SubService> findSubServicesByServiceId(Long serviceId) {
        return repository.findSubServicesByServiceId(serviceId);
    }

    @Override
    public void addExpertToSubService(Long expertId, Long subServiceId) {
        Optional<SubService> subServiceOptional = super.findById(subServiceId);
        Optional<Expert> expertOptional = ApplicationContext.getExpertService().findById(expertId);

        if (subServiceOptional.isPresent() && expertOptional.isPresent()) {
            SubService subService = subServiceOptional.get();
            Expert expert = expertOptional.get();

            repository.addExpert(expert, subService);
        } else {
            throw new SubServiceNotFoundException(subServiceId);
        }
    }

    @Override
    public void removeExpertFromSubService(Long expertId, Long subServiceId) {
        Optional<SubService> subServiceOptional = super.findById(subServiceId);
        Optional<Expert> expertOptional = ApplicationContext.getExpertService().findById(expertId);

        if (subServiceOptional.isPresent() && expertOptional.isPresent()) {
            SubService subService = subServiceOptional.get();
            Expert expert = expertOptional.get();

            repository.removeExpert(expert, subService);
        } else {
            throw new SubServiceNotFoundException(subServiceId);
        }
    }

}
