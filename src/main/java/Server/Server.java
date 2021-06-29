package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    public static final int SERVER_PORT = 23411;
    ServerSocketChannel serverChannel;

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", SERVER_PORT));

        List<SocketChannel> channels = Collections.synchronizedList(new ArrayList<>());

        while(true){
            synchronized (channels){
                try {
                    ClientWorker w = new ClientWorker(serverChannel, channels);
                    Thread t = new Thread(w);
                    t.start();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
