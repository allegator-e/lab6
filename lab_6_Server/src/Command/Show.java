package Command;

import TCPServer.CollectionManager;
import java.util.Iterator;
import java.util.TreeMap;
import Object.*;

/**
 * вывести в стандартный поток вывода все элементы коллекции в строковом представлении.
 */
public class Show extends Command {
    public Show(CollectionManager manager) {
        super(manager);
        setDescription("вывести в стандартный поток вывода все элементы коллекции в строковом представлении.");
    }

    @Override
    public String execute(Object args) {
        TreeMap<Integer, Flat> flat = getManager().getHouses();
        String s = "";
        if (flat.size() != 0) {
            for (Iterator<Integer> it = flat.keySet().iterator(); it.hasNext();) {
                Integer key = it.next();
                s = s + "key: " + key + ", " + flat.get(key).toString() + "\n";
            }
            return s;
        }
        else return "В коллекции отсутствуют элементы. Выполнение команды невозможно.";
    }
}
