package test1.test1.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AutoCompletion implements TabCompleter {
    List<String> arguments = new ArrayList<String>();
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String [] args){
        if (arguments.isEmpty()) {
            arguments.add("create");
            arguments.add("delete");
            arguments.add("list");
            arguments.add("move");
            arguments.add("tp");
        }

        List<String> result = new ArrayList<String>();

        if (args.length == 1) {
            for (String a : arguments){
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
            }
            return result;
        }

        return null;
    }
}
