package dev.jsinco.solitems.items.misc

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class DarkWickShieldItem : CustomItem {

    companion object {
        private val plugin: SolItems = SolItems.getPlugin()
        private val cooldown: MutableList<UUID> = mutableListOf()
    }

    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#7f815d&lD&#8e8f60&la&#9d9c63&lr&#acaa67&lk &#bbb86a&lW&#cac56d&li&#d9d370&lc&#e1db71&lk &#e0de6e&lS&#dfe16c&lh&#dee369&li&#dde667&le&#dce864&ll&#dbeb62&ld",
            mutableListOf("&#e1da72C&#e3da6fo&#e5da6cu&#e7da6an&#e8db67t&#eadb64d&#ecdb61o&#eedb5ew&#f0db5cn &#f2db59L&#f4db56i&#f6db53g&#f7dc50h&#f9dc4et&#fbdc4be&#fddc48r"),
            mutableListOf("While blocking with this shield,", "hold sneak to charge up a", "powerful explosion", "", "&cCooldown: 15 secs"),
            Material.SHIELD,
            mutableListOf("darkwickshield"),
            mutableMapOf(Enchantment.DURABILITY to 10, Enchantment.MENDING to 1, Enchantment.THORNS to 4, Enchantment.FIRE_ASPECT to 5)
        )
        item.tier = "&#c46bfb&lH&#c86eee&la&#cd71e2&ll&#d174d5&ll&#d677c8&lo&#da7abc&lm&#de7daf&la&#e380a2&lr&#e78395&le&#eb8689&ls &#f0897c&l2&#f48c6f&l0&#f98f63&l2&#fd9256&l3"
        return Pair("darkwickshield", item.createItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        when (type) {
            Ability.RIGHT_CLICK -> {
                countdownLighter(player)
            }
            else -> return false
        }
        return true
    }


    private fun countdownLighter(player: Player) {
        if (cooldown.contains(player.uniqueId) || !player.isSneaking) return


        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            if (!player.isSneaking) return@scheduleSyncDelayedTask
            player.world.createExplosion(player.location, 7f, false, false, player)
            player.world.spawnParticle(Particle.FLAME, player.location, 50, 0.5, 0.5, 0.5, 0.8)
            player.world.spawnParticle(Particle.SOUL_FIRE_FLAME, player.location, 50, 0.5, 0.5, 0.5, 0.8)
            cooldown(player.uniqueId)
        },40)
    }

    private fun cooldown(uuid: UUID) {
        cooldown.add(uuid)
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            cooldown.remove(uuid)
        }, 300) // 600
    }
}