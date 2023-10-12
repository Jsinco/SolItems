package dev.jsinco.solitems.events

import dev.jsinco.solitems.manager.ItemManager
import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.util.Util
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

/**
 * This class is used to listen for passive events
 */
class PassiveListeners(val plugin: SolItems) {

    // TODO: turn to abstract class and use a runnable manager?

    fun startMainRunnable() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            for (player in Bukkit.getOnlinePlayers()) {
                val datas: List<PersistentDataContainer> = Util.getAllEquipmentNBT(player)
                for (data in datas) {
                    for (customItem in ItemManager.customItems) {
                        if (!data.has(NamespacedKey(plugin, customItem.key), PersistentDataType.SHORT))  continue
                        val customItemClass = customItem.value
                        customItemClass.executeAbilities(Ability.RUNNABLE, player, 0)
                    }
                }
            }
        }, 0L, 40L)
    }
}