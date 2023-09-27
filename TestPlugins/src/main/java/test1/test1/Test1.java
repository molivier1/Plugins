package test1.test1;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import test1.test1.commands.AutoCompletion;
import test1.test1.commands.Changehoe;
import test1.test1.commands.RegenBlock;
import test1.test1.commands.Fly;
import test1.test1.handlers.ReplaceCrop;

import java.io.File;
import java.io.IOException;

public final class Test1 extends JavaPlugin {

    private static Test1 instance;
    private FileConfiguration dataConfig;
    private File dataConfigFile;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Bukkit.getLogger().info("PLUGIN : TEST1 ENABLED");

        getCommand("fly").setExecutor(new Fly());
        getCommand("changehoe").setExecutor(new Changehoe());
        getCommand("regenblock").setExecutor(new RegenBlock(this));
        getCommand("regenblock").setTabCompleter(new AutoCompletion());

        new ReplaceCrop(this);

        this.saveDefaultConfig(); // <-- create config.yml

        dataConfigFile = new File(getDataFolder(), "data.yml");
        reloadDataConfig();

        //new ButtonFiesta(this);

        //new CobbleRandom(this);
        //new CobbleRandomV2(this);
        //new CobbleRandomV3(this);

        //new TorchHandler(this);
        //new PlayerHandler(this);

        //new HoeHandler(this);
    }

    public void reloadDataConfig(){
        if(!dataConfigFile.exists()){
            saveResource("data.yml", false);
        }

        dataConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(dataConfigFile);
    }

    public FileConfiguration getDataConfig(){
        return dataConfig;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("PLUGIN : TEST1 DISABLED");
    }

    public static Test1 getInstance() {
        return instance;
    }
}
