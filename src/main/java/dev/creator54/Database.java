package dev.creator54;

import java.io.*;

public class Database {
    private static final String REDIRECTS_FOLDER_PATH = System.getProperty("user.dir") + "/redirects/";

    public Database() {
        File redirectsFolder = new File(REDIRECTS_FOLDER_PATH);
        if (!redirectsFolder.exists()) {
            redirectsFolder.mkdirs();
        }
    }

    public void add(String key, String value) {
        File[] files = new File(REDIRECTS_FOLDER_PATH).listFiles();

        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String existingValue = reader.readLine();
                    if (existingValue.equals(value)) {
                        String existingKey = file.getName();
                        System.out.println(existingKey + " -> " + value);
                        return; // Value is already present, do not create a duplicate
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String filePath = REDIRECTS_FOLDER_PATH + key;
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("  <meta http-equiv=\"refresh\" content=\"0; URL='" + value + "'\" />\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("</body>\n");
            writer.write("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(key + " -> " + value);
    }

    public void remove(String keyOrValue) {
        File[] files = new File(REDIRECTS_FOLDER_PATH).listFiles();

        if (files != null) {
            boolean removed = false;

            for (File file : files) {
                String fileName = file.getName();
                String filePath = file.getAbsolutePath();

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String value = reader.readLine();

                    if (fileName.equals(keyOrValue) || value.equals(keyOrValue)) {
                        file.delete();
                        System.out.println("Removed: " + keyOrValue);
                        removed = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!removed) {
                System.out.println("Key or value not found in the DB.");
            }
        } else {
            System.out.println("Database is empty.");
        }
    }

    public boolean containsKey(String key) {
        File file = new File(REDIRECTS_FOLDER_PATH + key);
        return file.exists();
    }
}
