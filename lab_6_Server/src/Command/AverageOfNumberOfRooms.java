package Command;

import TCPServer.CollectionManager;
import Object.*;
import java.util.TreeMap;

/**
 *  вывести среднее значение поля numberOfRooms для всех элементов коллекции.
 */
public class AverageOfNumberOfRooms extends Command {
    public AverageOfNumberOfRooms(CollectionManager manager) {
        super(manager);
        setDescription("вывести среднее значение поля numberOfRooms для всех элементов коллекции.");
    }

    @Override
    public String execute(Object args) {
        TreeMap<Integer, Flat> houses = getManager().getHouses();
        if (houses.size() != 0) {
            float sum_number_of_rooms = 0;
            for (Integer key : houses.keySet()) {
                sum_number_of_rooms += houses.get(key).getNumberOfRooms();
            }
            return "Cреднее значение поля numberOfRooms для всех элементов коллекции: " + sum_number_of_rooms / houses.size();
        } return "В коллекции отсутствуют элементы. Выполнение команды не возможно.";
    }
}
