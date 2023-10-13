package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Service;
import repository.ServiceRepository;
import service.ServiceService;

public class ServiceServiceImpl extends BaseServiceImpl<Service, Long, ServiceRepository>
        implements ServiceService {
    public ServiceServiceImpl(ServiceRepository repository) {
        super(repository);
    }

    @Override
    public Service save(String name) {
        Service service = new Service();
        service.setServiceName(name);
        return super.save(service);
    }
}
