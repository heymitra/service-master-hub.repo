import entity.Service;
import entity.SubService;
import entity.enumeration.ExpertStatusEnum;
import entity.enumeration.OrderStatusEnum;
import exception.InvalidPasswordException;
import exception.SubServiceNotFoundException;
import exception.UserNotFoundException;
import repository.dto.ExpertDTO;
import util.ApplicationContext;
import util.InputUtility;
import util.SecurityContext;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        ApplicationContext applicationContext = new ApplicationContext();

        // امکان افزودن کاربر جدید با توجه به نقش (متخصص/مشتری)
        // add customer
//        System.out.println("Adding new user:\n\tCustomer:");
//        String customerName = InputUtility.getValidName("\t\tName: ");
//        String customerSurname = InputUtility.getValidName("\t\tSurname: ");
//        String customerEmail = InputUtility.getUserInput("\t\tEmail: ");
//        String customerPassword = InputUtility.getUserInput("\t\tPassword: ");
//        try {
//            ApplicationContext.getUserService().save(customerName, customerSurname, customerEmail, customerPassword);
//        } catch (InvalidPasswordException ipe) {
//            System.err.println("Invalid password: " + ipe.getMessage());
//        }

//        // add expert
//        System.out.println("Adding new user:\n\tExpert:");
//        String expertName = InputUtility.getValidName("\t\tName: ");
//        String expertSurname = InputUtility.getValidName("\t\tSurname: ");
//        String expertEmail = InputUtility.getUserInput("\t\tEmail: ");
//        String expertPassword = InputUtility.getUserInput("\t\tPassword: ");
//        Path expertPersonalPhotoPath = InputUtility.getPhotoPath("Photo Path: ");
//        try {
//            ApplicationContext.getUserService().save(expertName, expertSurname, expertEmail, expertPassword, 0, ExpertStatusEnum.NEW, expertPersonalPhotoPath);
//        } catch (InvalidPasswordException ipe) {
//            System.err.println("Invalid password: " + ipe.getMessage());
//        }
//
        // قابلیت تغییر رمز عبور برای کاربران
//        String username = "test@gmail.com";
//        SecurityContext.username = username;
//        String oldPass = ApplicationContext.getPasswordService().getPasswordByEmail(username);
//        System.out.println("Changing password: \n\tOld Password: " + oldPass);
//        String newPass = InputUtility.getUserInput("\tNew Password: ");
//        try {
//            ApplicationContext.getPasswordService().changePassword(username, newPass);
//        } catch (InvalidPasswordException ipe) {
//            System.err.println("Invalid password: " + ipe.getMessage());
//        }

        // ثبت خدمات یا زیر خدمات جدید برای خدمت های تعریف شده (مدیر)
        // load all existing services
//        Map<Long, String> serviceById = loadAllExistingServices();
//        System.out.println("Existing Services: (id. service name)");
//        serviceById.forEach((id, service) ->
//                System.out.println(id + ". " + service));
//        // add new service
//        SecurityContext.username = "admin@gmail.com";
//        if (SecurityContext.username.equals("admin@gmail.com")) {
//            System.out.println("\nAdd New Service: ");
//            String serviceName = InputUtility.getValidName("Service Name: ");
//            ApplicationContext.getServiceService().save(serviceName);
//        } else
//            System.out.println("You do not have access to this section. ");

        // add new sub-service
//        System.out.println("Existing Services: (id. service name)");
//        Map<Long, String> serviceById = loadAllExistingServices();
//        serviceById.forEach((id, service) ->
//                System.out.println(id + ". " + service));
//        Long serviceId = InputUtility.getUserInputForServiceId(serviceById, "Enter the id of service to which you want to add sub-service: ");
//        String subServiceName = InputUtility.getValidName("Sub-Service Name: ");
//        double basePrice = InputUtility.getPositiveDoubleFromUser("Base Price: ");
//        String description = InputUtility.getUserInput("Description: ");
//        ApplicationContext.getSubServiceService().save(subServiceName, basePrice, description, serviceId);

        // اضافه و حذف متخصصان از زیر خدمت های موجود در سیستم (مدیر)
        // add
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
        // remove
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

        // order
        String email = "customer@gmail.com";
        SecurityContext.username = email;
        Map<Long, String> serviceById = getExistingServices();
        Long serviceId = InputUtility.getUserInputForServiceId(serviceById, "Enter the ID of the expert to remove from a sub-service: ");
        List<SubService> subServicesByServiceId = ApplicationContext.getSubServiceService().findSubServicesByServiceId(serviceId);
        for (SubService subService : subServicesByServiceId)
            System.out.println(subService);
        Long subServiceId = InputUtility.getValidSubServiceId(subServicesByServiceId);
        String address = InputUtility.getUserInput("Address: ");
        String workDescription = InputUtility.getUserInput("Work Description: ");
        double price = InputUtility.getPositiveDoubleFromUser("Proposed Price: ");
        LocalDateTime completionDateTime = InputUtility.getValidFutureOrPresentDateTime();
        OrderStatusEnum status = OrderStatusEnum.WAITING_FOR_OFFERS;
        ApplicationContext.getOrderService().save(email, subServiceId, price, workDescription, address, completionDateTime, status);


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
