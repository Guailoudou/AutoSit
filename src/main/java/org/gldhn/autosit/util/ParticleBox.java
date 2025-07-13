package org.gldhn.autosit.util;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.List;

public class ParticleBox {

    private static final double SCALE = 0.3; // 小方块大小相对于原方块的比例
    public static void drawParticleBox(List<Location> locations, Player player,Color  color) {
        for (Location loc : locations) {
            drawParticleBox(loc, player,color);
        }
    }
    public static void drawParticleBox(Location loc, Player player,Color  color) {
        World world = player.getWorld();
        Location center = loc.clone().add(0.5, 0.5, 0.5); // 获取方块中心位置

        // 根据SCALE计算小方块的边界
        double minX = center.getX() - SCALE / 2;
        double minY = center.getY() - SCALE / 2;
        double minZ = center.getZ() - SCALE / 2;
        double maxX = center.getX() + SCALE / 2;
        double maxY = center.getY() + SCALE / 2;
        double maxZ = center.getZ() + SCALE / 2;

        // 在小方块的每个边上生成粒子
        for (double x = minX; x <= maxX; x = x + SCALE/2) {
            for (double y = minY; y <= maxY; y = y + SCALE/2) {
                for (double z = minZ; z <= maxZ; z = z + SCALE/2) {
                    if ((x == minX || x == maxX) && (y == minY || y == maxY) && (z >= minZ && z <= maxZ) ||
                            (y == minY || y == maxY) && (z == minZ || z == maxZ) && (x >= minX && x <= maxX) ||
                            (z == minZ || z == maxZ) && (x == minX || x == maxX) && (y >= minY && y <= maxY)) {
                        Location particleLoc = new Location(world, x, y, z);
                        Bukkit.getOnlinePlayers().forEach(p -> {
                            if(p.isOp())
                                p.spawnParticle(Particle.REDSTONE, particleLoc, 1, new Particle.DustOptions(color, 1));
                        });
                    }
                }
            }
        }

    }
}
