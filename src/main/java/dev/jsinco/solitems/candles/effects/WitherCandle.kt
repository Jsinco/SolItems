package dev.jsinco.solitems.candles.effects

import dev.jsinco.solitems.candles.CreateCandle
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class WitherCandle : CustomItem {
    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateCandle(
            "&#67767e&lW&#617077&li&#5a6970&lt&#546369&lh&#4d5c62&le&#47565b&lr &#E2E2E2Candle",
            mutableListOf("§fChance to wither enemies","§fwhen attacking"),
            Material.BLACK_CANDLE,
            mutableListOf("wither")
        )
        return Pair("wither", item.getItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        //val entity
        when (type) {
            Ability.ENTITY_DAMAGE -> {
                //if (Random().nextInt(75) <= 2) {
                   // e
                //}
            }
            else -> return false
        }
        return true
    }
}