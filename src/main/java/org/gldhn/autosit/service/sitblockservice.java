package org.gldhn.autosit.service;

import org.bukkit.Location;
import org.gldhn.autosit.Autosit;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class sitblockservice {
    private List<Location> blockLocations;
    private final Autosit autosit;
    public sitblockservice(Autosit autosit) {
        this.autosit = autosit;
    }

    public List<Location> getBlockLocations() {
        return blockLocations;
    }

    public void setBlockLocations(List<Location> blockLocations) {
        this.blockLocations = blockLocations;
    }

    public void addBlockLocation(Location blockLocation) {
        blockLocations.add(blockLocation);
        sort();
    }
    public void addBlockLocation(Location blockLocation,boolean  sort) {
        blockLocations.add(blockLocation);
        if(sort) sort();
    }
    public void removeBlockLocation(Location blockLocation) {
        blockLocations.remove(blockLocation);
    }
    public void addBlockLocation(double x, double y, double z) {
        blockLocations.add(new Location(null,x,y,z));
        sort();
    }
    public void addBlockLocation(double x, double y, double z,boolean  sort) {
        blockLocations.add(new Location(null,x,y,z));
        if(sort) sort();
    }
    public void removeBlockLocation(double x, double y, double z) {
        blockLocations.remove(new Location(null,x,y,z));
    }
    public void addBlockLocations(double x1 , double y1 , double z1, double x2 , double y2 , double z2) {
        addBlocks(x1, y1, z1, x2, y2, z2);
        sort();
    }
    public void addBlockLocations(double x1 , double y1 , double z1, double x2 , double y2 , double z2,boolean  sort) {
        addBlocks(x1, y1, z1, x2, y2, z2);
        if(sort) sort();
    }

    private void addBlocks(double x1, double y1, double z1, double x2, double y2, double z2) {
        int minX = (int) Math.min(x1, x2);
        int maxX = (int) Math.max(x1, x2);
        int minY = (int) Math.min(y1, y2);
        int maxY = (int) Math.max(y1, y2);
        int minZ = (int) Math.min(z1, z2);
        int maxZ = (int) Math.max(z1, z2);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    addBlockLocation(x, y, z);
                }
            }
        }
    }

    private void sort() {
        blockLocations.sort((loc1, loc2) -> {
            // Y 降序
            if (loc2.getY() != loc1.getY()) {
                return Double.compare(loc2.getY(), loc1.getY());
            }

            // Z 降序
            if (loc2.getZ() != loc1.getZ()) {
                return Double.compare(loc2.getZ(), loc1.getZ());
            }

            // X 降序
            return Double.compare(loc2.getX(), loc1.getX());
        });
    }
}
