package test1.test1.handlers;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import test1.test1.Test1;
import java.util.Random;

public class CobbleRandom implements Listener {
    public CobbleRandom(Test1 plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCobbleBreak(BlockBreakEvent event){ // -81 70 396
        Player player = event.getPlayer();

        Location regenBlock = new Location(player.getWorld(), -81, 70, 396);

        //if(player.getGameMode() == GameMode.SURVIVAL)

        if (event.getBlock().getLocation().equals(regenBlock)) {

            player.sendMessage("La en gros je farm le bloc");

            World world = player.getWorld();
            Location location = event.getBlock().getLocation();
            event.setCancelled(true);

            switch (event.getBlock().getType()){
                case COBBLESTONE:
                    dropBlockItem(player, world, location, Material.COBBLESTONE);

                    randomChangeBlock(location);

                    break;

                case STONE:
                    dropMineralItem(player, world, location, Material.STONE);

                    randomChangeBlock(location);

                    break;

                case IRON_ORE:
                    dropMineralItem(player, world, location, Material.IRON_ORE);

                    randomChangeBlock(location);

                    break;
            }

        }
    }

    public void dropBlockItem(Player player, World world, Location location, Material material){
        if(checkPickaxe(player) > -1) {

            ItemStack item = new ItemStack(material, 1);

            world.dropItem(location, item);
        }
    }

    public void dropMineralItem(Player player, World world, Location location, Material material){
        if(checkPickaxe(player) > -1) {

            if(checkPickaxe(player) == 1){
                ItemStack item = new ItemStack(material, 1);

                world.dropItem(location, item);
            } else {
                Material mineral = blockToMineral(material);

                ItemStack item = new ItemStack(mineral, 1);

                world.dropItem(location, item);
            }

        }
    }

    public int checkPickaxe(Player player){
        int valeur = -1;

        switch (player.getItemInHand().getType()){
            case WOODEN_PICKAXE:
            case STONE_PICKAXE:
            case GOLDEN_PICKAXE:
            case IRON_PICKAXE:
            case DIAMOND_PICKAXE:
            case NETHERITE_PICKAXE:
                valeur = 0;
                if(player.getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)){
                    valeur = 1;
                }
                break;
        }

        return valeur;
    }

    public Material blockToMineral(Material material){
        Material mineral;
        switch (material){
            case STONE:
                mineral = Material.COBBLESTONE;
                return mineral;

            case IRON_ORE:
                mineral = Material.RAW_IRON;
                return mineral;

            default:
                mineral = Material.BEDROCK;
                return mineral;
        }
    }

    public void randomChangeBlock(Location location){
        Random random = new Random();

        int number = random.nextInt(3);

        switch (number){
            case 0:
                location.getBlock().setType(Material.COBBLESTONE);
                break;

            case 1:
                location.getBlock().setType(Material.STONE);
                break;

            case 2:
                location.getBlock().setType(Material.IRON_ORE);
                break;
        }
    }

}
