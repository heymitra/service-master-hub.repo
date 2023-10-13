package util;

import entity.SubService;
import repository.dto.ExpertDTO;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputUtility {
    private static Scanner scanner = new Scanner(System.in);

    public static String getValidName(String prompt) {
        String namePattern = "^[^0-9]+$";

        while (true) {
            System.out.print(prompt);
            String name = scanner.nextLine().trim();

            if (name.matches(namePattern)) {
                return name;
            } else {
                System.out.println("Invalid input. Please enter a valid name without numbers.");
            }
        }
    }

    public static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int getIntWithinRange(String prompt, int min, int max) {
        int userInput;

        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                System.out.print(prompt);
                scanner.next();  // Discard invalid input
            }
            userInput = scanner.nextInt();

            if (userInput < min || userInput > max) {
                System.out.println("Please enter a number within the range [" + min + ", " + max + "].");
            }
        } while (userInput < min || userInput > max);

        return userInput;
    }

    public static double getPositiveDoubleFromUser(String prompt) {
        double amount;

        while (true) {
            try {
                System.out.print(prompt);
                amount = scanner.nextInt();
                scanner.nextLine();

                if (amount > 0) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();  // Clear the invalid input
            }
        }

        return amount;
    }

    public static Path getPhotoPath(String prompt) {
        System.out.print(prompt);
        String photoPath = scanner.nextLine().trim();

        // Validate the file path and check if it's a valid image
        if (!isValidPath(photoPath) || !isImageFile(photoPath)) {
            System.out.println("Invalid or non-image file path. Please enter a valid path to your photo.");
            return null;
        }

        return Paths.get(photoPath);
    }

    // Method to validate the file path
    private static boolean isValidPath(String path) {
        // Check if the path is not empty
        return !path.isEmpty();
    }

    // Method to check if the path points to a valid image file
    private static boolean isImageFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && isImageExtension(path);
    }

    // Method to check if the file has an image extension
    private static boolean isImageExtension(String path) {
        String[] imageExtensions = {".jpg"}; // Add more extensions if needed
        for (String extension : imageExtensions) {
            if (path.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    public static Long getUserInputForServiceId(Map<Long, String> serviceById, String prompt) {
        Long chosenId;

        while (true) {
            try {
                System.out.println(prompt);
                chosenId = scanner.nextLong();
                scanner.nextLine();

                if (serviceById.containsKey(chosenId)) {
                    break;
                } else {
                    System.out.println("Invalid ID. Please choose a valid ID.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
        return chosenId;
    }

    public static Long getValidExpertId(List<ExpertDTO> expertDTOS) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Experts:");
        expertDTOS.forEach(expertDTO -> System.out.println("ID: " + expertDTO.getId() + ", Name: " + expertDTO.getName()));
        Long expertId;
        while (true) {
            System.out.print("Enter the ID of the expert to assign a sub-service: ");
            if (scanner.hasNextLong()) {
                Long enteredId = scanner.nextLong();
                boolean validExpert = expertDTOS.stream().anyMatch(expertDTO -> expertDTO.getId().equals(enteredId));

                if (validExpert) {
                    expertId = enteredId;
                    break;
                } else {
                    System.out.println("Invalid expert ID. Please enter a valid expert ID.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid expert ID.");
                scanner.next();
            }
        }
        return expertId;
    }


    public static Long getValidSubServiceId(List<SubService> subServices) {
        Scanner scanner = new Scanner(System.in);
        Long[] subServiceIdHolder = new Long[1];
        while (true) {
            System.out.print("Enter the ID of the sub-service to assign an expert: ");
            if (scanner.hasNextLong()) {
                subServiceIdHolder[0] = scanner.nextLong();
                boolean validSubService = subServices.stream()
                        .anyMatch(subService -> subService.getId().equals(subServiceIdHolder[0]));
                if (validSubService) {
                    return subServiceIdHolder[0];
                } else {
                    System.out.println("Invalid sub-service ID. Please enter a valid sub-service ID.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid sub-service ID.");
                scanner.next();
            }
        }
    }

    public static LocalDateTime getValidFutureOrPresentDateTime() {
        while (true) {
            System.out.print("Enter the completion date and time (yyyy-MM-dd HH:mm): ");
            String input = scanner.nextLine();

            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                if (dateTime.isAfter(LocalDateTime.now())) {
                    return dateTime;
                } else {
                    System.out.println("Completion date and time cannot be in the past. Please try again.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date and time format. Please enter in the format yyyy-MM-dd HH:mm.");
            }
        }
    }

}
