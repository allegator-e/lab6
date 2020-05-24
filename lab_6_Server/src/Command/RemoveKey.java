package Command;

import Object.Flat;
import TCPServer.CollectionManager;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * удалить элемент из коллекции по его ключу.
 */
public class RemoveKey extends Command {
    public RemoveKey (CollectionManager manager) {
        super(manager);
        setDescription("удалить элемент из коллекции по его ключу.");
    }

    @Override
    public String execute(Object args) {
        Integer key = (Integer)args;
        TreeMap<Integer, Flat> houses = getManager().getHouses();
        if (houses.size() != 0){
            for(Iterator<Integer> it = houses.keySet().iterator(); it.hasNext();){
                Integer s = it.next();
                if (s.equals(key)) {
                    it.remove();
                    houses.remove(s);
                    getManager().save();
                    return "Команда успешно выполнена.";
                }
            }return "В коллекции нет элементов с соответствующими ключами.";
        } else return "В коллекции отсутствуют элементы. Выполнение команды не возможно.";
    }
}
