package Command;

import Object.Flat;
import TCPServer.CollectionManager;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * удалить из коллекции все элементы, ключ которых превышает заданный.
 */
public class RemoveGreaterKey extends Command {
    public RemoveGreaterKey(CollectionManager manager) {
        super(manager);
        setDescription("удалить из коллекции все элементы, ключ которых превышает заданный.");
    }

    @Override
    public String execute(Object args) {
        Integer key = (Integer)args;
        TreeMap<Integer, Flat> houses = getManager().getHouses();
        if (houses.size() != 0){
            boolean b = false;
            for(Iterator<Integer> it = houses.keySet().iterator(); it.hasNext();){
                Integer s = it.next();
                if (s.compareTo(key) > 0) {
                    it.remove();
                    houses.remove(s);
                    b = true;
                }
            }
            if(b) {
                getManager().save();
                return "Команда успешно выполнена.";
            }
            return "В коллекции нет элементов с соответствующими ключами.";
        } else return "В коллекции отсутствуют элементы. Выполнение команды не возможно.";
    }
}
