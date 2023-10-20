package dev.jsinco.solitems.commands;

import dev.jsinco.solitems.SolItems;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DebugCommand implements SubCommand {
    @Override
    public void execute(@NotNull SolItems plugin, @NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return;

        if (player.getScoreboardTags().contains("solitems.debug")) {
            player.removeScoreboardTag("solitems.debug");
            player.sendMessage("Debug mode disabled.");
        } else {
            player.addScoreboardTag("solitems.debug");
            player.sendMessage("Debug mode enabled.");
        }
    }

    @Nullable
    @Override
    public List<String> tabComplete(@NotNull SolItems plugin, @NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }

    @Nullable
    @Override
    public String permission() {
        return "solitems.command.debug";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }
}
