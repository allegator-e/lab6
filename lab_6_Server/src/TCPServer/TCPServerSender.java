package TCPServer;

import Command.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TCPServerSender{
    private String str;
    private ServerSocket ss;
    private HashMap<String, Command> availableCommands = new HashMap<>();
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("lib/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(TCPServerSender.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }
    public TCPServerSender(String str,ServerSocket ss ) {
        this.str =str;
        this.ss =ss;

    };
    public HashMap<String, Command> getCommand()
    {
        return availableCommands;
    }
    /**
    *Отправка данных клиенту
     */
    public void Sender() {
        try {
            Socket s = ss.accept();
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(str);
            oos.flush();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Ошибка передачи данных" );
        }
    }
}
