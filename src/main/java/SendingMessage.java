import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SendingMessage {
    String name;
    String message;
    PrintWriter output;
    SocketChannel socketChannel;

    public void sendMessage(String name,String  output) throws IOException {
        socketChannel.write(ByteBuffer.wrap(("Server: \n" + output)
                .getBytes(StandardCharsets.UTF_8)));
    }
}
