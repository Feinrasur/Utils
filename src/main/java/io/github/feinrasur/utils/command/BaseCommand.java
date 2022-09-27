package io.github.feinrasur.utils.command;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class BaseCommand extends BukkitCommand{

    private JavaPlugin plugin;
    private String name;

    protected BaseCommand(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;

        //Annotations

        //End Annotations
    }

    //public abstract boolean execute(CommandSender sender, String label, String[] args, String[] previousArgs);
}
