package org.gldhn.autosit.service;

import org.bukkit.Location;
import org.gldhn.autosit.Autosit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SitBlockService {
    private List<Location> blockLocations = new ArrayList<>();
    private final Autosit autosit;
    private Location centerPoint;
    public SitBlockService(Autosit autosit) {
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
//        sort();
    }
    public void removeBlockLocation(Location blockLocation) {
        blockLocations.remove(blockLocation);
    }
    public void addBlockLocation(double x, double y, double z) {
        Location blockLocation = new Location(null,x,y,z);
        if(!blockLocations.contains(blockLocation)) blockLocations.add(blockLocation);
    }
    public void removeBlockLocation(double x, double y, double z) {
        Location blockLocation = new Location(null,x,y,z);
        blockLocations.remove(blockLocation);
    }
    public void clearBlockLocations() {
        blockLocations.clear();
    }
    public Location getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Location centerPoint) {
        this.centerPoint = centerPoint;
    }

    public void addBlockLocations(double x1 , double y1 , double z1, double x2 , double y2 , double z2) {
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
//        sort();
    }

    public void sort() {
        blockLocations.sort(Comparator.comparingDouble(o-> {
            double x = o.getX();
            double y = o.getY();
            double z = o.getZ();
            return Math.sqrt((x-centerPoint.getX()) * (x-centerPoint.getX()) + (y-centerPoint.getY()) * (y-centerPoint.getY()) + (z-centerPoint.getZ()) * (z-centerPoint.getZ()));
        }));
    }
}
