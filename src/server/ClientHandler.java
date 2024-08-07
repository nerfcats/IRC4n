// * Copyright (c) 2024, noahdossan <noahpds@proton.me>
// *
// * SPDX-License-Identifier: GPL-2.0

package server;

import printer.Printer;

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
                        out.println("pong"); // Send response to client
                        Printer.logInfo("[Server] Responded with pong to client.");
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
        }
    }
}
