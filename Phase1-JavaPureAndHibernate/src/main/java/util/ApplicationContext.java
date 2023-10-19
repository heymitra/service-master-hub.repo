package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import repository.*;
import repository.impl.*;
import service.*;
import service.impl.*;

public class ApplicationContext {
    private static EntityManager em;
    private static EntityManagerFactory emf;

    private static UserRepository userRepository;
    private static UserService userService;

    private static ServiceRepository serviceRepository;
    private static ServiceService serviceService;

    private static SubServiceRepository subServiceRepository;
    private static SubServiceService subServiceService;

    private static OrderRepository orderRepository;
    private static OrderService orderService;

    private static ExpertRepository expertRepository;
    private static ExpertService expertService;

    static {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();

        userRepository = new UserRepositoryImpl(em);
        userService = new UserServiceImpl(userRepository);

        serviceRepository = new ServiceRepositoryImpl(em);
        serviceService = new ServiceServiceImpl(serviceRepository);

        subServiceRepository = new SubServiceRepositoryImpl(em);
        subServiceService = new SubServiceServiceImpl(subServiceRepository);

        orderRepository = new OrderRepositoryImpl(em);
        orderService = new OrderServiceImpl(orderRepository);

        expertRepository = new ExpertRepositoryImpl(em);
        expertService = new ExpertServiceImpl(expertRepository);
    }

    public static UserService getUserService() {return userService;}
    public static ServiceService getServiceService() {return serviceService;}
    public static SubServiceService getSubServiceService() {return subServiceService;}
    public static OrderService getOrderService() {return orderService;}
    public static ExpertService getExpertService() {return expertService;}
}
