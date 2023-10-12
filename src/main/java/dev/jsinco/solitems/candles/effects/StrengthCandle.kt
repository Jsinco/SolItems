package dev.jsinco.solitems.candles.effects

import dev.jsinco.solitems.candles.CreateCandle
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class StrengthCandle : CustomItem {
    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateCandle(
            "&#ff4141&lS&#ff4544&lt&#ff4947&lr&#ff4e4a&le&#ff524d&ln&#ff5651&lg&#ff5a54&lt&#ff5f57&lh &#ff635a&lI &#E2E2E2Candle",
            "&#ff4141S&#ff4544t&#ff4947r&#ff4e4ae&#ff524dn&#ff5651g&#ff5a54t&#ff5f57h &#ff635aI",
            mutableListOf( "§fGives strength while ","§fwearing or holding"),
            Material.RED_CANDLE,
            mutableListOf("strength")
        )
        return Pair("strength", item.getItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        when (type) {
            Ability.RUNNABLE -> {
                player.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.INCREASE_DAMAGE, 220, 0, false, false, false))
            }
            else -> return false
        }
        return true
    }
}