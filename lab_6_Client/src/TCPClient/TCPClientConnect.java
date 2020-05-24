package TCPClient;
import java.io.IOException;

import java.net.InetSocketAddress;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс для подключения к серверу
 */
public class TCPClientConnect {

    public static void main(String[] args) {
       Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Работа программы завершена!")));
        InetSocketAddress is = new InetSocketAddress(1111);
        TCPSender s = new TCPSender(is);
        TCPReceiver r = new TCPReceiver(is);
        boolean b = false;
       while (true) {
            try {
                String[] command;
                Scanner commandReader = new Scanner(System.in);
                System.out.print(">>");
                command = commandReader.nextLine().trim().split(" ");
                if (command[0].equals("exit")) {
                    System.exit(1);
                } else {
                    b = s.Sender(command);
                }
                if (b) r.Receiver();
            } catch (IOException e) {
                System.out.println("Подключение к серверу невозможно");
            } catch (InputMismatchException e) {
                System.out.println("Команда введена некорректно. Введите help.");
           }
        }
    }
}

