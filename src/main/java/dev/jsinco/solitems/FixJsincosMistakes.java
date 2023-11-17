package dev.jsinco.solitems;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FixJsincosMistakes implements CommandExecutor {

    private final SolItems plugin;

    public FixJsincosMistakes(SolItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "deoriumcutlass"), PersistentDataType.SHORT)) {
            if (meta.hasEnchant(Enchantment.SILK_TOUCH)) {
                meta.removeEnchant(Enchantment.SILK_TOUCH);
                meta.addEnchant(Enchantment.SWEEPING_EDGE, 4, true);
                item.setItemMeta(meta);
            }
        } else {
            fixStellarItems(item, player);
        }

        return true;
    }


    private void fixStellarItems(ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : null;


        if (lore == null) return;
        for (String string : lore) {
            String line = ChatColor.stripColor(string);
            if (line.contains("Tier") && line.contains("Stellar")) {
                meta.getPersistentDataContainer().set(new NamespacedKey(SolItems.getPlugin(), "stellar"), PersistentDataType.SHORT, (short) 1);
                if (meta.hasEnchant(Enchantment.SILK_TOUCH) && meta.hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
                    meta.removeEnchant(Enchantment.SILK_TOUCH);
                }
                item.setItemMeta(meta);
                player.getInventory().setItemInMainHand(item);
                return;
            }
        }
    }
}
