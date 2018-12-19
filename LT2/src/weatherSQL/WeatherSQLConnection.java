package weatherSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class WeatherSQLConnection {
    private Connection con;

    public WeatherSQLConnection() {
        String url = "jdbc:mysql://localhost:3306/unwdmi?useSSL=false";
        String username = "";
        String password = "";

        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        }
        catch (SQLException sqle) { System.out.println(sqle); }
    }
}
