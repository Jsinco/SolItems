package dev.jsinco.solitems.events

import dev.jsinco.solitems.manager.ItemManager
import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.Ability
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

/**
 * This class is used to listen for passive events
 */
class PassiveListeners(val plugin: SolItems) {

    companion object {
        var customItemsRunnable: Int = 0
    }

    fun startCustomItemsRunnable() { // TODO: performance?
        customItemsRunnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            for (player in Bukkit.getOnlinePlayers()) {
                for (item in player.inventory.contents) {
                    if (item == null || !item.hasItemMeta()) continue

                    val data = item.itemMeta!!.persistentDataContainer
                    for (customItem in ItemManager.customItems) {
                        if (data.has(NamespacedKey(plugin, customItem.key), PersistentDataType.SHORT)) {
                            val customItemClass = customItem.value
                            customItemClass.executeAbilities(Ability.RUNNABLE, player, 0)
                        }
                    }
                }
            }
        }, 0L, 40L)
    }


}