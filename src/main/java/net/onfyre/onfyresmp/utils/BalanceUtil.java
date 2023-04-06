package net.onfyre.onfyresmp.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BalanceUtil {

    private static Material currency = Material.DIAMOND;
    private static Material currencyBlock = Material.DIAMOND_BLOCK;

    public static int countCurrency(Player player) {

        int currencyValue = 0;

        currencyValue = currencyValue + countInventory(player.getInventory());
        currencyValue = currencyValue + countInventory(player.getEnderChest());

        return currencyValue;
    }

    public static int countInventory(Inventory inventory) {
        int count = 0;
        for (ItemStack stack : inventory) {
            if (stack != null) {
                if (stack.getType() == currency) {
                    count = count + stack.getAmount();
                }
                if (stack.getType() == currencyBlock) {
                    count = count + stack.getAmount() * 9;
                }

                ItemMeta meta = stack.getItemMeta();
                if (meta instanceof BlockStateMeta blockStateMeta) {
                    BlockState blockState = blockStateMeta.getBlockState();
                    if (blockState instanceof ShulkerBox box) {
                        for (ItemStack shulkerStack : box.getInventory()) {
                            if (shulkerStack != null) {
                                if (shulkerStack.getType() == currency) {
                                    count = count + shulkerStack.getAmount();
                                }

                                if (shulkerStack.getType() == currencyBlock) {
                                    count = count + shulkerStack.getAmount() * 9;
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
}
