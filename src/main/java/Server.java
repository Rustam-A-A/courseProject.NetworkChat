import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static final int SERVER_PORT = 10101;
    ServerSocketChannel serverChannel;

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", SERVER_PORT));

        while(true){
            try {
                ClientWorker w = new ClientWorker(serverChannel);
                Thread t = new Thread(w);
                t.start();
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }
    }
}
