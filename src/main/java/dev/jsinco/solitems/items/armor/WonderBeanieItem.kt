package dev.jsinco.solitems.items.armor

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPotionEffectEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import java.util.UUID

class WonderBeanieItem : CustomItem {

    companion object {
        val plugin: SolItems = SolItems.getPlugin()
        private val recursionProtection: MutableList<UUID> = mutableListOf()
    }

    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#723d8a&lW&#715b85&lo&#71797f&ln&#70967a&ld&#6fb474&le&#6ed26f&lr &#75db79&lB&#82cf92&le&#8fc3ac&la&#9cb7c5&ln&#a9abdf&li&#b69ff8&le",
            mutableListOf("&#723d8aC&#715486a&#716c81u&#70837dl&#709b79d&#6fb275r&#6fca70o&#6ee16cn"),
            mutableListOf("&#723d8a\"&#7c4b9aI &#8559a9f&#8f67b9e&#9975c9e&#a383d9l &#ac91e8d&#b69ff8i&#aca8e4z&#a1b2d0z&#97bbbcy&#8dc5a8.&#83ce94.&#78d880.&#6ee16c\"","","Wearing this beanie amplifies", "all potion effects you receive"),
            Material.NETHERITE_HELMET,
            mutableListOf("wonderbeanie"),
            mutableMapOf(Enchantment.DURABILITY to 9, Enchantment.PROTECTION_ENVIRONMENTAL to 7, Enchantment.OXYGEN to 4, Enchantment.WATER_WORKER to 1, Enchantment.PROTECTION_EXPLOSIONS to 5)
        )
        item.tier = "&#c46bfb&lH&#c86eee&la&#cd71e2&ll&#d174d5&ll&#d677c8&lo&#da7abc&lm&#de7daf&la&#e380a2&lr&#e78395&le&#eb8689&ls &#f0897c&l2&#f48c6f&l0&#f98f63&l2&#fd9256&l3"
        return Pair("wonderbeanie", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        when (type) {
            Ability.POTION_EFFECT -> {
                if (recursionProtection.contains(player.uniqueId)) return false
                else if (player.equipment.helmet == null || !player.equipment.helmet.itemMeta.persistentDataContainer.has(NamespacedKey(plugin, "wonderbeanie"), PersistentDataType.SHORT)) return false

                event as EntityPotionEffectEvent
                val effect = event.newEffect ?: return false
                event.isCancelled = true

                recursionProtection.add(player.uniqueId)
                player.addPotionEffect(PotionEffect(effect.type, effect.duration, (effect.amplifier + 1), effect.isAmbient, effect.hasParticles(), effect.hasIcon()))
                recursionProtection.remove(player.uniqueId)
            }
            else -> return false
        }
        return true
    }
}