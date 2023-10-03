package test1.test1.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import test1.test1.Test1;

public class ReplaceCrop implements Listener {
    public ReplaceCrop(Test1 plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCropBreak(BlockBreakEvent event) {
        switch (event.getPlayer().getItemInHand().getType()) {
            case WOODEN_HOE:
            case STONE_HOE:
            case GOLDEN_HOE:
            case IRON_HOE:
            case DIAMOND_HOE:
            case NETHERITE_HOE:
                if (event.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                    switch (event.getBlock().getType()) {
                        case BEETROOTS:
                            placeSeed(event, Material.BEETROOT_SEEDS, Material.BEETROOTS);
                            break;
                        case WHEAT:
                            placeSeed(event, Material.WHEAT_SEEDS, Material.WHEAT);
                            break;
                        case CARROTS:
                            placeSeed(event, Material.CARROT, Material.CARROTS);
                            break;
                        case POTATOES:
                            placeSeed(event, Material.POTATO, Material.POTATOES);
                            break;
                        case NETHER_WART:
                            placeSeed(event, Material.NETHER_WART, Material.NETHER_WART);
                            break;
                        default:
                            break;
                    }
                }
                break;
        }
    }

    public void placeSeed(BlockBreakEvent event, Material material, Material replace) {

        Inventory inv = event.getPlayer().getInventory();

        if (inv.contains(material)) {
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack item = inv.getItem(i);
                if (item != null && item.getType().equals(material)) {
                    int itemCount = item.getAmount();

                    if (itemCount > 1) {
                        item.setAmount(itemCount - 1);
                    } else {
                        inv.setItem(i, null);
                    }

                    event.getPlayer().updateInventory();
                    break;
                }
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    event.getBlock().setType(replace);
                }
            }.runTaskLater(Test1.getInstance(), 2);
        }
    }
}
