package main;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, String> savedFiles = new HashMap<>();

    public static boolean saveFile(String name, String content) {
        if (savedFiles.containsKey(name)) {
            return false; // not unique
        }
        savedFiles.put(name, content);
        return true;
    }

    public static boolean isAlphanumeric(String name) {
        return name.matches("[a-zA-Z0-9]+");
    }

    public static boolean exists(String name) {
        return savedFiles.containsKey(name);
    }

    public static Map<String, String> getAllFiles() {
        return savedFiles;
    }

    public static String getContent(String name) {
        return savedFiles.get(name);
    }
    
    public static void updateFile(String name, String content) {
        savedFiles.put(name, content); // overwrite directly
    }
}
