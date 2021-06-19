public class GettingNickName {
    String text;

    public static String getNickName(String text){
        String[] result = text.split(": ", 2);
        return result[0];
    }

    public static String getContext(String text){
        String[] result = text.split(": ", 2);
        return result[1];
    }
}
