package Command;

import Object.*;
import TCPServer.CollectionManager;
import java.util.TreeMap;

/**
 * вывести количество элементов, значение поля transport которых равно заданному.
 */
public class CountByTransport extends Command {
    public CountByTransport(CollectionManager manager) {
        super(manager);
        setDescription("вывести количество элементов, значение поля transport которых равно заданному.");
    }

    @Override
    public String execute(Object args) {
        TreeMap<Integer, Flat> houses = getManager().getHouses();
        Transport transport = (Transport) args;
        if (houses.size() != 0) {
            int count_by_transport = 0;
            for (Integer key : houses.keySet()) {
                if (houses.get(key).getTransport().equals(transport))
                    count_by_transport++;
            }
            return "Количество элементов, значение поля transport которых равно " + transport + ": " + count_by_transport;
        }
        return "В коллекции отсутствуют элементы. Выполнение команды не возможно.";
    }
}
