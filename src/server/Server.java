package server;

import printer.Printer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static boolean running = true;
    private static final int PORT = 6942; // Use the same port number as the client
    private static final int THREAD_POOL_SIZE = 10; // Adjust the pool size as needed

    public static void start() {
        Printer.logInfo("[Server] Starting server...");
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Printer.logInfo("[Server] Server started successfully on port " + PORT);
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Printer.logInfo("[Server] New client connection established.");
                    threadPool.execute(new ClientHandler(clientSocket));
                } catch (IOException e) {
                    Printer.logErr("[Server] Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            Printer.logErr("[Server] Error starting server: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }
}