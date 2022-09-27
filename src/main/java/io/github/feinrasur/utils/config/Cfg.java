package io.github.feinrasur.utils.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Cfg {

    public static JavaPlugin plugin;
    private final FileConfiguration fileConfiguration;

    private final String name;

    public Cfg(String name) {
        this.name = name;
        fileConfiguration = Config.reload(name);
    }

    public static void init(JavaPlugin plugin) {
        Cfg.plugin = plugin;
    }

    public void reload() {
        Config.reloadNF(name);
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public ConfigurationSection getSection(String path) {
        if (fileConfiguration.getConfigurationSection(path) != null) {
            return fileConfiguration.getConfigurationSection(path);
        }
        return fileConfiguration.createSection(path);
    }

    public Object get(String path) {
        if (fileConfiguration.contains(path)) {
            return fileConfiguration.get(path);
        }
        return null;
    }
}
