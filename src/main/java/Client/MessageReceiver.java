package Client;

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

    Logger logger = Logger.getInstance();

    public MessageReceiver(SocketChannel socketChannel, ByteBuffer inputBuffer){
        this.socketChannel = socketChannel;
        this.inputBuffer = inputBuffer;
    }

    @Override
    public void run() {
        try {
            while(socketChannel != null) {
                int bytesCount = socketChannel.read(inputBuffer);
                String msg = new String(inputBuffer.array(), 0,
                        bytesCount, StandardCharsets.UTF_8).trim();
                System.out.println(msg);
                logger.log(msg + "\n");
                inputBuffer.clear();
            }
        } catch(Exception ex) {}
    }
}
