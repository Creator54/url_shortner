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

        String[] contentLines = {
                "<!DOCTYPE html>",
                "<html>",
                "<head>",
                "  <meta http-equiv=\"refresh\" content=\"0; URL='" + value + "'\" />",
                "</head>",
                "<body>",
                "</body>",
                "</html>"
        };

        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    boolean contentMatch = true;
                    for (String line : contentLines) {
                        String fileLine = reader.readLine();
                        if (fileLine == null || !fileLine.equals(line)) {
                            contentMatch = false;
                            break;
                        }
                    }

                    if (contentMatch) {
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
            for (String line : contentLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(key + " -> " + value);
    }

    public void remove(String keyOrValue) {
        File[] files = new File(REDIRECTS_FOLDER_PATH).listFiles();

        boolean removed = false;

        for (File file : files) {
            if(file.getName ().equals (keyOrValue)){
                file.delete ();
                removed = true;
                break;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains (keyOrValue)) {
                        file.delete();
                        removed = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!removed) {
            System.out.println("Redirect does not exists in the Database.");
        }
        else {
            System.out.println ("Redirect "+ keyOrValue + " removed !");
        }
    }


    public boolean containsKey(String key) {
        File file = new File(REDIRECTS_FOLDER_PATH + key);
        return file.exists();
    }
}
