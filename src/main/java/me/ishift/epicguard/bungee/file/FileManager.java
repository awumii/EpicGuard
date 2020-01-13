package me.ishift.epicguard.bungee.file;

import java.util.HashMap;
import java.util.Map;

public class FileManager {
    public static Map<String, CustomFile> fileMap = new HashMap<>();

    public static void createFile(String file) {
        fileMap.put(file, new CustomFile(file));
    }

    public static CustomFile getFile(String file) {
        return fileMap.get(file);
    }
}
