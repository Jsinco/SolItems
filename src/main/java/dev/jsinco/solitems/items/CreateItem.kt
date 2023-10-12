package dev.jsinco.solitems.items

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.util.Util
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class CreateItem(
    private val name: String,
    private val customEnchants: MutableList<String>,
    private val lore: MutableList<String>,
    private val material: Material,
    private val persistentData: MutableList<String>,
    private val vanillaEnchants: MutableMap<Enchantment, Int>
) {

    companion object {
        private val plugin: SolItems = SolItems.getPlugin()
    }

    var tier: List<String> = listOf(
        "&#EEE1D5&m       &r&#EEE1D5⋆⁺₊⋆ ★ ⋆⁺₊⋆&m       ",
        "&#EEE1D5Tier • &#ffc8c8&lC&#ffcfc8&le&#ffd5c7&ll&#ffdcc7&le&#ffe3c7&ls&#ffe9c6&lt&#fff0c6&li&#fff6c5&la&#fffdc5&ll",
        "&#EEE1D5&m       &r&#EEE1D5⋆⁺₊⋆ ★ ⋆⁺₊⋆&m       ")

    var unbreakable: Boolean = false
    var hideEnchants: Boolean = false
    val attributeModifiers: MutableMap<Attribute, AttributeModifier> = mutableMapOf()

    fun createItem(): ItemStack { // TODO: Organize
        val item = ItemStack(material)
        val meta = item.itemMeta!!


        for (name in persistentData) {
            meta.persistentDataContainer.set(NamespacedKey(plugin, name), PersistentDataType.SHORT, 1)
        }
        meta.setDisplayName(Util.colorcode(name))
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ARMOR_TRIM, ItemFlag.HIDE_UNBREAKABLE)

        val combinedLore: MutableList<String> = mutableListOf()
        combinedLore.addAll(customEnchants)
        combinedLore.add("§")
        combinedLore.addAll(lore.map { "&f$it" })
        combinedLore.add("")
        combinedLore.addAll(tier)

        meta.lore = Util.colorcodeList(combinedLore)

        for (enchant in vanillaEnchants) {
            meta.addEnchant(enchant.key, enchant.value, true)
        }
        if (attributeModifiers.isNotEmpty()) {
            for (attributeModifier in attributeModifiers) {
                meta.addAttributeModifier(attributeModifier.key, attributeModifier.value)
            }
        }
        meta.isUnbreakable = unbreakable
        if (hideEnchants) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        item.itemMeta = meta

        return item
    }
}