package me.feinrasur.utils.chat;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {

    public static String prefix = "";
    private static Pattern hexPattern = Pattern.compile("#([a-fA-F0-9]{6})");


    public static void setPrefix(String prefix) {
        Chat.prefix = prefix;
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

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(format(prefix + message));
    }

    public static void send(Player player, String message) {
        player.sendMessage(format(prefix + message));
    }
}
