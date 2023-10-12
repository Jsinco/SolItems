package dev.jsinco.solitems.candles.effects

import dev.jsinco.solitems.candles.CreateCandle
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class HeroOfTheVillageCandle : CustomItem {
    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateCandle(
            "&#97fb95&lH&#8afc8a&le&#7dfd7e&lr&#6ffd73&lo &#62fe67&lV&#55ff5c&lI &#E2E2E2Candle",
            "&#97fb95H&#8afc8ae&#7dfd7er&#6ffd73o &#62fe67V&#55ff5cI",
            mutableListOf("§fGives Hero of the Village","§fwhile wearing or holding"),
            Material.LIME_CANDLE,
            mutableListOf("hero")
        )
        return Pair("hero", item.getItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        when (type) {
            Ability.RUNNABLE -> {
                player.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.HERO_OF_THE_VILLAGE, 220, 5, false, false, false))
            }
            else -> return false
        }
        return true
    }
}