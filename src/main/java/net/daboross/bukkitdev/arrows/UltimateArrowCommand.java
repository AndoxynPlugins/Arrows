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

import java.util.Collections;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author daboross
 */
public class UltimateArrowCommand implements CommandExecutor, TabCompleter {

    private final ArrowsPlugin plugin;

    public UltimateArrowCommand(ArrowsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "You aren't a player.");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage(ChatColor.DARK_RED + "Too many arguments.");
            return true;
        }
        Player p = (Player) sender;
        PlayerInventory inv = p.getInventory();
        int slot = inv.firstEmpty();
        if (slot < 0) {
            sender.sendMessage(ChatColor.DARK_RED + "You don't have room.");
            return true;
        }
        inv.setItem(slot, plugin.getArrow().getArrow());
        return true;
    }

    public void registerIfExists(PluginCommand command) {
        if (command == null) {
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
        command.setPermission("ultimatearrow.get");
        command.setPermissionMessage(ChatColor.DARK_RED + "You do not have permission.");
        command.setUsage(ChatColor.GRAY + "/<command>");
        command.setDescription("Gives you the ultimate arrow.");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.EMPTY_LIST;
    }
}
