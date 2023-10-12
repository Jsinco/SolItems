package dev.jsinco.solitems.candles

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.util.Util
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class CreateCandle (
    private val name: String,
    private val lore: MutableList<String>,
    private val material: Material,
    private val persistentData: MutableList<String>,
) {

    companion object {
        private val plugin: SolItems = SolItems.getPlugin()
    }

    var tier: List<String> = listOf(
        "&#EEE1D5&m       &r&#EEE1D5⋆⁺₊⋆ ★ ⋆⁺₊⋆&m       ",
        "&#EEE1D5Tier • &#ffb89c&lC&#ffb8a8&le&#ffb8b3&ll&#ffb8bf&le&#ffb9ca&ls&#ffb9d6&lt&#ffb9e1&li&#ffb9ed&la&#ffb9f8&ll",
        "&#EEE1D5&m       &r&#EEE1D5⋆⁺₊⋆ ★ ⋆⁺₊⋆&m       ")

    fun getItem(): ItemStack {
        val candle = ItemStack(material)
        val meta = candle.itemMeta!!

        meta.setDisplayName(Util.colorcode(name))
        lore.add("")
        lore.addAll(tier)
        meta.lore = Util.colorcodeList(lore)

        meta.addEnchant(Enchantment.DURABILITY, 10, true)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        meta.persistentDataContainer.set(NamespacedKey(plugin, "candle"), PersistentDataType.SHORT, 1)
        for (data in persistentData) {
            meta.persistentDataContainer.set(NamespacedKey(plugin, data), PersistentDataType.SHORT, 1)
        }

        candle.itemMeta = meta
        return candle
    }
}