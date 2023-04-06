package net.onfyre.onfyresmp.listeners;

import net.onfyre.onfyresmp.Main;
import net.onfyre.onfyresmp.commands.admin.KeysCommand;
import net.onfyre.onfyresmp.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class InteractListener implements Listener {

    public ArrayList<Player> abilityUsed = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if(block != null && isShulker(block)) {
            if(block.getType().equals(Material.RED_SHULKER_BOX) && block.getLocation().distance(new Location(Bukkit.getWorld("world"), 0, 280, 0)) <= 50) {
                if(event.getItem() != null && player.getInventory().containsAtLeast(KeysCommand.starterKey, 1)) {
                    event.setCancelled(false);
                    player.sendMessage(Main.getPrefix() + "Du hast eine Crate geöffnet!");
                    remove(player.getInventory(), Material.TRIPWIRE_HOOK, KeysCommand.starterKey.getItemMeta().getDisplayName(), 1);
                    ShulkerBox shulkerBox = (ShulkerBox) block.getState();
                    Inventory inv = shulkerBox.getInventory();
                    inv.clear();
                    inv.setItem(10, new ItemBuilder(Material.IRON_SWORD).build());
                    inv.setItem(11, new ItemBuilder(Material.IRON_AXE).build());
                    inv.setItem(12, new ItemBuilder(Material.IRON_PICKAXE).build());
                    inv.setItem(13, new ItemBuilder(Material.SHIELD).build());
                    inv.setItem(14, new ItemBuilder(Material.IRON_HELMET).build());
                    inv.setItem(15, new ItemBuilder(Material.IRON_CHESTPLATE).build());
                    inv.setItem(16, new ItemBuilder(Material.COOKED_BEEF).setAmount(64).build());
                } else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Du brauchst einen Key um diese Crate zu öffnen!");
                }
            }

            if(block.getType().equals(Material.LIME_SHULKER_BOX) && block.getLocation().distance(new Location(Bukkit.getWorld("world"), 0, 280, 0)) <= 50) {
                if(event.getItem() != null && player.getInventory().containsAtLeast(KeysCommand.rewardKey, 1)) {
                    event.setCancelled(false);
                    player.sendMessage(Main.getPrefix() + "Du hast eine Crate geöffnet!");
                    remove(player.getInventory(), Material.TRIPWIRE_HOOK, KeysCommand.rewardKey.getItemMeta().getDisplayName(), 1);
                    ShulkerBox shulkerBox = (ShulkerBox) block.getState();
                    Inventory inv = shulkerBox.getInventory();
                    inv.clear();
                    inv.setItem(10, new ItemBuilder(Material.DIAMOND).setAmount(16).build());
                    inv.setItem(11, new ItemBuilder(Material.EMERALD).setAmount(64).build());
                    inv.setItem(12, new ItemBuilder(Material.IRON_INGOT).setAmount(32).build());
                    inv.setItem(13, new ItemBuilder(Material.COAL).setAmount(32).build());
                    inv.setItem(14, new ItemBuilder(Material.COOKED_BEEF).setAmount(64).build());
                } else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Du brauchst einen Key um diese Crate zu öffnen!");
                }
            }

            if(block.getType().equals(Material.PURPLE_SHULKER_BOX) && block.getLocation().distance(new Location(Bukkit.getWorld("world"), 0, 280, 0)) <= 50) {
                if(event.getItem() != null && player.getInventory().containsAtLeast(KeysCommand.pvpKey, 1)) {
                    event.setCancelled(false);
                    player.sendMessage(Main.getPrefix() + "Du hast eine Crate geöffnet!");
                    remove(player.getInventory(), Material.TRIPWIRE_HOOK, KeysCommand.pvpKey.getItemMeta().getDisplayName(), 1);
                    ShulkerBox shulkerBox = (ShulkerBox) block.getState();
                    Inventory inv = shulkerBox.getInventory();
                    inv.clear();
                    inv.setItem(10, new ItemBuilder(Material.END_CRYSTAL).setAmount(64).build());
                    inv.setItem(11, new ItemBuilder(Material.OBSIDIAN).setAmount(64).build());
                    inv.setItem(12, new ItemBuilder(Material.GOLDEN_APPLE).setAmount(16).build());
                    inv.setItem(13, new ItemBuilder(Material.RESPAWN_ANCHOR).setAmount(16).build());
                    inv.setItem(14, new ItemBuilder(Material.GLOWSTONE).setAmount(64).build());
                    inv.setItem(15, new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE).setAmount(4).build());
                    inv.setItem(16, new ItemBuilder(Material.TOTEM_OF_UNDYING).build());
                } else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Du brauchst einen Key um diese Crate zu öffnen!");
                }
            }

            if(block.getType().equals(Material.BLACK_SHULKER_BOX) && block.getLocation().distance(new Location(Bukkit.getWorld("world"), 0, 280, 0)) <= 50) {
                if(event.getItem() != null && player.getInventory().containsAtLeast(KeysCommand.magicKey, 1)) {
                    event.setCancelled(false);
                    player.sendMessage(Main.getPrefix() + "Du hast eine Crate geöffnet!");
                    remove(player.getInventory(), Material.TRIPWIRE_HOOK, KeysCommand.magicKey.getItemMeta().getDisplayName(), 1);
                    ShulkerBox shulkerBox = (ShulkerBox) block.getState();
                    Inventory inv = shulkerBox.getInventory();
                    inv.clear();
                    if(player.getWorld().getTime() % 2 == 0) {
                        inv.setItem(10, new ItemBuilder(Material.NETHERITE_SWORD).addEnchantment(Enchantment.DAMAGE_ALL,5).addEnchantment(Enchantment.LOOT_BONUS_MOBS, 3).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
                        inv.setItem(11, new ItemBuilder(Material.NETHERITE_AXE).addEnchantment(Enchantment.DAMAGE_ALL,5).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.DIG_SPEED, 5).build());
                        inv.setItem(12, new ItemBuilder(Material.NETHERITE_PICKAXE).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.DIG_SPEED, 5).build());
                        inv.setItem(13, new ItemBuilder(Material.NETHERITE_SHOVEL).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.DIG_SPEED, 5).build());
                    } else {
                        inv.setItem(10, new ItemBuilder(Material.NETHERITE_HELMET).addEnchantment(Enchantment.THORNS, 3).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.WATER_WORKER, 1 ).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                        inv.setItem(11, new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchantment(Enchantment.THORNS, 3).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                        inv.setItem(12, new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchantment(Enchantment.THORNS, 3).addEnchantment(Enchantment.SWIFT_SNEAK, 3).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                        inv.setItem(13, new ItemBuilder(Material.NETHERITE_BOOTS).addEnchantment(Enchantment.THORNS, 3).addEnchantment(Enchantment.SOUL_SPEED, 3).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                    }

                    inv.setItem(14, new ItemBuilder(Material.TRIDENT).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.RIPTIDE, 3).build());
                    inv.setItem(15, new ItemBuilder(Material.TOTEM_OF_UNDYING).build());
                    inv.setItem(16, new ItemBuilder(Material.TOTEM_OF_UNDYING).build());
                } else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Du brauchst einen Key um diese Crate zu öffnen!");
                }
            }

            if(block.getType().equals(Material.YELLOW_SHULKER_BOX) && block.getLocation().distance(new Location(Bukkit.getWorld("world"), 0, 280, 0)) <= 50) {
                if(event.getItem() != null && player.getInventory().containsAtLeast(KeysCommand.godKey, 1)) {
                    event.setCancelled(false);
                    player.sendMessage(Main.getPrefix() + "Du hast eine Crate geöffnet!");
                    remove(player.getInventory(), Material.TRIPWIRE_HOOK, KeysCommand.godKey.getItemMeta().getDisplayName(), 1);
                    ShulkerBox shulkerBox = (ShulkerBox) block.getState();
                    Inventory inv = shulkerBox.getInventory();
                    inv.clear();
                    inv.setItem(3, new ItemBuilder(Material.TOTEM_OF_UNDYING).build());
                    inv.setItem(4, new ItemBuilder(Material.TOTEM_OF_UNDYING).build());
                    inv.setItem(5, new ItemBuilder(Material.TOTEM_OF_UNDYING).build());
                    inv.setItem(9, new ItemBuilder(Material.NETHERITE_SWORD).addEnchantment(Enchantment.DAMAGE_ALL,5).addEnchantment(Enchantment.LOOT_BONUS_MOBS, 3).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
                    inv.setItem(10, new ItemBuilder(Material.NETHERITE_AXE).addEnchantment(Enchantment.DAMAGE_ALL,5).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.DIG_SPEED, 5).build());
                    inv.setItem(11, new ItemBuilder(Material.NETHERITE_PICKAXE).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.DIG_SPEED, 5).build());
                    inv.setItem(12, new ItemBuilder(Material.NETHERITE_SHOVEL).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.DIG_SPEED, 5).build());
                    inv.setItem(13, new ItemBuilder(Material.ELYTRA).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
                    inv.setItem(14, new ItemBuilder(Material.NETHERITE_HELMET).addEnchantment(Enchantment.THORNS, 3).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.WATER_WORKER, 1 ).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                    inv.setItem(15, new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchantment(Enchantment.THORNS, 3).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                    inv.setItem(16, new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchantment(Enchantment.THORNS, 3).addEnchantment(Enchantment.SWIFT_SNEAK, 3).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                    inv.setItem(17, new ItemBuilder(Material.NETHERITE_BOOTS).addEnchantment(Enchantment.THORNS, 3).addEnchantment(Enchantment.SOUL_SPEED, 3).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                    inv.setItem(21, new ItemBuilder(Material.TOTEM_OF_UNDYING).build());
                    inv.setItem(22, new ItemBuilder(Material.TOTEM_OF_UNDYING).build());
                    inv.setItem(23, new ItemBuilder(Material.TOTEM_OF_UNDYING).build());
                } else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Du brauchst einen Key um diese Crate zu öffnen!");
                }
            }
        } else if(event.getAction().isRightClick() && event.getItem() != null && event.getItem().getType().equals(Material.NETHERITE_SWORD)) {
            String itemName = event.getItem().getItemMeta().getDisplayName();
            if(itemName.equals("Dasher")) {
                if(!abilityUsed.contains(player)) {
                    player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 20);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 100, 1);
                    player.setVelocity(player.getFacing().getDirection().multiply(1));
                    abilityUsed.add(player);
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () ->  {
                        for(Entity entity : player.getNearbyEntities(1.5, 0, 1.5)) {
                            if (entity instanceof LivingEntity livingEntity) {
                                livingEntity.damage(8);
                                livingEntity.setVelocity(player.getFacing().getDirection().multiply(1.5));
                            }
                        }
                    }, 10L);
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> abilityUsed.remove(player), 30L);
                } else {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                }
            } else if(itemName.equals("Slayer")) {
                if(!abilityUsed.contains(player)) {
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 100, 0.5F);
                    for(Entity entity : player.getNearbyEntities(5, 3, 5)) {
                        if (entity instanceof LivingEntity livingEntity) {
                            player.getWorld().spawnParticle(Particle.LAVA, livingEntity.getLocation().add(0, 1, 0), 20);
                            livingEntity.damage(6);
                            livingEntity.setVelocity(player.getFacing().getDirection().multiply(0.5));
                        }
                    }
                    abilityUsed.add(player);
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> abilityUsed.remove(player), 30L);
                } else {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                }
            } else {

            }
        }
    }

    private boolean isShulker(Block block) {
        return block.getState() instanceof ShulkerBox;
    }

    public void remove(Inventory inv, Material mat, String name , int amount) {
        for(int i = 0; i < inv.getSize(); i++){
            ItemStack stack = inv.getItem(i);
            if(stack != null && stack.getType().equals(mat) && stack.getItemMeta().getDisplayName().equals(name)) {
                int value = stack.getAmount() - amount;
                stack.setAmount(value);
                inv.setItem(i, value > 0 ? stack : null);
                break;
            }
        }
    }
}
