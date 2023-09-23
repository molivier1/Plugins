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
import org.bukkit.event.server.PluginEnableEvent;
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
                        if(!data.contains(args[1])){
                            setBlockToAir(data, args);

                            addBlockToYml(data, regenBlock, args); // créer le regenBlock

                            sender.sendMessage(ChatColor.GREEN + "RegenBlock '" + args[1] + "' has been created!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "RegenBlock '" + args[1] + "' already exists!");
                        }

                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage /regenblock create {regenBlockName}");
                    }
                    return true;

                case "delete":
                    if (args.length > 1) {
                        if (data.contains(args[1])) {
                            setBlockToAir(data, args);

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

                case "move":
                    if (args.length > 1) {
                        if(data.contains(args[1])){
                            setBlockToAir(data, args);

                            addBlockToYml(data, regenBlock, args); // créer le regenBlock

                            sender.sendMessage(ChatColor.GREEN + "RegenBlock '" + args[1] + "' has been moved to your location!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "RegenBlock '" + args[1] + "' doesn't exists!");
                        }

                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage /regenblock move {regenBlockName}");
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
        String string;
        for (String key : Test1.getInstance().getConfig().getConfigurationSection("BlocksToGenerate").getKeys(false)) {
            string = Test1.getInstance().getConfig().getString("BlocksToGenerate." + key + ".block");

            if(event.getBlock().getType().equals(Material.matchMaterial(string))){
                FileConfiguration data = YamlConfiguration.loadConfiguration(new File("./plugins/data.yml"));

                for (String key2 : data.getKeys(false) ){
                    //We are getting every key from our config.yml file
                    ConfigurationSection l = data.getConfigurationSection(key2);
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
        }
    }

    public void randomChangeBlock(Location location){
        Random random = new Random();

        int indice = -1;

        for (String key : Test1.getInstance().getConfig().getConfigurationSection("BlocksToGenerate").getKeys(false)){
            indice++;
        }

        String[] blockName = new String[indice+1];
        int[] blockChance = new int[indice+1];
        int i = 0;

        for (String key : Test1.getInstance().getConfig().getConfigurationSection("BlocksToGenerate").getKeys(false)){
            blockName[i] = Test1.getInstance().getConfig().getString("BlocksToGenerate." + key + ".block");
            blockChance[i] = Test1.getInstance().getConfig().getInt("BlocksToGenerate." + key + ".block-chance");
            i++;
        }

        int valMax = 0;

        for(int i2 = 0; i2<blockChance.length; i2++){
            valMax += blockChance[i2];
        }

        String[] tabBlocks = new String[valMax];

        int val = 0;

        for(int indiceBlockName = 0; indiceBlockName < blockName.length; indiceBlockName++){
            for(int a = 0; a < blockChance[indiceBlockName]; a++){
                tabBlocks[val] = blockName[indiceBlockName];
                val++;
            }
        }

        int number = random.nextInt(valMax);

        location.getBlock().setType(Material.matchMaterial(tabBlocks[number]));

    }

    public void setBlockToAir(FileConfiguration data, String args[]){
        if (data.contains(args[1])) {
            Location delBlock = new Location(Bukkit.getWorld(data.getString(args[1] + ".world")), data.getInt(args[1] + ".x"),
                    data.getInt(args[1] + ".y"), data.getInt(args[1] + ".z"));

            delBlock.getBlock().setType(Material.AIR);
        }
    }

    public void addBlockToYml(FileConfiguration data, Location regenBlock, String args[]){
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
    }
}