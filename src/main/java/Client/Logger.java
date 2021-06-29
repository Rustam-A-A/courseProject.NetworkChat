package Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger<formatForDateNow> {
    protected int num = 1;
    private static Logger instance;
    String time;
    private static Logger logger;

    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Logger() {
        this.time = dateFormat.format(date);
        this.num = num;
//        File clientRecords1 = new File(
//                "/Users/rustam/IdeaProjects/courseProject.NetworkChat/ClientRecords",
//                "clientRecords1.txt"
   //     );
    }

    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    public void log(String msg) {
        File file = new File("/Users/rustam/IdeaProjects/courseProject.NetworkChat/ClientRecords", "clientRecords1.txt");
        try(FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(" [" + time + ", " + num++ + "] " + msg);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}

