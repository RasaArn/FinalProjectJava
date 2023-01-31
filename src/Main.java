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


            System.out.println("Welcome to our geography quiz!"+ '\n' +"What would you like to do?");

            while (again == 'y') {
                System.out.println("Please type 'register' / 'log in' ");

                String action = scanner.nextLine().toLowerCase().trim();


                String existingUserName;
                if (action.equals("register")) {
                    System.out.println("enter a new username");
                    String newUserName = scanner.nextLine();


                    if (database.logIn(connection, newUserName)) {
                        System.out.println("this username is taken");
                        break;
                    }

                    System.out.println("enter your full name");
                    String newFullName = scanner.nextLine();

                    database.registerUser(connection, newUserName, newFullName);

                } else if (action.equals("log in")) {
                    System.out.println("please, input your username");

                    existingUserName = scanner.nextLine();

                    database.logIn(connection, existingUserName);
                    QuizQuestionGenerator.startQuiz();

                    existingUserName = scanner.nextLine();
                    if(database.logIn(connection, existingUserName)){
                        System.out.println("You have logged in");
                    }else {
                        System.out.println("Ņot existing user");
                    }



                } else {
                    System.out.println("invalid input");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
