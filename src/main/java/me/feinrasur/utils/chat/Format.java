package me.feinrasur.utils.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {

    private static final Pattern hexPattern = Pattern.compile("#([a-fA-F0-9]{6})");

    public static Component item(Component component) {
        return component.decorations(decorations);
    }

    public static String hex(String message) {
        message = message.replace("&#", "#");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            matcher.appendReplacement(buffer, "<#" + matcher.group(1) + ">");
        }

        return matcher.appendTail(buffer).toString();

    }

    public static String cc(String message) {
        String string = message.replace("§", "&");
        for (Map.Entry<String, String> code : codes.entrySet()) {
            string = string.replace(code.getKey(), code.getValue());
        }
        return string;
    }

    public static String format(String message) {
        return hex(cc(message));
    }

    public static Component component(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static Component componentFormat(String message) {
        return MiniMessage.miniMessage().deserialize(format(message));
    }

    public static Map<TextDecoration, TextDecoration.State> decorations = new HashMap<>() {
        {
            put(TextDecoration.OBFUSCATED, TextDecoration.State.FALSE);
            put(TextDecoration.BOLD, TextDecoration.State.FALSE);
            put(TextDecoration.STRIKETHROUGH, TextDecoration.State.FALSE);
            put(TextDecoration.UNDERLINED, TextDecoration.State.FALSE);
            put(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        }
    };

    public static Map<String, String> codes = new HashMap<>() {
        {
            put("&0", "<black>");
            put("&1", "<dark_blue>");
            put("&2", "<dark_green>");
            put("&3", "<dark_aqua>");
            put("&4", "<dark_red>");
            put("&5", "<dark_purple>");
            put("&6", "<gold>");
            put("&7", "<grey>");
            put("&8", "<dark_grey>");
            put("&9", "<blue>");
            put("&a", "<green>");
            put("&b", "<aqua>");
            put("&c", "<red>");
            put("&d", "<light_purple>");
            put("&e", "<yellow>");
            put("&f", "<white>");
            put("&k", "<obf>");
            put("&l", "<b>");
            put("&m", "<st>");
            put("&n", "<u>");
            put("&o", "<i>");
            put("&r", "<reset>");
        }
    };


}
