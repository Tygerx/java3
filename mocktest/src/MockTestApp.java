import java.util.Scanner;

public class MockTestApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        
        System.out.println("Welcome, " + name + "! Let's start the test.");
        
        Test test = new Test();
        test.start();
        
        scanner.close();
    }
}


