package dev.jsinco.solitems;

import dev.jsinco.solitems.commands.CommandManager;
import dev.jsinco.solitems.events.Delay;
import dev.jsinco.solitems.events.Listeners;
import dev.jsinco.solitems.events.PassiveListeners;
import dev.jsinco.solitems.hooks.GlowColorPlaceholder;
import dev.jsinco.solitems.hooks.PAPIManager;
import dev.jsinco.solitems.manager.GlowManager;
import dev.jsinco.solitems.manager.ItemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SolItems extends JavaPlugin {

    private static SolItems plugin;
    private final PAPIManager papiManager = new PAPIManager();

    @Override
    public void onEnable() {
        plugin = this;
        ItemManager itemManager = new ItemManager(this);
        PassiveListeners passiveListeners = new PassiveListeners(this);

        FileManager fileManager = new FileManager("blank.txt");
        fileManager.generateFolder("saves");

        itemManager.initializeCandleClasses();
        passiveListeners.startMainRunnable();

        GlowManager.initGlowTeams(); // Convert to object
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        getCommand("solitems").setExecutor(new CommandManager(this));


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
