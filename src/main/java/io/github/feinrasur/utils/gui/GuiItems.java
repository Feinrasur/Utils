package io.github.feinrasur.utils.gui;

import io.github.feinrasur.utils.chat.Chat;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("all")
public class GuiItems {

    public static ItemStack convertToGui(@NotNull ItemStack item, @Nullable String name) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.MENDING, 1, true);
        if (name != null)
            meta.displayName(Chat.formatComponent(name));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getBackButtonFeather() {
        return convertToGui(new ItemStack(Material.FEATHER), Chat.format("&#2cb2ffBack"));
    }

    public static ItemStack getBackButtonBarrier() {
        return convertToGui(new ItemStack(Material.BARRIER), Chat.format("&#2cb2ffBack"));
    }

    public static ItemStack getCloseButton() {
        return convertToGui(new ItemStack(Material.BARRIER), Chat.format("&cClose"));
    }
}
