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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 *
 * @author daboross
 */
public class ArrowAttackListener implements Listener {

    private final Map<UUID, Boolean> arrows = new HashMap<UUID, Boolean>();
    private final ArrowsPlugin plugin;

    public ArrowAttackListener(ArrowsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent evt) {
        Player p = evt.getPlayer();
        int level = plugin.getArrow().getLevel(p.getItemInHand());
        if (level >= 0) {
            Location pos = p.getEyeLocation().clone();
            Vector v = pos.getDirection();
            pos.add(v.clone().multiply(2));
            Vector playerV = p.getVelocity().add(v.clone().multiply(-0.4));
            for (int i = 0; i < 2; i++) {
                Arrow a = p.getWorld().spawnArrow(pos, v, 2.0f, 2);
                a.setBounce(true);
                a.setShooter(p);
                arrows.put(a.getUniqueId(), Boolean.FALSE);
            }
            p.setVelocity(playerV);
            evt.setCancelled(true);
        }
    }

    @EventHandler
    public void onHit(final ProjectileHitEvent evt) {
        new BukkitRunnable() {
            @Override
            public void run() {
                UUID uuid = evt.getEntity().getUniqueId();
                Boolean b = arrows.get(uuid);
                if (b != null) {
                    if (b.booleanValue()) {
                        arrows.put(uuid, Boolean.FALSE);
                    } else {
                        arrows.remove(uuid);
                        Entity e = evt.getEntity().getWorld().spawnEntity(evt.getEntity().getLocation(), EntityType.ZOMBIE);
                        evt.getEntity().remove();
                    }
                }
            }
        }.runTask(plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent evt) {
        UUID uuid = evt.getDamager().getUniqueId();
        if (arrows.containsKey(uuid)) {
            arrows.put(uuid, Boolean.TRUE);
        }
    }
}
