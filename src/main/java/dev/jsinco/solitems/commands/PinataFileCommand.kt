package dev.jsinco.solitems.commands

import dev.jsinco.solitems.manager.FileManager
import dev.jsinco.solitems.SolItems
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PinataFileCommand : SubCommand {
    override fun execute(plugin: SolItems, sender: CommandSender, args: Array<out String>) {
        val player = sender as Player
        val fileManager = FileManager("saves/pinata.yml")
        fileManager.generateFile()
        val pinataFile = fileManager.getFileYaml()


        val item = player.inventory.itemInMainHand
        val name = if (item.itemMeta.hasDisplayName()) {
            ChatColor.stripColor(item.itemMeta.displayName)!!.replace(" ", "_").lowercase()
        } else {
            item.type.name.lowercase()
        }
        if (args[1].equals("add")) {
            val type = if (args[2].equals("rare")) {
                "rare-items"
            } else {
                "items"
            }
            pinataFile.set("$type.$name", item)
        } else {
            pinataFile.set("items.$name", null)
            pinataFile.set("rare-items.$name", null)
        }
        fileManager.saveFileYaml()

        player.sendMessage("Performed command")

    }

    override fun tabComplete(plugin: SolItems, sender: CommandSender, args: Array<out String>): List<String>? {
        if (args.size == 2) {
            return listOf("add", "remove")
        }

        when (args[1]) {
            "add" -> {
                return listOf("rare", "normal")
            }
        }
        return null
    }

    override fun permission(): String {
        return "solitems.command.pinatafile"
    }

    override fun playerOnly(): Boolean {
        return true
    }
}