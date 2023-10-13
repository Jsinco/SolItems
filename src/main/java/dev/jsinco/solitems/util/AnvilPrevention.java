package dev.jsinco.solitems.util;

import dev.jsinco.solitems.SolItems;
import dev.jsinco.solitems.manager.ItemManager;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


/**
 * Class to prevent certain custom items from being used
 * in anvils
 */
public class AnvilPrevention implements Listener {

    public SolItems plugin;

    public AnvilPrevention(SolItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().getType().equals(InventoryType.ANVIL) || event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) {
            return;
        }

        ItemMeta meta = event.getCurrentItem().getItemMeta();
        boolean cancelEvent = false;

        if (meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "forged"), PersistentDataType.SHORT)) {
            cancelEvent = true;
        } else { // TODO: Convert to JVM Static?
            for (String key : ItemManager.Companion.getCustomItems().keySet()) {
                if (meta.getPersistentDataContainer().has(new NamespacedKey(plugin, key), PersistentDataType.SHORT)) {
                    cancelEvent = true;
                    break;
                }
            }
        }

        if (cancelEvent) {
            event.setCancelled(true);
        }
    }
}
