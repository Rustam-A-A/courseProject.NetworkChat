import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

class ClientWorker implements Runnable {
    ServerSocketChannel serverChannel;

    public ClientWorker(ServerSocketChannel serverChannel){
        this.serverChannel = serverChannel;
    }

    @Override
    public void run() {
        while (true){
            try (SocketChannel socketChannel = serverChannel.accept()){
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2<<10);

                while (socketChannel.isConnected()){
                    System.out.println("new connection arranged");
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(inputBuffer.array(), 0,
                            bytesCount, StandardCharsets.UTF_8);
                    inputBuffer.clear();
                    System.out.println("Client's text received: " + msg);
                    String output = "Server's response: " + msg;
                    socketChannel.write(ByteBuffer.wrap(("Server: \n" + output)
                            .getBytes(StandardCharsets.UTF_8)));
                }
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
