package dev.jsinco.solitems.candles.effects

import dev.jsinco.solitems.candles.CreateCandle
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class WitherCandle : CustomItem {
    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateCandle(
            "&#67767e&lW&#617077&li&#5a6970&lt&#546369&lh&#4d5c62&le&#47565b&lr &#E2E2E2Candle",
            "&#67767eW&#617077i&#5a6970t&#546369h&#4d5c62e&#47565br",
            mutableListOf("§fChance to wither enemies","§fwhen attacking"),
            Material.BLACK_CANDLE,
            mutableListOf("wither")
        )
        return Pair("wither", item.getItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        val entityDamageEvent: EntityDamageByEntityEvent? = event as? EntityDamageByEntityEvent
        when (type) {
            Ability.ENTITY_DAMAGE -> {
                if (Random.nextInt(75) <= 2) {
                    val entity: LivingEntity = entityDamageEvent!!.entity as? LivingEntity ?: return false
                    entity.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.WITHER, 100, 1, false, true, true))
                }
            }
            else -> return false
        }
        return true
    }
}