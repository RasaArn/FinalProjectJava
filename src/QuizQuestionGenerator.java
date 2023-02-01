import java.sql.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class QuizQuestionGenerator {
  //  public static void main(String[] args) {
     //   startQuiz();
   // }
    public static void startQuiz()  {
        String dbURL = "jdbc:mysql://localhost:3306/java34";
        String username = "root";
        String password = "1234";
        Scanner scanner = new Scanner(System.in);


        while (true) {
            try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
                System.out.println("__________________________________________________________________________________________________________________________________________");
                System.out.println("Welcome to a Capital cities quiz! Here you can check your knowledge or learn capital cities of many countries around the world. Lets start!");
                System.out.println("For each correct answer you get 1 point. Your score resets each time you choose another region.");
                System.out.println("Choose what you want to do: ");
                System.out.println("q- take quiz");
                System.out.println("e - quit program");

                try {
                    char quiz = scanner.nextLine().charAt(0);
                    boolean quit = false;

                    if (quiz == 'q') {
                        while (quiz == 'q') {
                            quizMenu();

                            //validating user input in quiz menu. It should not be possible to enter anything else than numbers 1-5.
                            // User chooses quiz type from menu by entering number (variable choice)
                            //using try catch block to catch invalid input and not stop the program because of it.
                            int choice;
                            try {
                                choice = scanner.nextInt();
                                if (choice < 1 || choice > 5) {
                                    throw new InputMismatchException();
                                }
                                System.out.println("You input is not valid. Please enter a valid number between 1 and 5.");
                            } catch (InputMismatchException e) {
                                System.out.println("You input is not valid. Please enter a valid number between 1 and 5.");
                                scanner.nextLine();
                                continue;
                            }


                            int count = 1; //counts number of questions
                            int score = 0; // will count correct answers/ answers are reset to 0 each time user ends quiz, or chooses menu
                            // int choice = scanner.nextInt(); //user chooses quiz type from menu

                            //inner while loops to start
                            while (choice == 1) {
                                System.out.println("Capital cities of Asian countries: ");
                                if (AsiaCapitals(conn, count)) {
                                    score++;
                                }
                                if (!continueOrEndQuiz(score, count)) break;
                                count++;
                            }
                            while (choice == 2) {
                                System.out.println("Capital cities of European countries: ");
                                if (EuropeCapitals(conn, count)) {
                                    score++;
                                }
                                if (!continueOrEndQuiz(score, count)) break;
                                count++;
                            }
                            while (choice == 3) {
                                System.out.println("Capital cities of South American countries: ");
                                if (SouthAmericaCapitals(conn, count)) {
                                    score++;
                                }
                                if (!continueOrEndQuiz(score, count)) break;
                                count++;
                            }
                            while (choice == 4) {
                                System.out.println("Capital cities of African countries: ");
                                if (AfricaCapitals(conn, count)) {
                                    score++;
                                }
                                if (!continueOrEndQuiz(score, count)) break;
                                count++;
                            }
                            while (choice == 5) {
                                System.out.println("Capital cities of North American countries: ");
                                if (NorthAmericaCapitals(conn, count)) {
                                    score++;
                                }
                                if (!continueOrEndQuiz(score, count)) break;
                                count++;
                            }
                            if (!endQuiz()) {
                                quit = true;
                                break;
                            }
                        }
                        if (quit) {
                            break;
                        }
                    } else if (quiz == 'e' || quiz == 'E') {
                        System.out.println("See you next time! ");
                        break;
                    } else {    //validating variable quiz
                        System.out.println("Your input is not valid. Enter q to start quiz or e to quit");
                    }
                } catch (IndexOutOfBoundsException e){
                    System.out.println("Your input is not valid. Enter q to start quiz or e to quit");
                }
            } catch (SQLException | InputMismatchException e) { // //validating variable quiz, adding input mismatch exception to try catch block and scanner to read new input
                e.printStackTrace();
                scanner.nextLine();
            }
        }
    }

    //Returns true if answered correctly
    private static boolean AsiaCapitals(Connection conn, int count) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean answer = false;

        //randomID object is validated according to the Asian countries ID number in SQL database.
        Random rand = new Random();
        int randomID = rand.nextInt(47) + 1; //randomID between 1 and 47

        //creating statement and a resultSet, conn to database
        Statement statement = conn.createStatement();
        ResultSet resultSet;
        // selecting country, capital, and region with  random ID from our database
        resultSet = statement.executeQuery("SELECT Country, Capital, Region FROM countries WHERE Region = 'Asia' AND CountryID  = " + randomID);
        resultSet.next();
        String randomCountry = resultSet.getString("Country"); // selecting country and capital
        String randomCapital = resultSet.getString("Capital");

        //asking quiz question
        System.out.println(count + ". What is the capital of " + randomCountry + "?");
        // Validating  user's answer. If answer is incorrect, user can check what was the correct answer
        String userAnswer = scanner.nextLine();
        if (userAnswer.equalsIgnoreCase(randomCapital)) {
            System.out.println("Correct! You get 1 point");
            answer = true;

            // added while loop to prompt user to enter y or n, and not anything else
        } else {
            System.out.println("Incorrect. You get 0 points. Do you want me to show the correct answer y/n?");
            char knowCorrectAnswer = ' ';
            while (knowCorrectAnswer != 'y' && knowCorrectAnswer != 'n') {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("y")) {
                    knowCorrectAnswer = 'y';
                } else if (input.equalsIgnoreCase("n")) {
                    knowCorrectAnswer = 'n';
                } else {
                    System.out.println("Your input is not valid. Enter y or n.");
                }
            }
            if (knowCorrectAnswer == 'y') {
                System.out.println("The correct answer is " + randomCapital);
            } else if (knowCorrectAnswer == 'n') {
                System.out.println("OK :) We suggest you to check next question. You can do it! :)");
            }

            //Old version without validation:
            /*System.out.println("Incorrect. You get 0 points. Do you want me to show the correct answer y/n?");
            char knowCorrectAnswer = scanner.nextLine().charAt(0);
            if (knowCorrectAnswer == 'y') {
                System.out.println("The correct answer is " + randomCapital);
            } else if (knowCorrectAnswer == 'n') {
                System.out.println("OK :) We suggest you to check next question. You can do it! :)");
            } else {
                System.out.println("Your input is not valid. Enter y or n.");
            }*/
        }
        return answer;
    }

    private static boolean SouthAmericaCapitals(Connection conn, int count) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean answer = false;

        // tracking if ID is already used. But does not work since looping resets values each time while loop runs
        //Unique IDs are not  necessary because quiz is for learning purpose, not testing knowledge.privat
        /*   Set<Integer> usedIDs = new HashSet<>(); // will store the previously generated random IDs.
           int randomID = rand.nextInt(35) + 48; // generates a random number between 48 and 82 (inclusive) and then adds 1 to it, resulting in a random number between 1 and 3 (inclusive).
           while (usedIDs.contains(randomID)) {
               randomID = rand.nextInt(35) + 48;
           }usedIDs.add(randomID); // Once a unique random ID is generated, it is added to the usedIDs set using the add() method.
.*/
        Random rand = new Random();
        int randomID = rand.nextInt(12) + 48;

        //creating statement and a resultSet
        Statement statement = conn.createStatement();
        ResultSet resultSet;
        // selecting country, capital, and region with  random ID
        resultSet = statement.executeQuery("SELECT Country, Capital, Region FROM countries WHERE Region = 'South America' AND CountryID  = " + randomID);
        resultSet.next();
        String randomCountry = resultSet.getString("Country");
        String randomCapital = resultSet.getString("Capital");

        System.out.println(count + ". What is the capital of " + randomCountry + "?");
        // Validating  user's answer. If answer is incorrect, user can check what was the correct answer
        String userAnswer = scanner.nextLine();
        if (userAnswer.equalsIgnoreCase(randomCapital)) {
            System.out.println("Correct! You get 1 point");
            answer = true;
        } else {    // using while loop to prompt user to enter y or n, and not anything else
            System.out.println("Incorrect. You get 0 points. Do you want me to show the correct answer y/n?");
            char knowCorrectAnswer = ' ';
            while (knowCorrectAnswer != 'y' && knowCorrectAnswer != 'n') {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("y")) {
                    knowCorrectAnswer = 'y';
                } else if (input.equalsIgnoreCase("n")) {
                    knowCorrectAnswer = 'n';
                } else {
                    System.out.println("Your input is not valid. Enter y or n.");
                }
            }
            if (knowCorrectAnswer == 'y') {
                System.out.println("The correct answer is " + randomCapital);
            } else if (knowCorrectAnswer == 'n') {
                System.out.println("OK :) We suggest you to check next question. You can do it! :)");
            }
        }
        return answer;
    }

    private static boolean AfricaCapitals(Connection conn, int count) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean answer = false;
        // tracking if ID is already used
        /*   Set<Integer> usedIDs = new HashSet<>(); // will store the previously generated random IDs.
           int randomID = rand.nextInt(52) + 83; // generate a random integer between 83 and 135 (52 + 83)
           while (usedIDs.contains(randomID)) {
               randomID = rand.nextInt(52) + 83;
           }usedIDs.add(randomID); // Once a unique random ID is generated, it is added to the usedIDs set using the add() method.
.*/
        Random rand = new Random();
        int randomID = rand.nextInt(52) + 84;

        //creating statement and a resultSet
        Statement statement = conn.createStatement();
        ResultSet resultSet;
        // selecting country, capital, and region with  random ID
        resultSet = statement.executeQuery("SELECT Country, Capital, Region FROM countries WHERE Region = 'Africa' AND CountryID  = " + randomID);
        resultSet.next();
        String randomCountry = resultSet.getString("Country");
        String randomCapital = resultSet.getString("Capital");

        System.out.println(count + ". What is the capital of " + randomCountry + "?");

        // Validating  user's answer. If answer is incorrect, user can check what was the correct answer
        String userAnswer = scanner.nextLine();
        if (userAnswer.equalsIgnoreCase(randomCapital)) {
            System.out.println("Correct! You get 1 point");
            answer = true;
            // using while loop to prompt user to enter y or n, and not anything else
        } else {
            System.out.println("Incorrect. You get 0 points. Do you want me to show the correct answer y/n?");
            char knowCorrectAnswer = ' ';
            while (knowCorrectAnswer != 'y' && knowCorrectAnswer != 'n') {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("y")) {
                    knowCorrectAnswer = 'y';
                } else if (input.equalsIgnoreCase("n")) {
                    knowCorrectAnswer = 'n';
                } else {
                    System.out.println("Your input is not valid. Enter y or n.");
                }
            }
            if (knowCorrectAnswer == 'y') {
                System.out.println("The correct answer is " + randomCapital);
            } else if (knowCorrectAnswer == 'n') {
                System.out.println("OK :) We suggest you to check next question. You can do it! :)");
            }
        }
        return answer;
    }

    private static boolean EuropeCapitals(Connection conn, int count) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean answer = false;

        Random rand = new Random();
        int randomID = rand.nextInt(42) + 137;

        //creating statement and a resultSet
        Statement statement = conn.createStatement();
        ResultSet resultSet;
        // selecting country, capital, and region with  random ID
        resultSet = statement.executeQuery("SELECT Country, Capital, Region FROM countries WHERE Region = 'Europe' AND CountryID  = " + randomID);
        resultSet.next();
        String randomCountry = resultSet.getString("Country");
        String randomCapital = resultSet.getString("Capital");

        System.out.println(count + ". What is the capital of " + randomCountry + "?");

        // Validating  user's answer. If answer is incorrect, user can check what was the correct answer
        String userAnswer = scanner.nextLine();
        if (userAnswer.equalsIgnoreCase(randomCapital)) {
            System.out.println("Correct! You get 1 point");
            answer = true;
            // using while loop to prompt user to enter y or n, and not anything else
        } else {
            System.out.println("Incorrect. You get 0 points. Do you want me to show the correct answer y/n?");
            char knowCorrectAnswer = ' ';
            while (knowCorrectAnswer != 'y' && knowCorrectAnswer != 'n') {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("y")) {
                    knowCorrectAnswer = 'y';
                } else if (input.equalsIgnoreCase("n")) {
                    knowCorrectAnswer = 'n';
                } else {
                    System.out.println("Your input is not valid. Enter y or n.");
                }
            }
            if (knowCorrectAnswer == 'y') {
                System.out.println("The correct answer is " + randomCapital);
            } else if (knowCorrectAnswer == 'n') {
                System.out.println("OK :) We suggest you to check next question. You can do it! :)");
            }
        }
        return answer;
    }

    private static boolean NorthAmericaCapitals(Connection conn, int count) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean answer = false;

        Random rand = new Random();
        int randomID = rand.nextInt(22) + 61;

        //creating statement and a resultSet
        Statement statement = conn.createStatement();
        ResultSet resultSet;
        // selecting country, capital, and region with  random ID
        resultSet = statement.executeQuery("SELECT Country, Capital, Region FROM countries WHERE Region = 'North America' AND CountryID  = " + randomID);
        resultSet.next();
        String randomCountry = resultSet.getString("Country");
        String randomCapital = resultSet.getString("Capital");

        System.out.println(count + ". What is the capital of " + randomCountry + "?");
        // Validating  user's answer. If answer is incorrect, user can check what was the correct answer
        String userAnswer = scanner.nextLine();
        if (userAnswer.equalsIgnoreCase(randomCapital)) {
            System.out.println("Correct! You get 1 point");
            answer = true;
            // using while loop to prompt user to enter y or n, and not anything else
        } else {
            System.out.println("Incorrect. You get 0 points. Do you want me to show the correct answer y/n?");
            char knowCorrectAnswer = ' ';
            while (knowCorrectAnswer != 'y' && knowCorrectAnswer != 'n') {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("y")) {
                    knowCorrectAnswer = 'y';
                } else if (input.equalsIgnoreCase("n")) {
                    knowCorrectAnswer = 'n';
                } else {
                    System.out.println("Your input is not valid. Enter y or n.");
                }
            }
            if (knowCorrectAnswer == 'y') {
                System.out.println("The correct answer is " + randomCapital);
            } else if (knowCorrectAnswer == 'n') {
                System.out.println("OK :) We suggest you to check next question. You can do it! :)");
            }
        }
        return answer;

    }

    private static boolean continueOrEndQuiz(int score, int count)
    {
        while(true){ // added while loop to prompt user to enter a valid input c or s and prevent program from stopping if input will be invalid
            System.out.println("Enter c to continue with the same region");
            System.out.println("Enter s to check your score/ go back to menu or end quiz");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.matches("[CcSs]")) { // added regular expression validate user input
                char nextStep = input.charAt(0);
                if (nextStep == 's' || nextStep == 'S') {
                    System.out.println("You have answered: " + score + " questions out of " + count);
                    feedback(count, score);
                    System.out.println("To end quiz: press e + enter; To play again: any key + enter ");
                    return false;
                }
                return true;
            } else {
                System.out.println("Invalid input. Please enter c or s to continue.");
            }
        }
    }
    private static void quizMenu() {
        System.out.println("Choose region: ");
        System.out.println("1 - capitals of Asia");
        System.out.println("2 - capitals of Europe");
        System.out.println("3 - capitals of South America");
        System.out.println("4 - capitals of Africa");
        System.out.println("5 - capitals of North America");
    }

    private static boolean endQuiz() {
        //System.out.println("Press e to end quiz, or any case to go back to menu");
        try{
        Scanner scanner = new Scanner(System.in);
        char nextStep= scanner.nextLine().charAt(0);
        if (nextStep == 'e') {
            System.out.println("Quiz ended. See you next time! ");
            return false;
        }
    } catch (IndexOutOfBoundsException e) {
        }
        return true;
    }

    private static void feedback(int count, int score) {
        double percentage = (score / (double) count) * 100;

        if (percentage < 20) {
            System.out.println(" :( You can do it better! Don't give up!");
        } else if (percentage >= 20 && percentage < 60) {
            System.out.println("Not bad! But still you can do it better! Keep learning :)");
        } else if (percentage >= 60 && percentage < 80) {
            System.out.println("Very good! Keep going :)");
        } else if (percentage >= 80) {
            System.out.println("Nice result:) 80% or more questions were answered correctly! ");
        }
    }

}







