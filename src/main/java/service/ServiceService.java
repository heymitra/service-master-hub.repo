package service;

import base.service.BaseService;
import entity.Service;

public interface ServiceService extends BaseService<Service, Long> {
    Service save(String name);
}
