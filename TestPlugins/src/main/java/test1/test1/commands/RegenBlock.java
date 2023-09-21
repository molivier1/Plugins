package test1.test1.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import test1.test1.Test1;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class RegenBlock implements CommandExecutor, Listener {
    public RegenBlock(Test1 plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args){
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return true;
        }

        FileConfiguration data = YamlConfiguration.loadConfiguration(new File("./plugins/data.yml"));

        Player player = (Player) sender;

        Location regenBlock = player.getLocation();

        if(args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "create":
                    if (args.length > 1) {
                        if (data.contains(args[1])) {
                            Location delBlock = new Location(Bukkit.getWorld(data.getString(args[1] + ".world")), data.getInt(args[1] + ".x"),
                                    data.getInt(args[1] + ".y"), data.getInt(args[1] + ".z"));

                            delBlock.getBlock().setType(Material.AIR);
                        }

                        data.set(args[1] + ".world", regenBlock.getWorld().getName());
                        data.set(args[1] + ".x", regenBlock.getBlockX());
                        data.set(args[1] + ".y", regenBlock.getBlockY());
                        data.set(args[1] + ".z", regenBlock.getBlockZ());
                        try {
                            data.save("./plugins/data.yml");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        if (regenBlock.getBlock().getType() != Material.COBBLESTONE) {
                            regenBlock.getBlock().setType(Material.COBBLESTONE);
                        }

                        sender.sendMessage(ChatColor.GREEN + "RegenBlock '" + args[1] + "' has been created!");

                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage /regenblock create {regenBlockName}");
                    }
                    return true;

                case "delete":
                    if (args.length > 1) {
                        if (data.contains(args[1])) {
                            Location delBlock = new Location(Bukkit.getWorld(data.getString(args[1] + ".world")), data.getInt(args[1] + ".x"),
                                    data.getInt(args[1] + ".y"), data.getInt(args[1] + ".z"));

                            delBlock.getBlock().setType(Material.AIR);

                            data.set(args[1], null);
                            try {
                                data.save("./plugins/data.yml");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            sender.sendMessage(ChatColor.GREEN + "RegenBlock '" + args[1] + "' has been deleted!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "RegenBlock '" + args[1] + "' doesn't exists!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage /regenblock delete {regenBlockName}");
                    }
                    return true;

                case "list":
                    sender.sendMessage(ChatColor.GREEN + "List of RegenBlocks :");
                    for (String key : data.getKeys(false)) {
                        //We are getting every key from our config.yml file
                        ConfigurationSection l = data.getConfigurationSection(key);

                        sender.sendMessage("-" + Objects.requireNonNull(l.getCurrentPath()));
                    }
                    return true;

                case "tp":
                    if (args.length > 1) {
                        int verif = 0;
                        for (String key : data.getKeys(false)) {
                            //We are getting every key from our config.yml file
                            ConfigurationSection l = data.getConfigurationSection(key);
                            if (args[1].equals(Objects.requireNonNull(l.getCurrentPath()))) {
                                if (data.contains(args[1])) {
                                    World w = Bukkit.getWorld(data.getString(args[1] + ".world"));
                                    int x = l.getInt("x");
                                    int y = l.getInt("y");
                                    int z = l.getInt("z");

                                    double xf = x + 0.5;
                                    y++;
                                    double zf = z + 0.5;

                                    Location telep = new Location(w, xf, y, zf);
                                    player.teleport(telep);

                                    sender.sendMessage(ChatColor.GREEN + "Teleported to RegenBlock '" + args[1] + "'!");
                                    verif = 1;
                                }
                            }
                        }
                        if (verif == 0) {
                            sender.sendMessage(ChatColor.RED + "RegenBlock '" + args[1] + "' doesn't exists!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage /regenblock tp {regenBlockName}");
                    }

                    return true;

                default:
                    sender.sendMessage(ChatColor.RED + "Usage /regenblock {create/delete/list/tp}");
                    return true;
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "Usage /regenblock {create/delete/list/tp}");
        }
        return true;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        FileConfiguration data = YamlConfiguration.loadConfiguration(new File("./plugins/data.yml"));

        for (String key : data.getKeys(false) ){
            //We are getting every key from our config.yml file
            ConfigurationSection l = data.getConfigurationSection(key);
            World w = Bukkit.getWorld(l.getString("world"));
            int x = l.getInt("x");
            int y = l.getInt("y");
            int z = l.getInt("z");
            Location regenBlock = new Location(w, x, y, z);

            if(event.getBlock().getLocation().equals(regenBlock)){
                Location location = event.getBlock().getLocation();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        randomChangeBlock(location);
                    }
                }.runTaskLater(Test1.getInstance(), 2);
            }
        }
    }

    public void randomChangeBlock(Location location){
        Random random = new Random();

        int number = random.nextInt(11);

        switch (number){
            case 0:
            case 1:
            case 2:
            case 3:
                location.getBlock().setType(Material.COBBLESTONE);
                break;

            case 4:
            case 5:
                location.getBlock().setType(Material.STONE);
                break;

            case 6:
            case 7:
                location.getBlock().setType(Material.IRON_ORE);
                break;

            case 8:
            case 9:
                location.getBlock().setType(Material.COAL_ORE);
                break;

            case 10:
                location.getBlock().setType(Material.DIAMOND_ORE);
                break;
        }
    }
}
