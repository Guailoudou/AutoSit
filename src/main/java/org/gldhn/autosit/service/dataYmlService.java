package org.gldhn.autosit.service;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.gldhn.autosit.Autosit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class dataYmlService {
    private final Autosit autosit;
    private  YamlConfiguration ymaldata;
    private File ymlFile;
    public dataYmlService(Autosit autosit) {
        this.autosit = autosit;
        loadYml();
    }
    public void setYmaldata(YamlConfiguration ymaldata) {
        this.ymaldata = ymaldata;
    }
    public void saveYmaldata() {
        try {
            ymaldata.save(ymlFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public YamlConfiguration getYmaldata() {
        return ymaldata;
    }

    private void loadYml() {
        this.ymlFile = new File(autosit.getDataFolder(), "data.yml");
        reloadYmlFile();
        autosit.getLogger().info("data.yml已加载");
    }
    public void reloadYmlFile() {
        ymaldata = YamlConfiguration.loadConfiguration(ymlFile);
        autosit.getSitblockservice().setCenterPoint(ymaldata.getLocation("centerpoint"));
        autosit.getSitblockservice().setBlockLocations((List<Location>) ymaldata.getList("blocklocations", new ArrayList<>()));
    }
}
