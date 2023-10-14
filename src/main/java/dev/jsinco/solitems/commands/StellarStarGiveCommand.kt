package dev.jsinco.solitems.commands

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.items.misc.StellarStarItem
import dev.jsinco.solitems.util.Util
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class StellarStarGiveCommand : SubCommand {
    override fun execute(plugin: SolItems, sender: CommandSender, args: Array<out String>) {
        val player = Bukkit.getPlayerExact(args[1])
        val amount = if (args.size > 2) args[2].toInt() else 1

        if (player == null) {
            sender.sendMessage("${Util.prefix} Player not found!")
            return
        }

        val stellarStar = StellarStarItem().createItem().second
        stellarStar.amount = amount

        player.inventory.addItem(stellarStar)
    }

    override fun tabComplete(plugin: SolItems, sender: CommandSender, args: Array<out String>): List<String>? {
        if (args.size == 3) {
            return listOf("<amount>")
        }
        return null
    }

    override fun permission(): String? {
        return "solitems.command.stellarstar"
    }

    override fun playerOnly(): Boolean {
        return false
    }
}