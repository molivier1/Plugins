package test1.test1.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import test1.test1.Test1;

public class TorchHandler implements Listener {
    public TorchHandler (Test1 plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onTorchPlace_Low(BlockPlaceEvent event){
        if (event.getBlock().getType() == Material.TORCH) {
            //event.getBlock().setType(Material.DIAMOND_BLOCK);
            event.setCancelled(true); // Cancel l'event (même les events normaux : eg. poser)
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onTorchPlace_Normal(BlockPlaceEvent event){
        /*Block block = event.getBlock();
        if(block.getType() != Material.TORCH) { // Si le block n'est pas une torche alors fin de la méthode
            return;
        }*/

        Bukkit.getLogger().info("Une torche a ete placee");

    }

}
