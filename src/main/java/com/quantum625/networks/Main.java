package com.quantum625.networks;

import com.quantum625.networks.commands.CommandListener;
import com.quantum625.networks.commands.LanguageModule;
import com.quantum625.networks.commands.TabCompleter;
import com.quantum625.networks.data.Config;
import com.quantum625.networks.listener.AutoSave;
import com.quantum625.networks.listener.BlockBreakEventListener;
import com.quantum625.networks.listener.InventoryOpenEventListener;
import com.quantum625.networks.listener.RightClickEventListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import java.io.File;


public final class Main extends JavaPlugin {

    private File dataFolder;

    private Installer installer;
    private CommandListener commandListener;
    private TabCompleter tabCompleter;
    private NetworkManager net;
    private Config config;
    private Economy economy;
    private LanguageModule lang;
    private boolean economyState;

    @Override
    public void onEnable() {

        //Bukkit.getLogger().info("\n\n==================================\n   Networks Plugin has launched\n==================================\n");
        Bukkit.getLogger().info(startMessage);

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        this.dataFolder = this.getDataFolder();
        this.installer = new Installer(dataFolder, this);

        economyState = setupEconomy();
        this.config = new Config(this, economy);
        config.setEconomyState(economyState);

        this.lang = new LanguageModule(dataFolder, config.getLanguage());
        this.net = new NetworkManager(this.config, this.dataFolder, this.lang);
        this.commandListener = new CommandListener(net, dataFolder, lang, config, economy);
        this.tabCompleter = new TabCompleter(net);

        getCommand("network").setExecutor(commandListener);
        getCommand("network").setTabCompleter(tabCompleter);

        this.getServer().getPluginManager().registerEvents(new AutoSave(net), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakEventListener(net, lang), this);
        this.getServer().getPluginManager().registerEvents(new RightClickEventListener(net, lang, config), this);
        this.getServer().getPluginManager().registerEvents(new InventoryOpenEventListener(net, lang, config), this);
        net.loadData();

        scheduleEvents();

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getLogger().warning("[Networks] Vault plugin is not installed, Economy feature will be disabled!");
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if (getServer().getServicesManager() == null) {
            Bukkit.getLogger().warning("[Networks] No service manager found");
            return false;
        }

        if (rsp == null) {
            Bukkit.getLogger().warning("[Networks] Failed to register Economy Service Provider! Do you have a supported economy plugin installed?");
            Bukkit.getLogger().warning("[Networks] See https://github.com/Quantum625/networks/wiki/Supported-Plugins for more information.");
            return false;
        }
        economy = rsp.getProvider();
        if (economy == null) {
            Bukkit.getLogger().warning("[Networks] Failed to enable economy, economy provider is null");
            return false;
        }
        Bukkit.getLogger().info("[Networks] Vault successfully registered");
        return true;
    }

    private void scheduleEvents() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for (Network network : net.listAll()) {
                    network.sortAll();
                }
            }
        }, 0, config.getTickrate());
    }


    @Override
    public void onDisable() {
        net.saveData();
        Bukkit.getLogger().info("\n\n==================================\n   Networks Plugin was shut down\n==================================\n");
    }

    private String startMessage = "\n" +
            "===========================================================================\n\n" +
            "            __   _                      _                    _   ___  \n" +
            "       /\\  / /__| |___      _____  _ __| | _____    __   __ / | / _ \\ \n" +
            "      /  \\/ / _ \\ __\\ \\ /\\ / / _ \\| '__| |/ / __|   \\ \\ / / | || | | |\n" +
            "     / /\\  /  __/ |_ \\ V  V / (_) | |  |   <\\__ \\    \\ V /  | || |_| |\n" +
            "    /_/  \\/ \\___|\\__| \\_/\\_/ \\___/|_|  |_|\\_\\___/     \\_/   |_(_)___/ \n" +
            "                                                                  \n"+
            "===========================================================================\n";
}
