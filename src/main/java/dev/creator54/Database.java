package dev.creator54;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class Database {
    private static final String DB_FILE_PATH = System.getProperty("user.dir") + "/db.json";
    private static File db;

    public Database() throws IOException {
        db = new File(DB_FILE_PATH);
        if (!db.exists()) {
            createEmptyJsonDb();
        }
    }

    private void createEmptyJsonDb() throws IOException {
        try (FileWriter fileWriter = new FileWriter(DB_FILE_PATH)) {
            JSONObject emptyJson = new JSONObject();
            fileWriter.write(emptyJson.toString(2));
        }
    }

    public void add(String key, String value) {
        try {
            JSONObject jsonDb = getJsonDb();

            // Check if the value is already present in the database
            if (containsValue(jsonDb, value)) {
                System.out.println(getKey (value) + " -> " + value);
                return;
            }

            jsonDb.put(key, value);
            saveJsonDb(jsonDb);
            System.out.println(key + " -> " + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getKey(String value) {
        try {
            JSONObject jsonDb = getJsonDb();

            // Iterate through the keys and check for the matching value
            Iterator<String> keys = jsonDb.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String storedValue = jsonDb.getString(key);
                if (storedValue.equals(value)) {
                    return key; // Found the key associated with the value
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Value not found in the database
    }


    private boolean containsValue(JSONObject jsonDb, String value) {
        Iterator<String> keys = jsonDb.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String storedValue = jsonDb.getString(key);
            if (storedValue.equals(value)) {
                return true; // Value is already present
            }
        }
        return false; // Value is not present
    }


    public void remove(String keyOrValue) {
        try {
            JSONObject jsonDb = getJsonDb();
            ArrayList<String> keysToRemove = new ArrayList<> ();
            for (String key : jsonDb.keySet()) {
                String value = jsonDb.getString(key);
                if (key.equals(keyOrValue) || value.equals(keyOrValue)) {
                    keysToRemove.add(key);
                }
            }
            for (String key : keysToRemove) {
                jsonDb.remove(key);
            }
            if (!keysToRemove.isEmpty()) {
                saveJsonDb(jsonDb);
                System.out.println("Removed:  " + keyOrValue);
            } else {
                System.out.println("Key or value not found in the DB.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean containsKey(String key) {
        try {
            JSONObject jsonDb = getJsonDb();
            return jsonDb.has(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private JSONObject getJsonDb() throws IOException {
        FileInputStream fis = new FileInputStream(db);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        JSONTokener tokener = new JSONTokener(isr);
        return new JSONObject(tokener);
    }

    private void saveJsonDb(JSONObject jsonDb) throws IOException {
        try (FileWriter fileWriter = new FileWriter(DB_FILE_PATH)) {
            fileWriter.write(jsonDb.toString(2));
        }
    }
}
