package TCPClient;

import Object.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * Класс для отправки данных на сервер.
 */

public class TCPSender {

    private Flat flat;
    private NewFlat a = new NewFlat();
    private String script;
    private int key;
    private Transport transport;
    private InetSocketAddress is;
    private SocketChannel ssChannel;
    private ObjectOutputStream oos;
    private boolean b;
    private ArrayList<File> scripts = new ArrayList<>();

    public TCPSender(InetSocketAddress is) {
        this.is = is;
    }

    private void LocalOpen() throws IOException {
        ssChannel = SocketChannel.open(is);
        oos = new ObjectOutputStream(ssChannel.socket().getOutputStream());
    }

    public boolean Sender(String[] command) throws IOException {
        TCPReceiver r = new TCPReceiver(is);
        if (command[0].equals("insert") || command[0].equals("update")) {
            if (command.length == 2) {
                try {
                    key = Integer.parseInt(command[1]);
                    flat = a.newFlat();
                    LocalOpen();
                    oos.writeObject(command[0] + " " + command[1]);
                    oos.writeObject(flat);
                    oos.close();
                    return true;
                } catch (NumberFormatException ex) {
                    System.out.println("Аргумент не является значением типа Integer");
                }
            } else System.out.println("Комманда некорректна.");
        } else if (command[0].equals("remove_greater")) {
            flat = a.newFlat();
            LocalOpen();
            oos.writeObject(command[0]);
            oos.writeObject(flat);
            oos.close();
            return true;
        } else if (command[0].equals("remove_greater_key") || command[0].equals("remove_key")) {
            if (command.length == 2) {
                try {
                    key = Integer.parseInt(command[1]);
                    LocalOpen();
                    oos.writeObject(command[0]);
                    oos.writeObject(key);
                    oos.close();
                    return true;
                } catch (NumberFormatException ex) {
                    System.out.println("Аргумент не является значением типа Integer");
                }
            } else System.out.println("Комманда некорректна.");
        } else if (command[0].equals("count_by_transport")) {
            command[1] = command[1].toUpperCase();
            if (command.length == 2) {
                try {
                    transport = Transport.valueOf(command[1]);
                    LocalOpen();
                    oos.writeObject(command[0]);
                    oos.writeObject(transport);
                    oos.close();
                    return true;
                } catch (IllegalArgumentException | NullPointerException ex) {
                    System.out.println("Значение поля Transport некорректно. Возможные значения: FEW, NONE, LITTLE, NORMAL, ENOUGH.");
                }
            } else System.out.println("Комманда некорректна.");
        } else if (command[0].equals("execute_script")) {
            if (command.length == 2) {
                if (!command[1].equals("")) {
                    File file1 = new File(command[1]);
                    if (!file1.exists())
                        System.out.println("Файла с таким названием не существует.");
                    else if (!file1.canRead())
                        System.out.println("Файл защищён от чтения. Невозможно выполнить скрипт.");
                    else if (scripts.contains(file1)) {
                        System.out.println("Могло произойти зацикливание при исполнении скрипта: " + command[1] + "\nКоманда не будет выполнена. Переход к следующей команде");
                    } else {
                        script = command[1];
                        LocalOpen();
                        oos.writeObject(command[0]);
                        oos.writeObject("mew");
                        oos.close();
                        r.Receiver();
                        scripts.add(file1);
                        try (BufferedReader commandReader = new BufferedReader(new FileReader(file1))) {
                            String line = commandReader.readLine();
                            while (line != null) {
                                b = Sender(line.split(" "));
                                if(b) r.Receiver();
                                line = commandReader.readLine();
                            }
                        } catch (IOException ex) {
                            System.out.println("Невозможно считать скрипт");
                        }
                        scripts.remove(scripts.size() - 1);
                        return false;
                    }
                }
            } else System.out.println("Команда введена некорректно.");
        } else {
            LocalOpen();
            oos.writeObject(command[0]);
            oos.writeObject("mew");
            oos.close();
            return true;
        }
        return false;
    }
}
