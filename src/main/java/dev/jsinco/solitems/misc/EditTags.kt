package dev.jsinco.solitems.misc

import dev.jsinco.solitems.items.CreateItem
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.Listener

/**
 * Used for rename and re-lore tag. Consider moving to SolUtilities?
 */
class EditTags : Listener {

    val renameTag = CreateItem(
        "&#a8ff92&lItem Rename &#E2E2E2Tag",
        mutableListOf(),
        mutableListOf("&7Right click to use!"),
        Material.NAME_TAG,
        mutableListOf("renametag"),
        mutableMapOf(Enchantment.DURABILITY to 10)
    )




}