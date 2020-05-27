package Command;

import TCPServer.CollectionManager;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
        TreeMap<Integer, Flat> houses = getManager().getHouses();
        String s = "";
        if (houses.size() != 0) {
            return   houses.entrySet().stream()
                    .map(element -> "key: " + element.getKey() + ", flat: " + element.getValue())
                    .collect(Collectors.joining("\n"));
        }
        else return "В коллекции отсутствуют элементы. Выполнение команды невозможно.";
    }
}
