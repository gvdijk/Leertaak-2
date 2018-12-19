package weatherServer;

import weatherSQL.WeatherSQLConnection;

import java.net.ServerSocket;
import java.net.Socket;

public class WeatherServer {
    public static final int PORT = 54871;
    private static final int maxConnections = 800;
    public static Semaphore sem = new Semaphore(maxConnections);

    public static void main(String[] args) {
        Socket con;
        boolean yes = true;
        System.out.println();
        WeatherSQLConnection wsqlcon = new WeatherSQLConnection();
        try {
            ServerSocket server = new ServerSocket(PORT);
            while (true) {
                con = server.accept();
                Thread worker = new Thread(new WorkerThread(con));
                worker.start();
            }
        }
        catch (java.io.IOException ioe) {}
    }

}
