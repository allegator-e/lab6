package Command;

import Object.*;
import TCPServer.CollectionManager;
import java.time.LocalDateTime;
import java.util.*;

/**
 * вывести количество элементов, значение поля transport которых равно заданному.
 */
public class GroupCountingByCreationDate extends Command {
    public GroupCountingByCreationDate(CollectionManager manager) {
        super(manager);
        setDescription("сгруппировать элементы коллекции по значению поля creationDate, вывести количество элементов в каждой группе.");
    }

    @Override
    public String execute(Object args) {
        TreeMap<Integer, Flat> houses = getManager().getHouses();
        if (houses.size() != 0) {
            HashMap<LocalDateTime, Integer> creationDates = new HashMap<>();
            for (Integer key : houses.keySet()) {
                if (creationDates.containsKey(houses.get(key).getCreationDate())) {
                    creationDates.replace(houses.get(key).getCreationDate(), creationDates.get(houses.get(key).getCreationDate()) + 1);
                } else creationDates.put(houses.get(key).getCreationDate(), 1);
            }
            String s = "";
            for(Iterator<LocalDateTime> it = creationDates.keySet().iterator() ; it.hasNext();){
                LocalDateTime date= it.next();
                s = s + date +": "+ creationDates.get(date) + "\n";
            }
            return s;
        } return "В коллекции отсутствуют элементы. Выполнение команды не возможно.";
    }
}
