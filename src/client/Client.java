package client;

import interpreter.Interpreter;
import printer.Printer;

import java.io.*;
import java.net.Socket;

public class Client {
    private static boolean running = true;
    private static final String SERVER_ADDRESS = "localhost"; // Replace with server's IP if needed
    private static final int SERVER_PORT = 6942; // Must match the server's port

    public static void start() {
        Printer.logInfo("[Client] Starting client...");
        while (running) {
            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                while (running) {
                    String[] input = Interpreter.returnInput();
                    switch (input[0]) {
                        case "exit":
                            Printer.logInfo("[Client] Exiting...");
                            out.println("exit");
                            running = false;
                            break;
                        case "ping":
                            Printer.logInfo("[Client] Sending ping...");
                            out.println("ping");
                            String response = in.readLine(); // Read response from server
                            if (response != null) {
                                Printer.logInfo("[Client] Received from server: " + response);
                            } else {
                                Printer.logErr("[Client] Server closed the connection.");
                                running = false; // Stop trying to communicate
                            }
                            break;
                        default:
                            Printer.logErr("[Client] Invalid command!");
                    }
                }
            } catch (IOException e) {
                Printer.logErr("[Client] Error connecting to server: " + e.getMessage());
                running = false; // Stop trying to connect if there's an issue
            }
        }
    }
}
