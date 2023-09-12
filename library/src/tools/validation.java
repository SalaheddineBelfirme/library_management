package tools;

import java.util.Scanner;

public class validation {
    public static int getValidNumber() {
        int number = 0;
        boolean isValid = false;

        while (!isValid) {
            Scanner scanner=new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                number = Integer.parseInt(input);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid input : ");
            }
        }

        return number;
    }
}
