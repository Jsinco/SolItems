package dev.jsinco.solitems.events

import dev.jsinco.solitems.manager.ItemManager
import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.Ability
import dev.jsinco.solitems.util.Util
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

/**
 * This class is used to listen for passive events
 */
class PassiveListeners(val plugin: SolItems) {

    companion object {
        val runnables: MutableMap<String, Int> = mutableMapOf()
    }

    fun startRunnable(name: String, delay: Delay) { // TODO: performance?
        runnables[name] = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            for (player in Bukkit.getOnlinePlayers()) {
                val datas: List<PersistentDataContainer> = Util.getAllEquipmentNBT(player)
                for (data in datas) {
                    for (customItem in ItemManager.customItems) {
                        if (data.has(NamespacedKey(plugin, customItem.key), PersistentDataType.SHORT)) {
                            val customItemClass = customItem.value
                            customItemClass.executeAbilities(Ability.RUNNABLE, player, 0)
                        }
                    }
                }
            }
        }, 0L, delay.time)
    }


}