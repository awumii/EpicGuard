package me.ishift.epicguard.universal.util;

import java.net.URL;
import java.util.Scanner;

public class LogoPrinter {
    public static void print() {
        try {
            final Scanner scanner = new Scanner(new URL("https://pastebin.com/raw/YwUWQ8WC").openStream());
            while (scanner.hasNextLine()) {
                Logger.info(scanner.nextLine());
            }
            scanner.close();
            Logger.info("Created by iShift.");
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}
