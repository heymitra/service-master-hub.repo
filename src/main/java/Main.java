import entity.*;
import entity.enumeration.OrderStatusEnum;
import exception.*;
import repository.dto.ExpertDTO;
import util.ApplicationContext;
import util.InputUtility;
import util.SecurityContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        ApplicationContext applicationContext = new ApplicationContext();

        // امکان افزودن کاربر جدید با توجه به نقش (متخصص/مشتری)
        // add customer
        System.out.println("Adding new user:\n\tCustomer:");
        String name = InputUtility.getValidName("\t\tName: ");
        String surname = InputUtility.getValidName("\t\tSurname: ");
        String email = InputUtility.getUserInput("\t\tEmail: ");
        String password = InputUtility.getUserInput("\t\tPassword: ");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        customer.setEmail(email);
        customer.setPassword(password);

        ApplicationContext.getUserService().save(customer);

//        // add expert
//        System.out.println("Adding new user:\n\tExpert:");
//        String name = InputUtility.getValidName("\t\tName: ");
//        String surname = InputUtility.getValidName("\t\tSurname: ");
//        String email = InputUtility.getUserInput("\t\tEmail: ");
//        String password = InputUtility.getUserInput("\t\tPassword: ");
//        byte[] personalPhoto = null;
//        while (personalPhoto == null)
//            personalPhoto = InputUtility.getPhoto("Photo Path: ");
//        Expert expert = new Expert();
//        expert.setName(name);
//        expert.setSurname(surname);
//        expert.setEmail(email);
//        expert.setPassword(password);
//        expert.setPersonalPhoto(personalPhoto);
//        ApplicationContext.getUserService().save(expert);
//
//
//        // قابلیت تغییر رمز عبور برای کاربران
//        String username = "test@gmail.com";
//        SecurityContext.username = username;
//        User user = ApplicationContext.getUserService().findUserByEmail(username);
//        System.out.println("Changing Password... ");
//        String newPass = null;
//        String confirmedPass = null;
//        while (newPass==null || !(newPass.equals(confirmedPass))) {
//            newPass = InputUtility.getUserInput("\tNew Password: ");
//            confirmedPass = InputUtility.getUserInput("\tConfirm Password: ");
//        }
//        try {
//            ApplicationContext.getUserService().changePassword(user, newPass);
//        } catch (InvalidPasswordException ipe) {
//            System.err.println("Invalid password: " + ipe.getMessage());
//        }
//
//        // ثبت خدمات یا زیر خدمات جدید برای خدمت های تعریف شده (مدیر)
//        // load all existing services
//        Map<Long, String> serviceById = loadAllExistingServices();
//        System.out.println("Existing Services: (id. service name)");
//        serviceById.forEach((id, service) ->
//                System.out.println(id + ". " + service));
//        // add new service
//        SecurityContext.username = "admin@gmail.com";
//        if (SecurityContext.username.equals("admin@gmail.com")) {
//            System.out.println("\nAdd New Service: ");
//            String serviceName = InputUtility.getValidName("Service Name: ");
//            Service service = new Service();
//            service.setServiceName(serviceName);
//            ApplicationContext.getServiceService().save(service);
//        } else
//            System.out.println("You do not have access to this section. ");
//
//        // add new sub-service
//        SubService subService = new SubService();
//        System.out.println("Existing Services: (id. service name)");
//        Map<Long, String> serviceById = loadAllExistingServices();
//        serviceById.forEach((id, service) ->
//                System.out.println(id + ". " + service));
//        Long serviceId = InputUtility.getUserInputForServiceId(serviceById, "Enter the id of service to which you want to add sub-service: ");
//        Optional<Service> optionalService = ApplicationContext.getServiceService().findById(serviceId);
//        if (optionalService.isPresent()) {
//            Service service = optionalService.get();
//            subService.setService(service);
//        } else {
//            throw new ServiceNotFoundException(serviceId);
//        }
//        String subServiceName = InputUtility.getValidName("Sub-Service Name: ");
//        double basePrice = InputUtility.getRangedDoubleFromUser(0, "Base Price: ");
//        String description = InputUtility.getUserInput("Description: ");
//        subService.setSubServiceName(subServiceName);
//        subService.setDescription(description);
//        subService.setBasicPrice(basePrice);
//        ApplicationContext.getSubServiceService().save(subService);
//
//        // اضافه و حذف متخصصان از زیر خدمت های موجود در سیستم (مدیر)
//        // add
//        List<ExpertDTO> expertDTOS = ApplicationContext.getUserService().safeLoadAllExperts();
//        Long expertId = InputUtility.getValidExpertId(expertDTOS);
//        System.out.println("Existing Services: (id. service name)");
//        Map<Long, String> serviceById = loadAllExistingServices();
//        serviceById.forEach((id, service) ->
//                System.out.println(id + ". " + service));
//        Long serviceId = InputUtility.getUserInputForServiceId(serviceById, "Enter the ID of the expert to assign a sub-service: ");
//        List<SubService> subServicesByServiceId = ApplicationContext.getSubServiceService().findSubServicesByServiceId(serviceId);
//        for (SubService subService : subServicesByServiceId)
//            System.out.println(subService);
//        Long subServiceId = InputUtility.getValidSubServiceId(subServicesByServiceId);
//        try {
//            ApplicationContext.getSubServiceService().addExpertToSubService(expertId, subServiceId);
//        } catch (UserNotFoundException | SubServiceNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        // remove
//        List<ExpertDTO> expertDTOS = ApplicationContext.getUserService().safeLoadAllExperts();
//        Long expertId = InputUtility.getValidExpertId(expertDTOS);
//        Map<Long, String> serviceById = getExistingServices();
//        Long serviceId = InputUtility.getUserInputForServiceId(serviceById, "Enter the ID of the expert to remove from a sub-service: ");
//        List<SubService> subServicesByServiceId = ApplicationContext.getSubServiceService().findSubServicesByServiceId(serviceId);
//        for (SubService subService : subServicesByServiceId)
//            System.out.println(subService);
//        Long subServiceId = InputUtility.getValidSubServiceId(subServicesByServiceId);
//        try {
//            ApplicationContext.getSubServiceService().removeExpertFromSubService(expertId, subServiceId);
//        } catch (UserNotFoundException | SubServiceNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//
//        // order
//        String email = "customer@gmail.com";
//        SecurityContext.username = email;
//        Order order = new Order();
//        order.setCustomer((Customer) ApplicationContext.getUserService().findUserByEmail(email));
//        Map<Long, String> serviceById = getExistingServices();
//        Long serviceId = InputUtility.getUserInputForServiceId(serviceById, "Enter the ID of the expert to remove from a sub-service: ");
//        List<SubService> subServicesByServiceId = ApplicationContext.getSubServiceService().findSubServicesByServiceId(serviceId);
//        for (SubService subService : subServicesByServiceId)
//            System.out.println(subService);
//        Long subServiceId = InputUtility.getValidSubServiceId(subServicesByServiceId);
//        SubService subService = ApplicationContext.getSubServiceService().findById(serviceId).get();
//        order.setSubService(subService);
//        String address = InputUtility.getUserInput("Address: ");
//        order.setAddress(address);
//        String workDescription = InputUtility.getUserInput("Work Description: ");
//        order.setWorkDescription(workDescription);
//        double price = InputUtility.getRangedDoubleFromUser(subService.getBasicPrice(),"Proposed Price: ");
//        order.setProposedPrice(price);
//        LocalDateTime completionDateTime = InputUtility.getValidFutureOrPresentDateTime();
//        order.setCompletionDateTime(completionDateTime);
//        OrderStatusEnum status = OrderStatusEnum.WAITING_FOR_OFFERS;
//        order.setStatus(status);
//        ApplicationContext.getOrderService().save(order);
//
//
//        // توضیح و قیمت پایه باید قابلیت ویرایش داشته باشند
//        System.out.println("Edit description/base price");
//        Map<Long, String> serviceById = getExistingServices();
//        Long serviceId = InputUtility.getUserInputForServiceId(serviceById, "Service ID: ");
//        List<SubService> subServicesByServiceId = ApplicationContext.getSubServiceService().findSubServicesByServiceId(serviceId);
//        for (SubService subService : subServicesByServiceId)
//            System.out.println(subService);
//        Long subServiceId = InputUtility.getValidSubServiceId(subServicesByServiceId);
//        System.out.println("Edit description/base price");
//        String newDescription = InputUtility.getUserInput("New description: ");
//        double updatedBasePrice = InputUtility.getPositiveDoubleFromUser("Updated base price: ");
//        Optional<SubService> optionalSubService = ApplicationContext.getSubServiceService().findById(subServiceId);
//
//        if (optionalSubService.isPresent()) {
//            SubService subService = optionalSubService.get();
//            subService.setDescription(newDescription);
//            subService.setBasicPrice(updatedBasePrice);
//            ApplicationContext.getSubServiceService().update(subService);
//        } else {
//            System.out.println("SubService with ID " + subServiceId + " not found.");
//        }
    }

    public static Map<Long, String> loadAllExistingServices() {
        List<Service> services = ApplicationContext.getServiceService().loadAll();
        return services.stream()
                .collect(Collectors.toMap(Service::getId, Service::getServiceName));
    }

    public static Map<Long, String> getExistingServices() {
        System.out.println("Existing Services: (id. service name)");
        Map<Long, String> serviceById = loadAllExistingServices();
        serviceById.forEach((id, service) ->
                System.out.println(id + ". " + service));
        return serviceById;
    }

}
