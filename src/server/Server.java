// * Copyright (c) 2024, noahdossan <noahpds@proton.me>
// *
// * SPDX-License-Identifier: GPL-2.0

package server;

import printer.Printer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;
    private static final int PORT = 6942; // Use the same port number as the client

    public static void start() {
        Printer.logInfo("[Server] Starting server...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Printer.logInfo("[Server] New client connection established.");
                    // Create a new thread for each client connection
                    new Thread(new ClientHandler(clientSocket)).start();
                } catch (IOException e) {
                    Printer.logErr("[Server] Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            Printer.logErr("[Server] Error starting server: " + e.getMessage());
        }
    }
}
