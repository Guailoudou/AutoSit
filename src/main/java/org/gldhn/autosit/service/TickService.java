package org.gldhn.autosit.service;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.gldhn.autosit.Autosit;
import org.gldhn.autosit.util.ParticleBox;

import java.util.HashMap;
import java.util.UUID;

public class TickService {
    private final Autosit autosit;
    private Boolean lookBlockTick = false;
    private Player player;
    public TickService(Autosit autosit){
        this.autosit = autosit;
    }

    public void tick(){
        if(lookBlockTick){
            ParticleBox.drawParticleBox(autosit.getSitblockservice().getBlockLocations(),player, Color.LIME);
            if(autosit.getSitblockservice().getCenterPoint()!=null) ParticleBox.drawParticleBox(autosit.getSitblockservice().getCenterPoint(),player, Color.ORANGE);
            Location leftLocation = autosit.getItemListener().getLeftLocation().get(player.getUniqueId());
            Location rightLocation = autosit.getItemListener().getRightLocation().get(player.getUniqueId());
            if(leftLocation!=null){
                ParticleBox.drawParticleBox(leftLocation,player, Color.RED);
            }
            if(rightLocation!=null){
                ParticleBox.drawParticleBox(rightLocation,player, Color.BLUE);
            }
        }
    }

    public Boolean getLookBlockTick() {
        return lookBlockTick;
    }

    public void setLookBlockTick(Boolean lookBlockTick, Player player) {
         this.lookBlockTick = lookBlockTick;
         this.player = player;
    }
}
