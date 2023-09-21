package test1.test1.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import test1.test1.Test1;

public class PlayerHandler implements Listener {
    public PlayerHandler(Test1 plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        Inventory inv = player.getInventory();

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("TEST");
        item.setItemMeta(meta);

        inv.addItem(item);
        inv.setItem(8, item);
    }

}
