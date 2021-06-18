import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class ClientWorker implements Runnable {
    ServerSocketChannel serverChannel;
    //String message;
    Messages messages;
    static int i;

    public ClientWorker(ServerSocketChannel serverChannel, Messages messages){
        this.serverChannel = serverChannel;
        this.messages = messages;
    }

    public Messages getMessage() {
        return messages;
    }

    @Override
    public void run() {
        Messages messages = new Messages();
        while (true){
            try (SocketChannel socketChannel = serverChannel.accept()){
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2<<10);

                while (socketChannel.isConnected()){
                    //System.out.println("new connection arranged");
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(inputBuffer.array(), 0,
                            bytesCount, StandardCharsets.UTF_8);
                    messages.writeOutMessage(i, msg);
                    inputBuffer.clear();
                    System.out.println("Client's text received: " + msg);
                    if (i == 0){
                        String output = "Server's response: " + messages.readMessage(i );
                    }
                    String output = "Server's response: " + messages.readMessage(i);

                    socketChannel.write(ByteBuffer.wrap(("Server: \n" + output)
                            .getBytes(StandardCharsets.UTF_8)));
                    System.out.println(messages.printChat());
                }
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
