package me.feinrasur.utils.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config
{

    private static FileConfiguration config;
    private static JavaPlugin plugin;

    public static Map<String, File> customConfigFiles = new HashMap<>();
    private static Map<String, FileConfiguration> customConfigs = new HashMap<>();

    public static void init(JavaPlugin plugin) {
        Config.plugin = plugin;
        File folder = plugin.getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static FileConfiguration getConfig(String name) {
        if (customConfigs.containsKey(name)) {
            return customConfigs.get(name);
        } else {
            return createCustomConfig(name);
        }
    }

    public static void save() {
        plugin.saveConfig();
    }

    public static void save(String name) {
        if (customConfigs.containsKey(name)) {
            try {
                customConfigs.get(name).save(customConfigFiles.get(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static FileConfiguration createCustomConfig(String name) {
        File folder = plugin.getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File customConfigFile = new File(folder, name + ".yml");
        customConfigFiles.put(name, customConfigFile);
        FileConfiguration customConfig = new YamlConfiguration();

        try {
            if (!customConfigFile.exists()) {
                customConfigFile.createNewFile();
            }
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        customConfigs.put(name, customConfig);
        return customConfig;
    }

    public static void reload(String name) {
        FileConfiguration config;
        if (customConfigs.containsKey(name))
            config = customConfigs.get(name);
        if (!customConfigs.containsKey(name))
            config = createCustomConfig(name);
        config = YamlConfiguration.loadConfiguration(customConfigFiles.get(name));
        customConfigs.put(name, config);
    }

    public static void reload() {
        plugin.reloadConfig();
    }
}
