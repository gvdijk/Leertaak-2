package weatherSQL;

import weatherXML.WeatherMeasurement;

import java.sql.*;
import java.util.ArrayList;

public class WeatherSQLConnection implements Runnable {
    private static final String url = "jdbc:mysql://localhost:3306/unwdmi?useSSL=false";
    private static final String user = "weather";
    private static final String pass = "Kawaii$88Pannekoek";

    private Connection con;
    private ArrayList<String> queries = new ArrayList<>();
    private ArrayList<WeatherMeasurement> sets = new ArrayList<>();
    private int count = 0;
    private boolean batching;

    public WeatherSQLConnection() {}

    public void run() {
        try {
            while (true) {
                while (sets.size() > 0) {
                    con = DriverManager.getConnection(url, user, pass);
                    PreparedStatement ps = con.prepareStatement("INSERT INTO `Measurement` (`Station_stn`, `date`, `time`, `temp`, `dew`, `stp`, `slp`, `vis`, `wdsp`, `prcp`, `sndp`, `cldc`, `wnddir`, `frz`, `rain`, `snow`, `hail`, `tndr`, `torn`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    batching = true;
                    while (batching == true) {
                        WeatherMeasurement wm = sets.remove(0);
                        ps.setInt(1, wm.getStation());
                        ps.setString(2, wm.getDate());
                        ps.setString(3, wm.getTime());
                        ps.setFloat(4, wm.getTemperature());
                        ps.setFloat(5, wm.getDew());
                        ps.setFloat(6, wm.getAirStation());
                        ps.setFloat(7, wm.getAirSea());
                        ps.setFloat(8, wm.getVisibility());
                        ps.setFloat(9, wm.getWindSpeed());
                        ps.setFloat(10, wm.getRain());
                        ps.setFloat(11, wm.getSnow());
                        ps.setFloat(12, wm.getClouds());
                        ps.setInt(13, wm.getWindDegree());
                        ps.setInt(14, wm.isFrozen());
                        ps.setInt(15, wm.isRained());
                        ps.setInt(16, wm.isSnowed());
                        ps.setInt(17, wm.isHailed());
                        ps.setInt(18, wm.isRained());
                        ps.setInt(19, wm.isTornado());
                        ps.addBatch();
                        count++;
                        System.out.println(count);
                        if (count > 999) {
                            count = 0;
                            System.out.println(sets.size());
                            ps.executeBatch();
                            System.out.println("Done");
                        }
                        if(sets.size() == 0) {
                            batching = false;
                            ps.executeBatch();
                        }
                    }
                    con.close();
                }
            }
        }
        catch (Exception e) { System.out.println(e); }
    }

    public synchronized void addQuery(String q) {
        queries.add(q);
        notify();
    }

    public synchronized void addData(ArrayList<WeatherMeasurement> wm) {
        sets.addAll(wm);
        notify();
    }
}
