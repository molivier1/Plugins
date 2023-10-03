package test1.test1.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Furnace;
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
import java.util.Objects;
import java.util.Random;

public class BetterFurnace implements CommandExecutor, Listener {
    public BetterFurnace(Test1 plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void furnaceSpeed(FurnaceStartSmeltEvent event){
        FileConfiguration dataFurnace = Test1.getInstance().getFurnaceConfig();

        System.out.println(event.getBlock());

        /*for (String key : Test1.getInstance().getFurnaceConfig().getConfigurationSection("furnace").getKeys(false)){

        }*/


        int newTime = (int) (event.getTotalCookTime() * 0.1);
        event.setTotalCookTime(newTime);
    }

    @EventHandler
    public void furnaceMultiplier(FurnaceSmeltEvent event){
        ItemStack itemResult = event.getResult();

        Random random = new Random();

        int val = random.nextInt(2) + 1;

        itemResult.setAmount(itemResult.getAmount() * val);
    }

    @EventHandler
    public void addFurnace(PlayerInteractEvent event){
        if (event.getPlayer().getItemInHand().getType() == Material.STICK && event.getClickedBlock() != null){
            Block furnace = event.getClickedBlock();
            if (furnace.getType() == Material.FURNACE){
                Player player = event.getPlayer();

                FileConfiguration dataFurnace = Test1.getInstance().getFurnaceConfig();

                if (!dataFurnace.contains(player.getUniqueId().toString())){
                    dataFurnace.set(player.getUniqueId() + ".world", player.getWorld().getName());
                    dataFurnace.set(player.getUniqueId() + ".x", furnace.getX());
                    dataFurnace.set(player.getUniqueId() + ".y", furnace.getY());
                    dataFurnace.set(player.getUniqueId() + ".z", furnace.getZ());

                    try {
                        dataFurnace.save("./plugins/Test1/furnace.yml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args){
        if(args[0].equals("reload")){
            Test1.getInstance().reloadFurnaceConfig();
        }

        return true;
    }
}
