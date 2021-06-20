import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MessageReceiver extends Thread{
    public static final int SERVER_PORT = 23411;
    SocketChannel socketChannel;
    final ByteBuffer inputBuffer;


    public MessageReceiver(SocketChannel socketChannel, ByteBuffer inputBuffer){
        this.socketChannel = socketChannel;
        this.inputBuffer = inputBuffer;
    }

    @Override
    public void run() {
        try {
            int bytesCount = socketChannel.read(inputBuffer);
            System.out.println(new String(inputBuffer.array(), 0,
                    bytesCount, StandardCharsets.UTF_8).trim());
            inputBuffer.clear();
        } catch (IOException  | NullPointerException exception) {
            exception.printStackTrace();
        }
    }
}
