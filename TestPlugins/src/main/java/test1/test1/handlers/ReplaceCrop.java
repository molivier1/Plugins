package test1.test1.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import test1.test1.Test1;

public class ReplaceCrop implements Listener {
    public ReplaceCrop(Test1 plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCropBreak(BlockBreakEvent event){
        Player player = event.getPlayer();


        player.sendMessage(event.getBlock().getType().toString());


        if (player.getItemInHand().getType() == Material.DIAMOND_HOE && event.getBlock().getType() == Material.WHEAT){



            new BukkitRunnable() {
                @Override
                public void run() {

                    event.getBlock().setType(Material.WHEAT);

                }
            }.runTaskLater(Test1.getInstance(), 2);
        }
    }
}
