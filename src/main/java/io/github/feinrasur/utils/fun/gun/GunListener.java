package io.github.feinrasur.utils.fun.gun;

import com.destroystokyo.paper.event.entity.ThrownEggHatchEvent;
import io.github.feinrasur.utils.fun.gun.implement.GunHitTargetEvent;
import io.github.feinrasur.utils.fun.gun.implement.GunShootEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class GunListener implements Listener {

    private JavaPlugin plugin;
    protected static GunManager manager = new GunManager();

    public GunListener(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        Player player = event.getPlayer();
        EquipmentSlot equipmentSlot = player.getHandRaised();
        ItemStack itemStack = player.getInventory().getItem(equipmentSlot);
        if (!manager.isRegistered(itemStack))return;
        Gun gun = manager.getGun(itemStack);
        plugin.getServer().getPluginManager().callEvent(new GunShootEvent(player, gun));
    }

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();;
        if (!(damager instanceof Player player)) return;
        EquipmentSlot equipmentSlot = player.getHandRaised();
        ItemStack itemStack = player.getInventory().getItem(equipmentSlot);
        if (!manager.isRegistered(itemStack))return;
        Gun gun = manager.getGun(itemStack);
        Entity target = event.getEntity();
        plugin.getServer().getPluginManager().callEvent(new GunHitTargetEvent(player, gun, target, event));
    }

    @EventHandler
    public void hatch(ThrownEggHatchEvent event) {
        if (event.getEgg().getShooter() instanceof Player player) {
            EquipmentSlot equipmentSlot = player.getHandRaised();
            ItemStack itemStack = player.getInventory().getItem(equipmentSlot);
            if (!manager.isRegistered(itemStack))return;
            event.setHatching(false);
        }
    }

    @EventHandler
    public void onGunShoot(GunShootEvent event) {
        Player player = event.getPlayer();
        Gun gun = event.getGun();
        if (gun.getEntityType() != null) {

            Runnable runnable = () -> {
                Snowball snowball = player.launchProjectile(Snowball.class);
                Vector vector = snowball.getVelocity();
                snowball.remove();
                Entity entity = player.getWorld().spawnEntity(player.getLocation(), gun.getEntityType());
                entity.setVelocity(vector.multiply(gun.getSpeed()));
                Bukkit.getScheduler().runTaskLater(gun.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        try {
                            entity.remove();
                        } catch (Exception e) {
                        }
                    }
                }, 200L);
            };
            BukkitTask task = Bukkit.getScheduler().runTaskTimer(gun.getPlugin(), runnable, 0,1);
            Bukkit.getScheduler().runTaskLater(gun.getPlugin(), r -> {task.cancel();}, gun.getSpray());
        }

        if (gun.getProjectile() != null) {
            Runnable runnable = () -> {
                Projectile projectile = player.launchProjectile(gun.getProjectile());
                projectile.setVelocity(projectile.getVelocity().multiply(gun.getSpeed()));
                Bukkit.getScheduler().runTaskLater(gun.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        try {
                            projectile.remove();
                        } catch (Exception e) {
                        }
                    }
                }, 200L);
            };
            BukkitTask task = Bukkit.getScheduler().runTaskTimer(gun.getPlugin(), runnable, 0,1);
            Bukkit.getScheduler().runTaskLater(gun.getPlugin(), r -> {task.cancel();}, gun.getSpray());
        }
    }
}
