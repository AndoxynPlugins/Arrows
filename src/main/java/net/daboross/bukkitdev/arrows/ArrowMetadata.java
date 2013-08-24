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

import java.util.List;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author daboross
 */
public class ArrowMetadata {

    private final ArrowsPlugin plugin;
    private final JustExists justExists = new JustExists();

    public ArrowMetadata(ArrowsPlugin plugin) {
        this.plugin = plugin;
    }

    public void setBoolean(Metadatable metadatable, String name, boolean value) {
        if (value) {
            metadatable.setMetadata(name, justExists);
        } else {
            metadatable.removeMetadata(name, plugin);
        }
    }

    public boolean isBoolean(Metadatable metadatable, String name) {
        return metadatable.hasMetadata(name);
    }

    public void setInt(Metadatable metadatable, String name, int value) {
        metadatable.setMetadata(name, new IntValue(value));
    }

    public int getInt(Metadatable metadatable, String name) {
        List<MetadataValue> value = metadatable.getMetadata(name);
        if (value.isEmpty()) {
            return -1;
        } else {
            return value.get(0).asInt();
        }
    }

    public class JustExists implements MetadataValue {

        @Override
        public Object value() {
            return null;
        }

        @Override
        public int asInt() {
            return 0;
        }

        @Override
        public float asFloat() {
            return 0;
        }

        @Override
        public double asDouble() {
            return 0;
        }

        @Override
        public long asLong() {
            return 0;
        }

        @Override
        public short asShort() {
            return 0;
        }

        @Override
        public byte asByte() {
            return 0;
        }

        @Override
        public boolean asBoolean() {
            return true;
        }

        @Override
        public String asString() {
            return "";
        }

        @Override
        public Plugin getOwningPlugin() {
            return plugin;
        }

        @Override
        public void invalidate() {
        }
    }

    public class IntValue implements MetadataValue {

        private final int value;

        public IntValue(int value) {
            this.value = value;
        }

        @Override
        public Object value() {
            return Integer.valueOf(value);
        }

        @Override
        public int asInt() {
            return value;
        }

        @Override
        public float asFloat() {
            return value;
        }

        @Override
        public double asDouble() {
            return value;
        }

        @Override
        public long asLong() {
            return value;
        }

        @Override
        public short asShort() {
            return (short) value;
        }

        @Override
        public byte asByte() {
            return (byte) value;
        }

        @Override
        public boolean asBoolean() {
            return value > 0;
        }

        @Override
        public String asString() {
            return Integer.toString(value);
        }

        @Override
        public Plugin getOwningPlugin() {
            return plugin;
        }

        @Override
        public void invalidate() {
        }
    }
}
