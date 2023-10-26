package dev.jsinco.solitems.items.armor

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.CreateItem
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import java.util.UUID

class ThunderStridesItem : CustomItem {

    companion object {
        val plugin: SolItems = SolItems.getPlugin()
        val directions: MutableMap<UUID, Vector> = mutableMapOf()

        val activeFastLane: MutableList<UUID> = mutableListOf()
        val cooldown: MutableList<UUID> = mutableListOf()
    }

    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateItem(
            "&#ffe800&lT&#e9e917&lh&#d3ea2e&lu&#bdec45&ln&#a7ed5c&ld&#91ee73&le&#7bef8a&lr &#67e198&lS&#56c49d&lt&#45a7a2&lr&#348aa7&li&#226cac&ld&#114fb1&le&#0032b6&ls",
            mutableListOf("&#70f096F&#60daa5a&#50c4b4s&#40aec3t &#3099d2L&#2083e1a&#106df0n&#0057ffe"),
            mutableListOf("&#ffe800\"&#f2e009S&#e6d813u&#d9d01cr&#cdc825g&#c0c02fe &#b4b838l&#a7b041i&#9ba84bk&#8ea054e &#82975dl&#758f66i&#698770g&#5c7f79h&#507782t&#436f8cn&#376795i&#2a5f9en&#1e57a8g&#114fb1\"","","Crouch to activate a speed boost", "during your boost, crouch to slide","","&cCooldown: 10 secs"),
            Material.NETHERITE_BOOTS,
            mutableListOf("thunderstrides"),
            mutableMapOf(Enchantment.PROTECTION_ENVIRONMENTAL to 7, Enchantment.PROTECTION_PROJECTILE to 7, Enchantment.PROTECTION_FALL to 8 , Enchantment.DURABILITY to 10 ,Enchantment.MENDING to 1)
        )
        item.tier = "&#c46bfb&lH&#c86eee&la&#cd71e2&ll&#d174d5&ll&#d677c8&lo&#da7abc&lm&#de7daf&la&#e380a2&lr&#e78395&le&#eb8689&ls &#f0897c&l2&#f48c6f&l0&#f98f63&l2&#fd9256&l3"
        return Pair("thunderstrides", item.createItem())
    }

    // TODO: Add particles

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {

        when (type) {
            Ability.PLAYER_CROUCH ->{
                if (player.isSneaking || cooldown.contains(player.uniqueId) || player.isFlying) return false

                if (activeFastLane.contains(player.uniqueId)) {
                    slideAbility(player)
                } else if (!activeFastLane.contains(player.uniqueId)) {
                    startFastLane(player)
                }
            }
            Ability.MOVE -> {
                if (!activeFastLane.contains(player.uniqueId)) return false
                val playerMoveEvent: PlayerMoveEvent = event as PlayerMoveEvent
                val vector = playerMoveEvent.to.clone().subtract(playerMoveEvent.from.clone()).toVector()
                directions[player.uniqueId] = vector
            }
            else -> return false
        }
        return true
    }

    private fun startCooldown(uuid: UUID) {
        cooldown.add(uuid)
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            cooldown.remove(uuid)
        }, 200L)
    }


    private fun startFastLane(player: Player) {
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 140, 2, false, false, false))
        activeFastLane.add(player.uniqueId)

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            stopFastLane(player)
        }, 140L)
    }

    private fun stopFastLane(player: Player) {
        activeFastLane.remove(player.uniqueId)
        startCooldown(player.uniqueId)
    }

    private fun slideAbility(player: Player) {
        if (player.hasMetadata("thunderstrides")) return
        player.velocity = directions[player.uniqueId]?.multiply(7.5)?.setY(-0.1) ?: return
        player.setMetadata("thunderstrides", FixedMetadataValue(plugin, true))
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            player.removeMetadata("thunderstrides", plugin)
        }, 10)
    }
}