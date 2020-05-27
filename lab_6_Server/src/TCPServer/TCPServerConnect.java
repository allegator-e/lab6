package TCPServer;

import Command.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Класс для получения данных с клиента и их обработки(запуск соответствующей команды)
 */

public class TCPServerConnect {
    public static void main(String[] args) {
        ArrayList<String> history = new ArrayList<>();
        Logger LOGGER = Logger.getLogger(TCPServerConnect.class.getName());
        try (FileInputStream ins = new FileInputStream("lib/log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        try {
            Scanner commandReader = new Scanner(System.in);
            System.out.println("Введите Порт");
            Integer port = 0;
            while (true) {
                try {
                    System.out.print("Введите порт:");
                    port = Integer.parseInt(commandReader.nextLine());
                    if (port <= 65535 && port >= 1) break;
                } catch (NumberFormatException e) {
                    System.out.println("Порт должен принимать целочисленные значения от 1 до 65535.");
                }
            }
            ServerSocket ss = new ServerSocket(port);
            CollectionManager serverCollection = new CollectionManager(args[0]);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                serverCollection.save();
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LOGGER.log(Level.INFO, "Работа сервера завершена!");
            }));
            HashMap<String, Command> availableCommands = new HashMap<>();
            int arg = 0;
            while (true) {
                Socket s = ss.accept();
                LOGGER.log(Level.INFO, "Устанавливается соединение...");
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                String command = (String) ois.readObject();
                String[] parseCommand = command.trim().split(" ", 2);
                command = parseCommand[0];
                System.out.println(command);
                if (parseCommand.length == 2) {
                    arg = Integer.parseInt(parseCommand[1]);
                }
                availableCommands.put("help", new Help(serverCollection, availableCommands));
                availableCommands.put("insert", new Insert(serverCollection, arg));
                availableCommands.put("info", new Info(serverCollection));
                availableCommands.put("clear", new Clear(serverCollection));
                availableCommands.put("show", new Show(serverCollection));
                availableCommands.put("update_id", new Update(serverCollection, arg));
                availableCommands.put("remove_greater", new RemoveGreater(serverCollection));
                availableCommands.put("history", new History(serverCollection));
                availableCommands.put("remove_key", new RemoveKey(serverCollection));
                availableCommands.put("remove_greater_key", new RemoveGreaterKey(serverCollection));
                availableCommands.put("average_of_number_of_rooms", new AverageOfNumberOfRooms(serverCollection));
                availableCommands.put("group_counting_by_creation_date", new GroupCountingByCreationDate(serverCollection));
                availableCommands.put("count_by_transport", new CountByTransport(serverCollection));
                Command errorCommand = new Command(null) {
                    @Override
                    public String execute(Object args) {
                        if (parseCommand[0].equals("execute_script"))
                            return "Обработка скрипта запущена.";
                        return "Неверная команда.";
                    }
                };
                Object o = ois.readObject();
                if (parseCommand[0].equals("history")) {
                    o = history;
                }
                TCPServerSender t = new TCPServerSender(availableCommands.getOrDefault(command, errorCommand).execute(o), ss);
                if (availableCommands.containsKey(parseCommand[0]) || parseCommand[0].equals("execute_script")) {
                    history.add(parseCommand[0]);
                    if (history.size() > 8) {
                        history.remove(0);
                    }
                }
                t.sender();
                ois.close();
                LOGGER.log(Level.INFO, "Окончание соединения.");
            }

        } catch (IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, "Путь до файла xml нужно передать через аргумент командной строки.");
            System.exit(1);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Возникла проблема во время работы программы. Все плохо... ");
        }
    }
}
