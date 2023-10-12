package dev.jsinco.solitems.items.armor

import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class NeonBootsItem : CustomItem {
    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#fbf334&lN&#e5e04d&le&#cecd66&lo&#b8b97f&ln &#a1a699&lB&#8b93b2&lo&#7480cb&lo&#5e6ce4&lt&#4759fd&ls",
            mutableListOf("&#4759fdF&#7a5bd2a&#ae5ca7s&#e15e7ct &#fc767cL&#fda4a8a&#fed1d3n&#ffffffe"),
            mutableListOf("No lore yet"),
            Material.NETHERITE_BOOTS,
            mutableListOf("neonboots"),
            mutableMapOf(Enchantment.MENDING to 1)
        )
        return Pair("neonboots", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        when (type) {
            Ability.PLAYER_CROUCH ->{
                //player.velocity = player.
            }
            else -> return false
        }
        return true
    }
}