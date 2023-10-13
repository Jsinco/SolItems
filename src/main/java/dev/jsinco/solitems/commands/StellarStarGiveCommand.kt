package dev.jsinco.solitems.commands

import dev.jsinco.solitems.SolItems
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


    }

    override fun tabComplete(plugin: SolItems, sender: CommandSender, args: Array<out String>): List<String>? {
        TODO("Not yet implemented")
    }

    override fun permission(): String? {
        TODO("Not yet implemented")
    }

    override fun playerOnly(): Boolean {
        TODO("Not yet implemented")
    }
}