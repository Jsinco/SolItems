package dev.jsinco.solitems.commands

import dev.jsinco.solitems.SolItems
import org.bukkit.command.CommandSender

interface SubCommand {

    fun execute(plugin: SolItems, sender: CommandSender, args: Array<out String>)

    fun tabComplete(plugin: SolItems, sender: CommandSender, args: Array<out String>): List<String>?

    fun permission(): String?

    fun playerOnly(): Boolean
}