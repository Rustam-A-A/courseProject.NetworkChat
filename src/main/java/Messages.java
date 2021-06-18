import java.util.ArrayList;
import java.util.List;

public class Messages {
    List<String> messages = new ArrayList<>();

    public Messages(){
    }

    public void writeOutMessage(int i, String msg){
        messages.add(i, msg);
    }

    public String readMessage(int i){
        return messages.get(i);
    }

    public String printChat() {
        String result = "";
        for (int i = 0; i < messages.size(); i++) {
            result += i + ": " + messages.get(i) + "\n";
        }
        return result;
    }
}
