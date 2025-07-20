package org.gldhn.autosit.service;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.gldhn.autosit.Autosit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SitBlockService {
    private List<Location> blockLocations = new ArrayList<>();
    private final Autosit autosit;
    private Location centerPoint;
    private boolean needSort = false;
    private List<Player> nositPlayers = new ArrayList<>();
    public SitBlockService(Autosit autosit) {
        this.autosit = autosit;
    }

    public List<Location> getBlockLocations() {
        return blockLocations;
    }

    public void setBlockLocations(List<Location> blockLocations) {
        this.blockLocations = blockLocations;
        needSort = true;
    }

    public void addBlockLocation(Location blockLocation) {
        blockLocations.add(blockLocation);
//        sort();
    }
    public void removeBlockLocation(Location blockLocation) {
        blockLocation.setWorld(null);
        blockLocations.remove(blockLocation);
    }
    public void addBlockLocation(double x, double y, double z) {
        Location blockLocation = new Location(null,x,y,z);
        if(!blockLocations.contains(blockLocation)) blockLocations.add(blockLocation);
        needSort = true;
    }
    public void removeBlockLocation(double x, double y, double z) {
        Location blockLocation = new Location(null,x,y,z);
        blockLocations.remove(blockLocation);
        needSort = true;
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
    public void addBlockLocations(Location l1,Location l2) {
        addBlockLocations(l1.getX(), l1.getY(), l1.getZ(), l2.getX(), l2.getY(), l2.getZ());
    }
    public void removeBlockLocations(double x1 , double y1 , double z1, double x2 , double y2 , double z2) {
        int minX = (int) Math.min(x1, x2);
        int maxX = (int) Math.max(x1, x2);
        int minY = (int) Math.min(y1, y2);
        int maxY = (int) Math.max(y1, y2);
        int minZ = (int) Math.min(z1, z2);
        int maxZ = (int) Math.max(z1, z2);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    removeBlockLocation(x, y, z);
                }
            }
        }
    }
    public void removeBlockLocations(Location l1, Location l2){
        removeBlockLocations(l1.getBlockX(), l1.getBlockY(), l1.getBlockZ(), l2.getBlockX(), l2.getBlockY(), l2.getBlockZ());
    }

    public void sort() {
        blockLocations.sort(Comparator.comparingDouble(o-> {
            double x = o.getX();
            double y = o.getY();
            double z = o.getZ();
            return Math.sqrt((x-centerPoint.getX()) * (x-centerPoint.getX()) + (y-centerPoint.getY()) * (y-centerPoint.getY()) + (z-centerPoint.getZ()) * (z-centerPoint.getZ()));
        }));
        needSort = false;
    }

    public void save() {
        autosit.getDataYmlService().getYmaldata().set("blocklocations", blockLocations);
        autosit.getDataYmlService().getYmaldata().set("centerpoint", centerPoint);
        autosit.getDataYmlService().getYmaldata().set("nositplayers", nositPlayers);
        autosit.getDataYmlService().saveYmaldata();
    }
    public void addNoSitPlayer(Player player) {
        nositPlayers.add(player);
    }
    public void removeNoSitPlayer(Player player) {
        nositPlayers.remove(player);
    }

    public List<Player> getNositPlayers() {
        return nositPlayers;
    }

    public boolean isNeedSort() {
        return needSort;
    }
}
