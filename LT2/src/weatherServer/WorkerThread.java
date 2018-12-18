package weatherServer;

import weatherXML.WeatherXMLParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class WorkerThread implements Runnable {

    private Socket con;
    private ArrayList<ArrayList> data;
    private WeatherXMLParser parser;

    public WorkerThread(Socket con) {
        this.con = con;
        this.data = new ArrayList<>();
    }

    public void run() {
        try {
            WeatherServer.sem.attempt();
            DataInputStream streamIn = new DataInputStream(con.getInputStream());
            // FileOutputStream out = new FileOutputStream("E:\\outputstream.xml");
            ByteArrayOutputStream streamOut = new ByteArrayOutputStream();


            byte[] bytes = new byte[4096];
            int count;
            while ((count = streamIn.read(bytes)) > 0) {
                System.out.println("Count: " + count);
                //streamOut.write(bytes, 0, count);
                //String s = streamOut.toString(StandardCharsets.UTF_8);
                parser = new WeatherXMLParser(new ByteArrayInputStream(bytes));
                data.add(parser.getData());
                //data.add(s);
            }

            System.out.println("closing connection");
            printData();
            this.con.close();
            WeatherServer.sem.close();
        }
        catch (IOException ioe) { System.out.println("ioe: " + ioe); }
        catch (InterruptedException ie) { System.out.println("ie: " + ie); }
    }

    public void printData() {
        try { System.out.println(data); }
        catch (IndexOutOfBoundsException ioobe) { }
    }
}
