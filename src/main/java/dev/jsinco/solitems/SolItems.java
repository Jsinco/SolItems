package dev.jsinco.solitems;

import dev.jsinco.solitems.commands.CommandManager;
import dev.jsinco.solitems.events.Listeners;
import dev.jsinco.solitems.events.PassiveListeners;
import dev.jsinco.solitems.hooks.GlowColorPlaceholder;
import dev.jsinco.solitems.hooks.PAPIManager;
import dev.jsinco.solitems.manager.FileManager;
import dev.jsinco.solitems.manager.GlowManager;
import dev.jsinco.solitems.manager.ItemManager;
import dev.jsinco.solitems.util.AnvilPrevention;
import dev.jsinco.solitems.util.Util;
import org.bukkit.plugin.java.JavaPlugin;

public final class SolItems extends JavaPlugin {

    private static SolItems plugin;
    private final PAPIManager papiManager = new PAPIManager();

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Util.loadUtils();

        PassiveListeners passiveListeners = new PassiveListeners(this);

        FileManager.generateFolder("saves");
        ItemManager itemManager = new ItemManager(this);
        try {
            itemManager.registerItems();
        } catch (Exception e) {
            e.printStackTrace();
        }

        passiveListeners.startMainRunnable();

        GlowManager.initGlowTeams();

        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        getServer().getPluginManager().registerEvents(new AnvilPrevention(this), this);
        getServer().getPluginManager().registerEvents(new StellarDeconstructor(this), this);

        getCommand("solitems").setExecutor(new CommandManager(this));
        getCommand("fixjsincosmistakes").setExecutor(new FixJsincosMistakes(plugin));

        papiManager.addPlaceholder(new GlowColorPlaceholder());
        papiManager.registerPlaceholders();
    }

    @Override
    public void onDisable() {
        if (papiManager.hasRegisteredPlaceholders()){
            papiManager.unregisterPlaceholders();
        }
    }

    public static SolItems getPlugin() {
        return plugin;
    }
}
