package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    public static final int SERVER_PORT = 23411;
    ServerSocketChannel serverChannel;

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", SERVER_PORT));

        CopyOnWriteArrayList<SocketChannel> channels = new CopyOnWriteArrayList<SocketChannel> ();

        while(true){
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
