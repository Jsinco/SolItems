package dev.jsinco.solitems.util

import org.bukkit.ChatColor


object Util {
    const val WITH_DELIMITER = "((?<=%1\$s)|(?=%1\$s))"

    /**
     * @param text The string of text to apply color/effects to
     * @return Returns a string of text with color/effects applied
     */
    @JvmStatic
    fun colorcode(text: String): String {
        val texts = text.split(String.format(WITH_DELIMITER, "&").toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val finalText = StringBuilder()
        var i = 0
        while (i < texts.size) {
            if (texts[i].equals("&", ignoreCase = true)) {
                //get the next string
                i++
                if (texts[i][0] == '#') {
                    finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7)).toString() + texts[i].substring(7))
                } else {
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]))
                }
            } else {
                finalText.append(texts[i])
            }
            i++
        }
        return finalText.toString()
    }

    @JvmStatic
    fun colorcodeList(list: List<String>): List<String> {
        val coloredList: MutableList<String> = ArrayList()
        for (string in list) {
            coloredList.add(colorcode(string))
        }
        return coloredList
    }


    fun getColorCodeByChatColor(colorCode: ChatColor): String {
        return when (colorCode) {
            ChatColor.AQUA -> "§b"
            ChatColor.BLACK -> "§0"
            ChatColor.BLUE -> "§9"
            ChatColor.DARK_AQUA -> "§3"
            ChatColor.DARK_BLUE -> "§1"
            ChatColor.DARK_GRAY -> "§8"
            ChatColor.DARK_GREEN -> "§2"
            ChatColor.DARK_PURPLE -> "§5"
            ChatColor.DARK_RED -> "§4"
            ChatColor.GOLD -> "§6"
            ChatColor.GRAY -> "§7"
            ChatColor.GREEN -> "§a"
            ChatColor.LIGHT_PURPLE -> "§d"
            ChatColor.RED -> "§c"
            ChatColor.YELLOW -> "§e"
            else -> "§f"
        }
    }
}