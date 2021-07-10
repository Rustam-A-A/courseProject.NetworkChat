package client;
import logger.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class MessageReceiver extends Thread{
    public static final int SERVER_PORT = 23411;
    SocketChannel socketChannel;
    final ByteBuffer inputBuffer;

    Logger logger = Logger.getInstance();
    File file = new File("/Users/rustam/IdeaProjects/courseProject.NetworkChat/ClientRecords", "clientRecords1.txt");


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
                logger.log(msg + "\n", file);
                inputBuffer.clear();
            }
        } catch(Exception ex) {}
    }
}
