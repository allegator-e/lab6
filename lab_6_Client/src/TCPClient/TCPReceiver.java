package TCPClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class TCPReceiver {
    private InetSocketAddress is;
    public TCPReceiver(InetSocketAddress is){
        this.is = is; }

    /**
     * Класс для получения данных с сервера
     */
    public void receiver() {
        try {
            String fromServer;
            SocketChannel ssChannel = SocketChannel.open(is);
            ObjectInputStream ois = new ObjectInputStream(ssChannel.socket().getInputStream());
            fromServer = (String) ois.readObject();
            System.out.println(fromServer);
        }catch (IOException|ClassNotFoundException e) {
            System.out.println("В процессе получения данных с сервера возникла ошибка.");
        }
    }
}
