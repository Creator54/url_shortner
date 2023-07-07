package dev.creator54;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Random;

public class Main {

    private static String newRedirect = "";
    static Database db;

    public Main() throws IOException {
        db = new Database();
    }

    public static String getRedirect() {
        int length = 5; // Length of redirect

        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // Generate lowercase alphabetic characters
            sb.append(randomChar);
        }

        newRedirect = sb.toString();

        if (db.containsKey(newRedirect)) {
            newRedirect = getRedirect();
        }

        return newRedirect;
    }

    public static void main(String[] args) {
        try {
            Main main = new Main(); // Instantiate the Main class to initialize the db object

            if (args.length < 2) {
                System.out.println("Invalid arguments. Please provide an operation and a value.");
                return;
            }
            if(args[0].equals ("add")){
                db.add(getRedirect(), args[1]);
            }
            if(args[0].equals ("remove")){
                db.remove (args[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
