package Server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger<formatForDateNow> {


    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    public void log(String msg) {
        File file = new File("/Users/rustam/IdeaProjects/courseProject.NetworkChat/serverRecords", "serverRecords.txt");
        try(FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(" [" + time + ", " + num++ + "] " + msg);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}

