/*
 * Copyright (C) 2013 daboross
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author daboross
 */
public class ArrowAttackListener implements Listener {

    private final ArrowsPlugin plugin;
    private final ArrowMetadata metadata;

    public ArrowAttackListener(ArrowsPlugin plugin) {
        this.plugin = plugin;
        this.metadata = plugin.getMetadata();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent evt) {
        Player p = evt.getPlayer();
        int level = plugin.getArrow().getLevel(p.getItemInHand());
        if (level >= 0) {
            evt.setCancelled(true);
            plugin.getAttack().launchAttack(p, level);
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent evt) {
        final Entity e = evt.getEntity();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (metadata.isBoolean(e, "isUltimate")) {
                    if (metadata.isBoolean(e, "hasHitEntity")) {
                        if (metadata.isBoolean(e, "hitEntiyEventFired")) {
                            e.remove();
                        } else {
                            metadata.setBoolean(e, "hitEntiyEventFired", true);
                        }
                    } else {
                        int level = metadata.getInt(e, "ultimateLevel");
                        plugin.getAttack().effect(e.getLocation(), level);
                        e.remove();
                    }
                }
            }
        }.runTask(plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent evt) {
        Entity damager = evt.getDamager();
        if (damager instanceof Arrow && metadata.isBoolean(damager, "isUltimate")) {
            metadata.setBoolean(damager, "hasHitEntity", true);
        }
        if (damager instanceof Player) {
            Player p = (Player) damager;
            int level = plugin.getArrow().getLevel(p.getItemInHand());
            if (level >= 0) {
                evt.setCancelled(true);
                plugin.getAttack().launchAttack(p, level);
            }
        }
    }
}
