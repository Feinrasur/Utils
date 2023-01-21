package io.github.feinrasur.utils.fun.gun;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GunManager {

    protected Map<ItemStack, Gun> guns = new HashMap<>();

    protected GunManager() {

    }

    protected void registerGun(ItemStack itemStack, Gun gun) {
        guns.put(itemStack, gun);
    }

    protected boolean isRegistered(ItemStack itemStack) {
        return guns.containsKey(itemStack);
    }

    protected Gun getGun(ItemStack itemStack) {
        if (!isRegistered(itemStack)) return null;
        return guns.get(itemStack);
    }
}
