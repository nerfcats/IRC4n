import interpreter.Interpreter;
import printer.Printer;

public class Main {
    // Release version (e.g. "indev-0.3.2_02")
    // Do NOT increment the major version number until IRC4n is ready for release.
    // Dev stage: 0.major.minor_bugfix
    public static String version = "indev-0.0.1_00";
    public static void main(String[] args) {
        System.out.println("IRC4n version " + version);
        // Ask the interpreter to ask the user to determine if irc4n should be client or server
        Interpreter.selectMode();

        Printer.logInfo("End of Main reached, shutting down.");
        // TO-DO: put clean-up here when it's actually needed
    }
}