import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String dbURL = "jdbc:mysql://localhost:3306/java34";
        String username = "root";
        String password = "1234";
        Scanner scanner = new Scanner(System.in);
        char again = 'y';

        try (Connection connection = DriverManager.getConnection(dbURL, username, password)) {

            System.out.println("Welcome to our geography quiz!" + '\n' + "What would you like to do?");

            while (again == 'y') {
                System.out.println("Please type 'register' / 'log in' ");

                String action = scanner.nextLine().toLowerCase().trim();

                if (action.equals("register")) {
                    System.out.println("Enter a new username");
                    String newUserName = scanner.nextLine();

                    if (database.logIn(connection, newUserName)) {
                        System.out.println("This username is taken");
                        continue;
                    }

                    System.out.println("Enter your full name");
                    String newFullName = scanner.nextLine();

                    database.registerUser(connection, newUserName, newFullName);

                } else if (action.equals("log in")) {
                    System.out.println("Please, input your username");

                    String existingUserName = scanner.nextLine();
                    if (database.logIn(connection, existingUserName)) {
                        System.out.println("You have logged in");
                        QuizQuestionGenerator.startQuiz();
                    } else {
                        System.out.println("Not an existing user");
                    }

                } else {
                    System.out.println("Invalid input");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}