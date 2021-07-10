package server;
import logger.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

class ClientWorker implements Runnable {
    ServerSocketChannel serverChannel;
    final CopyOnWriteArrayList<SocketChannel>channels;

    public ClientWorker(ServerSocketChannel serverChannel, CopyOnWriteArrayList<SocketChannel>channels){
        this.serverChannel = serverChannel;
        this.channels = channels;
    }

    Logger logger = Logger.getInstance();
    File file = new File("/Users/rustam/IdeaProjects/courseProject.NetworkChat/serverRecords", "serverRecords.txt");



    @Override
    public void run() {
        while (true){
            try (SocketChannel socketChannel = serverChannel.accept()){
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2<<10);
                channels.add(socketChannel);

                while (socketChannel.isConnected()){
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(inputBuffer.array(), 0,
                            bytesCount, StandardCharsets.UTF_8);
                    logger.log(msg + "\n", file);

                    inputBuffer.clear();
                    System.out.println(msg);
                        for (SocketChannel c : channels){
                            if (!c.equals(socketChannel)){
                                c.write(ByteBuffer.wrap(("\n" + msg)
                                        .getBytes(StandardCharsets.UTF_8)));
                            }
                        }
                }
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
