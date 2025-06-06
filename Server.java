import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private Socket socket;

    public static void main(String[] args) throws Exception {
        new Server();
    }

    public Server() throws Exception {
        int port = 50000;
        System.out.println("Starting server up.");
        ServerSocket server = new ServerSocket(port);
        System.out.println("Waiting for someone to connect.");
        socket = server.accept();
        System.out.println("Someone connected to the server!");
        Thread t1 = new Thread(new ListenThread());
        Thread t2 = new Thread(new SendThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private class ListenThread implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(!socket.isClosed()) {
                    System.out.println("Waiting for a message.");
                    String message = br.readLine(); //blocks execution
                    System.out.println(message);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class SendThread implements Runnable {
        @Override
        public void run() {
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                Scanner scan = new Scanner(System.in);
                while (!socket.isClosed()) {
                    System.out.println("Type in a message to send: ");
                    String message = scan.nextLine(); //blocks execution
                    writer.println(message);
                }
                scan.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
