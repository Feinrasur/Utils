package io.github.feinrasur.utils.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
public class Chat {

    public static String prefix = "";
    private static final Pattern hexPattern = Pattern.compile("#([a-fA-F0-9]{6})");


    public static void setPrefix(String prefix) {
        Chat.prefix = prefix + " &8» &r";
    }


    public static String getPrefix() {
        return prefix;
    }


    public static String format(String text) {
        text = text.replace("&#", "#");
        text = ChatColor.translateAlternateColorCodes('&', text);
        Matcher matcher = hexPattern.matcher(text);
        StringBuilder builder = new StringBuilder(text.length() + 4 * 8);

        while (matcher.find()) {
            String a = "§" + matcher.group(1).charAt(0);
            String b = "§" + matcher.group(1).charAt(1);
            String c = "§" + matcher.group(1).charAt(2);
            String d = "§" + matcher.group(1).charAt(3);
            String e = "§" + matcher.group(1).charAt(4);
            String f = "§" + matcher.group(1).charAt(5);
            matcher.appendReplacement(builder, "§x" + a + b + c + d + e + f);
        }

        return matcher.appendTail(builder).toString();
    }


    public static TextComponent formatComponent(String message) {
        return LegacyComponentSerializer.builder().build().deserialize(Chat.format(message));
    }


    public static void send(CommandSender sender, String message) {
        sender.sendMessage(format(prefix + message));
    }

    public static void send(Player player, String message) {
        player.sendMessage(format(prefix + message));
    }

    public static void send(CommandSender sender, Component component) {
        sender.sendMessage(component);
    }

    public static void send(Player player, Component component) {
        player.sendMessage(component);
    }
}
