package dev.jsinco.solitems.items.misc

import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class FrakenshearsItem : CustomItem {
    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#79a617&lF&#749d18&lr&#709319&la&#6b8a1a&ln&#66811b&lk&#61771c&le&#5d6e1e&ln&#58651f&ls&#535b20&lh&#4e5221&le&#4a4922&la&#453f23&lr&#403624&ls",
            mutableListOf("&#79a617S&#6a9918k&#5b8b19u&#4c7e1al&#3d701bl &#2e631cC&#31651dr&#46771eu&#5c891fs&#729b20h&#87ad20e&#9dbf21r"),
            mutableListOf("This tool allows the user", "to break any head instantly"),
            Material.SHEARS,
            mutableListOf("frakenshears"),
            mutableMapOf(Enchantment.DIG_SPEED to 6, Enchantment.DURABILITY to 7, Enchantment.MENDING to 1)
        )
        item.tier = "&#c46bfb&lH&#c86eee&la&#cd71e2&ll&#d174d5&ll&#d677c8&lo&#da7abc&lm&#de7daf&la&#e380a2&lr&#e78395&le&#eb8689&ls &#f0897c&l2&#f48c6f&l0&#f98f63&l2&#fd9256&l3"
        return Pair("frakenshears", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        val interact: PlayerInteractEvent? = event as? PlayerInteractEvent

        when (type) {
            Ability.LEFT_CLICK -> {
                val block = interact!!.clickedBlock ?: return false
                if (block.toString().contains("HEAD")) {
                    block.breakNaturally()
                    block.world.spawnParticle(Particle.BLOCK_DUST, block.location.add(0.5, 0.5, 0.5), 10, 0.5, 0.5, 0.5, 0.1, block.blockData)
                }
            }
            else -> return false
        }
        return true
    }

}