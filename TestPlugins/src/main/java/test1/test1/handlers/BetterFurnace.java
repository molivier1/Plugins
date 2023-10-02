package test1.test1.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.inventory.ItemStack;
import test1.test1.Test1;

import java.util.Random;

public class BetterFurnace implements Listener {
    public BetterFurnace(Test1 plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void furnaceSpeed(FurnaceStartSmeltEvent event){
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
}
