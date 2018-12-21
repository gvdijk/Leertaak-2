package weatherServer;

import weatherSQL.WeatherSQLParser;
import weatherXML.WeatherXMLParser;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class WorkerThread implements Runnable {

    private Socket con;
    private ArrayList<ArrayList> data;

    public WorkerThread(Socket con) {
        this.con = con;
        this.data = new ArrayList<>();
    }

    public void run() {
        try {
            WeatherServer.sem.attempt();
            DataInputStream streamIn = new DataInputStream(con.getInputStream());

            byte[] bytes = new byte[4096];
            while ((streamIn.read(bytes)) > 0) {
                WeatherXMLParser parser = new WeatherXMLParser(new ByteArrayInputStream(bytes));
                data.add(parser.getData());
                if (data.size() >= 15) {
                    WeatherSQLParser sqlParser = new WeatherSQLParser();
                    for (int x=0; x<10; x++) {
                        //sqlParser.parseChuck(data.remove(0));
                        WeatherServer.wsqlcon.addData(data.remove(0));
                    }
                    //WeatherServer.wsqlcon.addQuery(sqlParser.getQuery());
                }
            }

            this.con.close();
            WeatherServer.sem.close();
        }
        catch (IOException ioe) { System.out.println("ioe: " + ioe); }
        catch (InterruptedException ie) { System.out.println("ie: " + ie); }
    }
}
