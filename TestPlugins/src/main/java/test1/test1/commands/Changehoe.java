package test1.test1.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Changehoe implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command.");
            return true;
        }

        Player player = (Player) sender;

        if(player.getItemInHand().getType() == Material.DIAMOND_HOE){
            player.sendMessage("Diamond hoe successfully turned into netherite!");
            ItemStack item = new ItemStack(Material.NETHERITE_HOE, 1);
            Inventory inv = player.getInventory();

            //item.addEnchantments(player.getItemInHand().getEnchantments());

            ItemMeta meta = player.getItemInHand().getItemMeta();

            item.setItemMeta(meta);

            player.setItemInHand(null);

            inv.addItem(item);
        } else {
            player.sendMessage("You need to have a diamond hoe to use this command!");
        }

        return true;
    }
}
