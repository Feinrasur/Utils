package io.github.feinrasur.utils.fun.gun;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Gun {

    private JavaPlugin plugin;
    private ItemStack itemStack;
    private Component displayName;
    private double damage;
    private EntityType entityType = null;
    private Class<? extends Projectile> projectile = null;
    private Integer speed = 1;
    private Integer spray = 1;

    public Gun(JavaPlugin plugin, ItemStack itemStack, Component displayName, float damage) {
        this.plugin = plugin;
        this.itemStack = itemStack;
        this.displayName = displayName;
        this.damage = damage;
    }

    public void setProjectile(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setProjectile(Class<? extends Projectile> projectile) {
        this.projectile = projectile;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void register() {
        GunListener.manager.registerGun(this.itemStack, this);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public Class<? extends Projectile> getProjectile() {
        return projectile;
    }

    public void setSpeedMultiplier(Integer integer) {
        this.speed = integer;
    }

    public Integer getSpeed() {
        return this.speed;
    }

    public Integer getSpray() {
        return spray;
    }

    public void setSpray(Integer spray) {
        this.spray = spray;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
