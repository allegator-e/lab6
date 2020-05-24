package Command;

import TCPServer.CollectionManager;
import java.util.HashMap;
import java.util.Iterator;

/**
 *вывести справку по доступным командам.
 */
public class Help extends Command {
    private HashMap<String, Command> commands ;
    public Help(CollectionManager manager, HashMap<String, Command> commands) {
        super(manager);
        setDescription("вывести справку по доступным командам.");
        this.commands=commands;
    }
    @Override
    public String execute(Object object)
    {
        String s = "";
        for (Iterator<String> it = commands.keySet().iterator(); it.hasNext();) {
                String key = it.next();
                s = s + key + ": " + commands.get(key).getDescription() + "\n";
        }
        s = s + "execute_script: считать и исполнить скрипт из указанного файла. \n";
        return s;
    }
}
