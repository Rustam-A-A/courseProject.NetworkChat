package Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client extends Thread{
    public static final int SERVER_PORT = 23411;

    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getInstance();

        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", SERVER_PORT);
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);


        try (Scanner scanner = new Scanner(System.in)){

            System.out.println("Please tip your NICKNAME in one word\n");
            String nickName = scanner.nextLine();
            User user = new User(nickName);
            final ByteBuffer inputBuffer= ByteBuffer.allocate(2 << 10);

            //String msg;
            while (true){
                new Thread(new MessageReceiver(socketChannel, inputBuffer)).start();

                System.out.println("Your message: \n");
                String msg = user.getName() + ": " + scanner.nextLine();
                logger.log(msg + "\n");
                if ("end".equals(scanner.nextLine())) break;
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            }
        } finally {
            socketChannel.close();
        }

    }

//    public static void writeDownYourMsg(File file, String text){
//        try(FileOutputStream fileOutputStream = new FileOutputStream(file, true);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
//            objectOutputStream.writeObject(text);
//        }catch(IOException ex){
//            System.out.println(ex.getMessage());
//        }
//    }


}


