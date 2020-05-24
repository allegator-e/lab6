package Command;

import Object.*;
import TCPServer.CollectionManager;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * удалить из коллекции все элементы, превышающие заданный.
 */
public class RemoveGreater extends Command{

    public RemoveGreater(CollectionManager manager) {
        super(manager);
        setDescription("удалить из коллекции все элементы, превышающие заданный.");
    }

    @Override
    public String execute(Object args) {
        TreeMap<Integer, Flat> houses = getManager().getHouses();
        Flat flat = (Flat) args;
        if (houses.size() != 0) {
            for(Iterator<Integer> it = houses.keySet().iterator(); it.hasNext();)
            {
                Integer key = it.next();
                if (houses.get(key).compareTo(flat) > 0) {
                    it.remove();
                    houses.remove(key);
                    return "Команда успешно выполнена.";
                }
            }
        } return "В коллекции отсутствуют элементы. Выполнение команды не возможно.";
    }
}
