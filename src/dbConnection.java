import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class dbConnection {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java34", "root", "1234");


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM countries");

            while (rs.next()){
                System.out.printf("CountryID %d Country:  %s Capital: %s Region: %s  \n",
                        rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }
            con.close();


        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
