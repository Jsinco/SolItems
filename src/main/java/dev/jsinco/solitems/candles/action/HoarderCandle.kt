package dev.jsinco.solitems.candles.action

import dev.jsinco.solitems.candles.CreateCandle
import dev.jsinco.solitems.manager.Ability
import dev.jsinco.solitems.manager.CustomItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class HoarderCandle : CustomItem {
    override fun createItem(): Pair<String, ItemStack> {
        val item = CreateCandle(
            "&#fb7f4a&lH&#ef7958&lo&#e47366&la&#d86d73&lr&#cd6681&ld&#c1608f&li&#b55a9d&ln&#aa54aa&lg &#9e4eb8&lC&#9248c6&la&#8741d4&ln&#7b3be1&ld&#7035ef&ll&#642ffd&le",
            "&#fb7f4aH&#e57464o&#d0687da&#ba5d97r&#a551b0d&#8f46cai&#7a3ae3n&#642ffdg",
            mutableListOf("§fShift left click any container to sell","§fits contents to the Hoarder","","§fShift right click to sell your","§finventory to the Hoarder"),
            Material.ORANGE_CANDLE,
            mutableListOf("hoarding")
        )
        return Pair("hoarding", item.getItem())
    }

    override fun executeAbilities(type: Ability, player: Player, event: Any): Boolean {
        if (!player.isSneaking) return false
        when (type) {
            Ability.LEFT_CLICK -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "hoarderextras sellcontainer ${player.name}")
            }
            Ability.RIGHT_CLICK -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "hoarderextras sellinventory ${player.name}")
            }
            else -> return false
        }
        return true
    }
}