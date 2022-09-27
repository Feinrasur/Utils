package io.github.feinrasur.utils.chat;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Kyori {

    private static Component prefix;
    private static BukkitAudiences adventure;
    private Component component;

    public Kyori(@NotNull JavaPlugin plugin, @NotNull Component prefix) {
        Kyori.prefix = prefix;
        Kyori.adventure = BukkitAudiences.create(plugin);
    }

    public Kyori(@NotNull JavaPlugin plugin, @NotNull String prefix) {
        Kyori.prefix = MiniMessage.miniMessage().deserialize(prefix);
        Kyori.adventure = BukkitAudiences.create(plugin);
    }

    private Kyori(Component component) {
        this.component = component;
    }

    public static Kyori create() {
        return new Kyori(Component.text(""));
    }

    public static Component createComponent(String text) {
        return MiniMessage.miniMessage().deserialize(text);
    }

    public static Kyori create(String message) {
        return new Kyori(MiniMessage.miniMessage().deserialize(message));
    }

    public static Kyori createNoFormat(String message) {
        return new Kyori(MiniMessage.miniMessage().deserialize(message));
    }

    private boolean isInitialized() {
        return adventure != null;
    }

    public Component getPrefix() {
        return prefix;
    }

    public Component append(Component component) {
        return this.component.append(component);
    }

    public Component append(String message) {
        return append(MiniMessage.miniMessage().deserialize(message));
    }

    public void disable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    public void send(CommandSender sender) {
        Component cmp = prefix.append(component);
        adventure.sender(sender).sendMessage(cmp);
    }

    public void send(Player player) {
        Component cmp = prefix.append(component);
        adventure.player(player).sendMessage(cmp);
    }

    public static void send(CommandSender sender, Component component) {
        adventure.sender(sender).sendMessage(component);
    }

    public static void send(Player sender, Component component) {
        adventure.player(sender).sendMessage(component);
    }
}
