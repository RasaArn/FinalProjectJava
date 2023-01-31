import java.sql.*;

public class database {private static final String dbURL = "jdbc:mysql://localhost:3306/java34";
    private static final String user ="root" ;
    private static final String password ="1234";


    public static void registerUser (Connection connection, String UserName, String FullName) throws SQLException {

        String sql = "INSERT INTO gameUsers (UserName, FullName) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, UserName);
        statement.setString(2, FullName);

        int rowInserted = statement.executeUpdate();

        if (rowInserted > 0) {
            System.out.println("user registered successfully@");
        } else {
            System.out.println("sorry, something went wrong...");
        }
    }

    public static boolean logIn(Connection connection, String existingUserName) throws SQLException {

        String sql;
        try (Connection connection1 = DriverManager.getConnection(dbURL, user, password)) {
            sql = "SELECT UserName FROM gameUsers WHERE UserName = '"+ existingUserName+ "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }






}
