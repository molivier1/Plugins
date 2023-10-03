package test1.test1.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import test1.test1.Test1;

import java.io.IOException;
import java.util.Random;

public class BetterFurnace implements CommandExecutor, Listener {
    public BetterFurnace(Test1 plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void furnaceSpeed(FurnaceStartSmeltEvent event) {
        FileConfiguration dataFurnace = Test1.getInstance().getFurnaceConfig();

        for (String key : dataFurnace.getKeys(false)) {
            Location location = new Location(Bukkit.getWorld(dataFurnace.getString(key + ".world")), dataFurnace.getInt(key + ".x"),
                    dataFurnace.getInt(key + ".y"), dataFurnace.getInt(key + ".z"));
            if (event.getBlock().getLocation().equals(location)) {
                int newTime = (int) (event.getTotalCookTime() * 0.1);
                event.setTotalCookTime(newTime);
            }
        }
    }

    @EventHandler
    public void furnaceMultiplier(FurnaceSmeltEvent event) {
        FileConfiguration dataFurnace = Test1.getInstance().getFurnaceConfig();

        for (String key : dataFurnace.getKeys(false)) {
            Location location = new Location(Bukkit.getWorld(dataFurnace.getString(key + ".world")), dataFurnace.getInt(key + ".x"),
                    dataFurnace.getInt(key + ".y"), dataFurnace.getInt(key + ".z"));
            if (event.getBlock().getLocation().equals(location)) {
                ItemStack itemResult = event.getResult();

                Random random = new Random();

                int val = random.nextInt(2) + 1;

                itemResult.setAmount(itemResult.getAmount() * val);
            }
        }
    }

    @EventHandler
    public void interactFurnace(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.FURNACE && event.getPlayer().getItemInHand().getType() == Material.STICK) {
            Block furnace = event.getClickedBlock();

            event.setCancelled(true);

            Player player = event.getPlayer();

            FileConfiguration dataFurnace = Test1.getInstance().getFurnaceConfig();

            switch (event.getAction().toString()) {
                case "RIGHT_CLICK_BLOCK":
                    if (!dataFurnace.contains(player.getUniqueId().toString())) {
                        dataFurnace.set(player.getUniqueId() + ".world", player.getWorld().getName());
                        dataFurnace.set(player.getUniqueId() + ".x", furnace.getX());
                        dataFurnace.set(player.getUniqueId() + ".y", furnace.getY());
                        dataFurnace.set(player.getUniqueId() + ".z", furnace.getZ());

                        try {
                            dataFurnace.save("./plugins/Test1/furnace.yml");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        player.sendMessage(ChatColor.GREEN + "Furnace upgraded!");
                    } else {
                        player.sendMessage(ChatColor.RED + "You already have an upgraded furnace!");
                    }

                    break;

                case "LEFT_CLICK_BLOCK":
                    if (dataFurnace.contains(player.getUniqueId().toString())) {
                        dataFurnace.set(player.getUniqueId().toString(), null);
                        try {
                            dataFurnace.save("./plugins/Test1/furnace.yml");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        player.sendMessage(ChatColor.GREEN + "Upgraded furnace for player " + player.getName() + " cleared!");
                    } else {
                        player.sendMessage(ChatColor.RED + "This furnace is not upgraded or is already taken!");
                    }

                    break;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "reload":
                    Test1.getInstance().reloadFurnaceConfig();
                    sender.sendMessage(ChatColor.GREEN + "Furnace file successfully reloaded!");
                    break;
                case "clear":
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
                        return true;
                    }
                    Player player = (Player) sender;
                    Test1.getInstance().getFurnaceConfig().set(player.getUniqueId().toString(), null);
                    try {
                        Test1.getInstance().getFurnaceConfig().save("./plugins/Test1/furnace.yml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sender.sendMessage(ChatColor.GREEN + "Upgraded furnace for player " + player.getName() + " cleared!");
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Usage /betterfurnace clear/reload");
                    break;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage /betterfurnace clear/reload");
        }

        return true;
    }
}
