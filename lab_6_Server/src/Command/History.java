package Command;

import TCPServer.CollectionManager;

import java.util.ArrayList;
import java.util.Iterator;
import TCPServer.TCPServerConnect;

/**
 * вывести последние 9 команд (без их аргументов).
 */
public class History extends Command {
    public static ArrayList<String> history = new ArrayList<>();
    public History(CollectionManager manager) {
        super(manager);
        setDescription("вывести последние 9 команд (без их аргументов).");
    }

    @Override
    public String execute(Object args) {
        String s = "";
        if(!history.isEmpty()) {
            for (Iterator<String> it = history.iterator(); it.hasNext(); ) {
                String command = it.next();
                s = s + command + "\n";
            }
        }
        return s;
    }
}
