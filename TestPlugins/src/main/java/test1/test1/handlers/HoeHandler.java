package test1.test1.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import test1.test1.Test1;

public class HoeHandler implements Listener {
    public HoeHandler (Test1 plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /*@EventHandler
    public void onHoeUse(PlayerInteractEvent  event){
        Player player = event.getPlayer();

        if(event.getItem().getType() == Material.DIAMOND_HOE){
            player.sendMessage("Le test de l'event fonctionne");
            ItemStack item = new ItemStack(Material.NETHERITE_HOE, 1);
            Inventory inv = player.getInventory();

            item.addEnchantments(player.getItemInHand().getEnchantments());

            player.setItemInHand(null);

            inv.addItem(item);
        }

        return;
    }*/

}
