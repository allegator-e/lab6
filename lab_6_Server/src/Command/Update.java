package Command;

import Object.Flat;
import TCPServer.CollectionManager;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * обновить значение элемента коллекции, id которого равен заданному.
 */
public class Update extends Command{
    private Integer id;
    public Update(CollectionManager manager, Integer id) {
        super(manager);
        this.id = id;
        setDescription("обновить значение элемента коллекции, id которого равен заданному.");
    }

    @Override
    public String execute(Object args) {
        TreeMap<Integer, Flat> houses = getManager().getHouses();
        Flat flat = (Flat) args;
        if (houses.size() != 0) {
                for (Iterator<Integer> it = houses.keySet().iterator(); it.hasNext();) {
                    Integer key = it.next();
                    if (houses.get(key).getId().equals(id)) {
                        flat.setId(id);
                        flat.setCreationDate(houses.get(key).getCreationDate());
                        houses.replace(key, flat);
                        getManager().save();
                        return "Элемент коллекции успешно обновлен.";
                    }
                }
                return("В коллекции не найдено элемента с указанным id.");
        } else return ("В коллекции отсутствуют элементы. Выполнение команды не возможно.");
    }
}