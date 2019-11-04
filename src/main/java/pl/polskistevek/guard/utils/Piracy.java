package pl.polskistevek.guard.utils;

import pl.polskistevek.guard.bukkit.BukkitMain;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Piracy {
    public static boolean b = false;

    public static void register(){
        Logger.log("Waiting for API response...");
        try {
            final Scanner s = new Scanner(new URL("http://infinity-cloud.cba.pl/api/guard-license.txt").openStream());
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (s.next().contains(getServerId() + ":true")) {
                        Logger.log("Premium features unlocked. Thanks for your support :)");
                        b = true;
                    }
                    if (s.next().contains(getServerId() + ":false")) {
                        Logger.log("Your plugin / premium features has been disabled. Please contact plugin developer. Plugin will now shut down.");
                        BukkitMain.plugin.getPluginLoader().disablePlugin(BukkitMain.plugin);
                        b = false;
                    }
                }
            }
        } catch (IOException ignored) {
            Logger.log("Premium features is not active. (This message is only an debug info, premium coming soon in 2.0)");
            b = false;
        }
    }

    public static String getServerId() {
        return bytesToHex(generateHWID());
    }

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static byte[] generateHWID() {
        try {
            MessageDigest hash = MessageDigest.getInstance("MD5");
            StringBuilder s = new StringBuilder();
            s.append(System.getProperty("os.name"));
            s.append(System.getProperty("os.arch"));
            s.append(System.getProperty("os.version"));
            s.append(Runtime.getRuntime().availableProcessors());
            s.append(System.getenv("PROCESSOR_IDENTIFIER"));
            s.append(System.getenv("PROCESSOR_ARCHITECTURE"));
            s.append(System.getenv("PROCESSOR_ARCHITEW6432"));
            s.append(System.getenv("NUMBER_OF_PROCESSORS"));
            return hash.digest(s.toString().getBytes());
        }
        catch (NoSuchAlgorithmException e) {
            throw new Error("Algorithm wasn't found.", e);
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0xF];
        }
        return new String(hexChars);
    }
}
