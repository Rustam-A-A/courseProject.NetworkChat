import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static final int SERVER_PORT = 10101;

    public static void main(String[] args) throws IOException {

        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", SERVER_PORT);
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);

        try (Scanner scanner = new Scanner(System.in)){

            System.out.println("Please tap your nickname in one word");
            String name = scanner.nextLine();
            User user = new User(name);

            final ByteBuffer inputBuffer= ByteBuffer.allocate(2 << 10);
            String msg;
            while (true){
                System.out.println("\nYour message: ");
                msg = user.getName() + ": " + scanner.nextLine();
                if ("end".equals(scanner.nextLine())) break;
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0,
                        bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        } finally {
            socketChannel.close();
        }
    }
}


