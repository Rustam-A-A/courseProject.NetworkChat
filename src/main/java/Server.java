import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int SERVER_PORT = 10101;
    private static Messages messages;
    ServerSocketChannel serverChannel;

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", SERVER_PORT));

        while(true){
            try {
                ClientWorker w = new ClientWorker(serverChannel, messages);
                Thread t = new Thread(w);
                t.start();
                Thread.sleep(100);
            }catch (NullPointerException | InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
