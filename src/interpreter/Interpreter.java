// * Copyright (c) 2024, noahdossan <noahpds@proton.me>
// *
// * SPDX-License-Identifier: GPL-2.0

package interpreter;

import printer.Printer;
import client.Client;
import server.Server;

import java.util.Scanner;
/*
* The IRC4N interpreter.Interpreter
*  */

public class Interpreter {
    private static String input;

    // Asks the user client or server
    public static int selectMode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is IRC4N going to do today? (cli/serv)");
        System.out.print("> ");
        input = scanner.nextLine();

        switch (input) {
            case "cli":
                Printer.logInfo("Starting the client");
                Client.start();
                break;
            case "serv":
                Printer.logInfo("Starting the server");
                Server.start();
                break;
            default:
                Printer.logErr("Invalid input. Please try again.");
                selectMode();
        }

        scanner.close();
        return 0;
    }

    // Returns the users input to whatever called it
    public static String[] returnInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        input = scanner.nextLine();
        String[] parts = input.split("\\\\");
        return parts;
    }
}
