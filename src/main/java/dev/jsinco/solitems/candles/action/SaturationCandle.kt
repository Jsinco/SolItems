package dev.jsinco.solitems.candles.action

import dev.jsinco.solitems.candles.CreateCandle
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class SaturationCandle : CustomItem {
    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateCandle(
            "&#fbd779&lS&#fbd47a&la&#fbd27a&lt&#fbcf7b&lu&#fccc7b&lr&#fcc97c&la&#fcc77d&lt&#fcc47d&li&#fcc17e&lo&#fcbe7e&ln &#fcbc7f&lC&#fcb980&la&#fdb680&ln&#fdb381&ld&#fdb181&ll&#fdae82&le",
            "&#fbd779S&#fbd17aa&#fccb7ct&#fcc57du&#fcc07er&#fcba7fa&#fdb481t&#fdae82e",
            mutableListOf("§fChance to feed you when", "§fbreaking blocks"),
            Material.YELLOW_CANDLE,
            mutableListOf("saturation")
        )
        return Pair("saturation", item.getItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        when (type) {
            Ability.BREAK_BLOCK -> {
                val chance: Int = Random.nextInt(1000)
                if (chance <= 2) {
                    player.foodLevel = player.foodLevel + 2
                    player.saturation = player.saturation + 2
                }
            }

            else -> return false
        }
        return true
    }
}