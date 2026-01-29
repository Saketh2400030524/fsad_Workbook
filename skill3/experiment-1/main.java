public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Git Version Control Lab!");
        System.out.println("=====================================");

        displayProjectInfo();
        performCalculation(10, 5);
    }

    public static void displayProjectInfo() {
        System.out.println("\nProject: FSAD Workbook");
        System.out.println("Purpose: Learning Git Version Control");
        System.out.println("Author: Saketh");
        System.out.println("Date: " + java.time.LocalDate.now());
    }

    public static void performCalculation(int a, int b) {
        System.out.println("\n--- Calculation Demo ---");
        System.out.println("Addition: " + (a + b));
        System.out.println("Subtraction: " + (a - b));
        System.out.println("Multiplication: " + (a * b));

        if (b != 0) {
            double quotient = (double) a / b;
            System.out.println("Division: " + quotient);
            System.out.println("Modulus: " + (a % b));
        } else {
            System.out.println("Division: undefined (division by zero)");
            System.out.println("Modulus: undefined (division by zero)");
        }
    }
}