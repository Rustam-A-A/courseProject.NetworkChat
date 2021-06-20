import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

class ClientWorker implements Runnable {
    ServerSocketChannel serverChannel;
    final List<SocketChannel>channels;

    public ClientWorker(ServerSocketChannel serverChannel, List<SocketChannel> channels){
        this.serverChannel = serverChannel;
        this.channels = channels;
    }

    //Map clients = new HashMap();
    List<String>usersNickNames = new ArrayList<>();


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

                    String nickName = GettingNickName.getNickName(msg);
                    usersNickNames.add(nickName);

                    //clients.put(nickName, socketChannel);

                    inputBuffer.clear();
                    System.out.println(msg);
                    synchronized (channels){
                        for (SocketChannel c : channels){
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
