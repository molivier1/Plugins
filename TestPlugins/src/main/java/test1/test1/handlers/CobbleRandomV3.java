package test1.test1.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import test1.test1.Test1;
import java.util.Random;

public class CobbleRandomV3 implements Listener {
    public CobbleRandomV3(Test1 plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        Location regenBlock = event.getPlayer().getLocation();


        if(event.getBlock().getLocation().equals(regenBlock)){
            World world = player.getWorld();
            Location location = event.getBlock().getLocation();

            new BukkitRunnable() {
                @Override
                public void run() {
                    randomChangeBlock(location);
                }
            }.runTaskLater(Test1.getInstance(), 2);

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
