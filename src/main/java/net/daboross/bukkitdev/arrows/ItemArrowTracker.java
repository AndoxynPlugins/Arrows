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

import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author daboross
 */
public class ItemArrowTracker {

    private static final String LORE0 = ChatColor.GREEN + "The ultimate weapon";
    private final ItemStack arrow;

    public ItemArrowTracker() {
        arrow = new ItemStack(Material.ARROW);
        ItemMeta meta = arrow.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Ultimate");
        meta.setLore(Arrays.asList(ChatColor.GREEN + "The ultimate weapon",
                ChatColor.LIGHT_PURPLE + "Level 01",
                ChatColor.RED + "10%" + ChatColor.GRAY + " chance to spawn blaze"));
        arrow.setItemMeta(meta);
    }

    public ItemStack getArrow() {
        return arrow.clone();
    }

    public int getLevel(ItemStack stack) {
        if (stack == null || stack.getType() != Material.ARROW) {
            return -1;
        }
        ItemMeta meta = stack.getItemMeta();
        if (!meta.getDisplayName().equals(ChatColor.DARK_RED + "Ultimate")) {
            return -1;
        }
        List<String> lore = meta.getLore();
        if (lore == null || lore.size() < 3 || !lore.get(0).equals(LORE0)) {
            return -1;
        }
        String levelString = lore.get(1);
        String levelNumberString = levelString.substring(levelString.length() - 2);
        try {
            return Integer.parseInt(levelNumberString);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
