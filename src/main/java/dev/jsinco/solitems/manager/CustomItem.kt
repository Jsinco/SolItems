package dev.jsinco.solitems.manager

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.Ability
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface CustomItem {

    /**
     * Called at startup to initialize and create each custom item
     * @return The created item
     */
    fun createItem(): Pair<String, ItemStack>


    /**
     * Called when a listener detects a custom item being used
     * @param type The type of ability to execute
     * @return A boolean for return info
     */
    fun executeAbilities(type: Ability, player: Player, event: Any): Boolean
}