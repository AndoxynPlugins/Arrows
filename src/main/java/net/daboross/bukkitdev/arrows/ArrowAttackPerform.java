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

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.util.Vector;

/**
 *
 * @author daboross
 */
public class ArrowAttackPerform {

    private final ArrowsPlugin plugin;
    private final ArrowMetadata metadata;

    public ArrowAttackPerform(ArrowsPlugin plugin) {
        this.plugin = plugin;
        this.metadata = plugin.getMetadata();
    }

    public void launchAttack(Player p, int level) {
        knockbackPlayer(p, 0.6);
        launchArrows(p, level);
    }

    public void knockbackPlayer(Player p, double force) {
        p.setVelocity(p.getVelocity().add(p.getLocation().getDirection().multiply(-force)));
    }

    public void launchArrows(Player player, int level) {
        Location arrowLoc = player.getEyeLocation().clone();
        arrowLoc.add(arrowLoc.getDirection());
        Vector direction = arrowLoc.getDirection();
        for (int i = 0; i < 2; i++) {
            launchArrow(arrowLoc, direction, player, level);
        }
    }

    public void launchArrow(Location location, Vector direction, Player shooter, int level) {
        Arrow a = location.getWorld().spawnArrow(location, direction, 2.0f, 2);
        a.setBounce(false);
        a.setShooter(shooter);
        metadata.setBoolean(a, "isUltimate", true);
        metadata.setInt(a, "ultimateLevel", level);
    }
    private final Random r = new Random();

    public void effect(Location location, int level) {
        if (level == 1) {
            switch (r.nextInt(30)) {
                case 0:
                case 1:
                    Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                    zombie.setCustomName("Zombie level " + level);
                    break;
                case 2:
                case 3:
                    Blaze blaze = (Blaze) location.getWorld().spawnEntity(location, EntityType.BLAZE);
                    blaze.setCustomName("Ultimate blaze level " + level);
                    break;
                case 4:
                    Vector direction = location.getDirection().multiply(-1);
                    launchArrow(location, direction, null, 2);
                    launchArrow(location, direction, null, 2);
                    launchArrow(location, direction, null, 2);
                    break;
            }
        } else if (level == 2) {
            switch (r.nextInt(10)) {
                case 0:
                    Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                    zombie.setCustomName("Zombie level " + level);
                    break;
                case 1:
                    Blaze blaze = (Blaze) location.getWorld().spawnEntity(location, EntityType.BLAZE);
                    blaze.setCustomName("Ultimate blaze level " + level);
                    break;
            }
        } else if (level == 3) {
            switch (r.nextInt(5)) {
                case 0:
                    Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                    zombie.setCustomName("Zombie level " + level);
                    break;
                case 1:
                    Blaze blaze = (Blaze) location.getWorld().spawnEntity(location, EntityType.BLAZE);
                    blaze.setCustomName("Ultimate blaze level " + level);
                    break;
            }
        }
    }
}
