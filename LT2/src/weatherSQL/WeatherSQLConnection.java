package weatherSQL;

import java.sql.*;
import java.util.ArrayList;

public class WeatherSQLConnection implements Runnable {
    private static final String url = "jdbc:mysql://localhost:3306/unwdmi?useSSL=false";
    private static final String user = "";
    private static final String pass = "";

    private Connection con;
    private ArrayList<String> queries = new ArrayList<>();

    public WeatherSQLConnection() {}

    public synchronized void run() {
        try {
            while (true) {
                if (queries.size() > 0) {
                    con = DriverManager.getConnection(url, user, pass);
                    Statement s = con.createStatement();
                    while (queries.size() > 0) {
                        s.executeUpdate(queries.remove(0));
                        System.out.println(queries.size());
                    }
                    //PreparedStatement ps = con.prepareStatement("INSERT INTO `Measurement` (`Station_stn`, `date`, `time`, `temp`, `dew`, `stp`, `slp`, `vis`, `wdsp`, `prcp`, `sndp`, `cldc`, `wnddir`, `frz`, `rain`, `snow`, `hail`, `tndr`, `torn`) VALUES (?)");
                    //while (queries.size() > 0) {
                    //    ps.setString(1, queries.remove(0));
                    //    ps.executeUpdate();
                    //    System.out.println(queries.size());
                    //}
                    con.close();
                }
                wait();
            }
        }
        catch (Exception e) { System.out.println(e); }
    }

    public synchronized void addQuery(String q) {
        queries.add(q);
        notify();
    }
}
