package test1.test1.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AutoCompletion implements TabCompleter {
    List<String> arguments = new ArrayList<String>();

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<String>();

        switch (command.getName()) {
            case "regenblock":
                if (arguments.isEmpty()) {
                    arguments.add("create");
                    arguments.add("delete");
                    arguments.add("list");
                    arguments.add("move");
                    arguments.add("reload");
                    arguments.add("tp");
                }

                result = sendResult(args);
                break;

            case "betterfurnace":
                if (arguments.isEmpty()) {
                    arguments.add("clear");
                    arguments.add("reload");
                }

                result = sendResult(args);
                break;
        }
        return result;
    }

    public List<String> sendResult(String[] args) {
        List<String> result = new ArrayList<String>();

        if (args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
            }
            return result;
        }
        return null;
    }
}
