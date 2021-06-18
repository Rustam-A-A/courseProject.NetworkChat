import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

class ClientWorker implements Runnable {
    ServerSocketChannel serverChannel;
    //String message;
    Messages messages;
    String name;
    static int i;
    List<String>names = new ArrayList<>();
    List<SocketChannel>socketChannels = new ArrayList<>();
    //Map clients = new HashMap();

    //CopyOnWriteArrayList<String> names;
    //Vector<String>names;

    public ClientWorker(ServerSocketChannel serverChannel, Messages messages){
        this.serverChannel = serverChannel;
        this.messages = messages;
    }

    public Messages getMessage() {
        return messages;
    }

    @Override
    public void run() throws NullPointerException{
        Messages messages = new Messages();
        while (true){
            try (SocketChannel socketChannel = serverChannel.accept()){
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2<<10);
                //socketChannels.add(socketChannel);

                while (socketChannel.isConnected()){
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(inputBuffer.array(), 0,
                            bytesCount, StandardCharsets.UTF_8);

                    //to form the List of the names
                    String[] result = msg.split(": ", 2);
                    String userName = result[0];
                    String text = result[1];
                    //System.out.println("Check the name " + name);
                    names.add(userName);
                    //clients.put(name, socketChannel);

                    messages.writeOutMessage(i, msg);
                    inputBuffer.clear();
                    System.out.println("Client's text received: " + msg);

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
