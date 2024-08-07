package server;

import printer.Printer;
import main.Main;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            Printer.logInfo("[Server] Client connected: " + clientSocket.getRemoteSocketAddress());

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                switch (inputLine.trim()) {
                    case "ping":
                        out.println(Main.version + "\\Another IRC4n server."); // Send response to client
                        Printer.logInfo("[Server] Responded with pong data to client.");
                        break;
                    case "exit":
                        Printer.logInfo("[Server] Closing connection with client.");
                        return; // Exit the loop to close the connection
                    default:
                        out.println("Invalid command!");
                        Printer.logErr("[Server] Invalid command from client!");
                }
            }
        } catch (IOException e) {
            Printer.logErr("[Server] Error handling client connection: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                Printer.logInfo("[Server] Client connection closed.");
            } catch (IOException e) {
                Printer.logErr("[Server] Error closing client socket: " + e.getMessage());
            }
        }
    }
}