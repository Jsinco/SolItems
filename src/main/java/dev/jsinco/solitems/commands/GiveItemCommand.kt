package dev.jsinco.solitems.commands

import dev.jsinco.solitems.SolItems
import dev.jsinco.solitems.manager.ItemManager
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GiveItemCommand : SubCommand {
    private val customItems = ItemManager.customItems
    private val customItemsByName: MutableMap<String, ItemStack> = mutableMapOf()

    init {
        for (customItem in customItems) {
            val item: ItemStack = customItem.value.createItem().second
            customItemsByName[ChatColor.stripColor(item.itemMeta.displayName)!!.replace(" ", "_")] = item
        }
    }

    override fun execute(plugin: SolItems, sender: CommandSender, args: Array<out String>) {
        val player = sender as Player


        val item = if (args[1] != "all") {
            customItemsByName[args[1]] ?: return
        } else {
            null
        }

        if (item != null) {
            player.inventory.addItem(item)
        } else {
            for (customItem in customItems) {
                player.inventory.addItem(customItem.value.createItem().second)
            }
        }
    }

    override fun tabComplete(plugin: SolItems, sender: CommandSender, args: Array<out String>): List<String> {
        return customItemsByName.keys.toMutableList()
    }

    override fun permission(): String {
        return "solitems.command.give"
    }

    override fun playerOnly(): Boolean {
        return true
    }
}