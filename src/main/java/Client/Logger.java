package Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger<formatForDateNow> {
    protected static int num = 1;
    private static Logger instance;
    private static Logger logger;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String time;

    private Logger() {
        this.time = LocalDateTime.now().format(formatter);
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

