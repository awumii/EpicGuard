package me.ishift.epicguard.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

public class FileUtil {
    /**
     * Erases content of a text file, without deleting it.
     *
     * @param file File which should be erased.
     */
    public static void eraseFile(File file) {
        try {
            final PrintWriter pw = new PrintWriter(file);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appending a message to the text file.
     *
     * @param file Target file.
     * @param message Message to be logged.
     */
    public static void writeToFile(File file, String message) {
        try {
            if (!file.exists() || file.createNewFile()) {
                return;
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will copy resource from jar file to specified directory.
     * @param directory Directory where file will be copied. If not exists, will be automatically created.
     * @param fileName Name of the file inside the jar, (and also the copied file name).
     */
    public static void saveResource(File directory, String fileName) {
        if (!directory.exists()) {
            directory.mkdir();
        }
        final File file = new File(directory, fileName);
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream(fileName)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get a resource from within this plugins jar or container. Care must be
     * taken to close the returned stream (method taken from BungeeCord).
     *
     * @param name the full path name of this resource
     * @return the stream for getting this resource, or null if it does not
     * exist
     */
    public static InputStream getResourceAsStream(String name) {
        return FileUtil.class.getClassLoader().getResourceAsStream(name);
    }
}
