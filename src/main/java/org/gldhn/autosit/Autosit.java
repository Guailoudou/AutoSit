package org.gldhn.autosit;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.gldhn.autosit.command.AsitCommand;
import org.gldhn.autosit.command.AsitTabComplete;
import org.gldhn.autosit.listener.itemListener;
import org.gldhn.autosit.service.SitBlockService;
import org.gldhn.autosit.service.TickService;
import org.gldhn.autosit.service.dataYmlService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Autosit extends JavaPlugin {
    private SitBlockService sitblockservice;
    private TickService tickService;
    private dataYmlService dataYmlService;
    private itemListener itemListener;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("autosit 已加载！");
        Objects.requireNonNull(getCommand("asit")).setExecutor(new AsitCommand(this));
        Objects.requireNonNull(getCommand("asit")).setTabCompleter(new AsitTabComplete(this));
        getServer().getPluginManager().registerEvents(itemListener = new itemListener(this),this);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            // 这里是每tick都会执行的代码
            tickService.tick();
        }, 0L, 5L);
    }

    @Override
    public void onDisable() {
        getLogger().info("autosit 已卸载！");
        // Plugin shutdown logic
    }
    public void onLoad() {
        tickService = new TickService(this);
        sitblockservice = new SitBlockService(this);
        dataYmlService = new dataYmlService(this);

    }
    public SitBlockService getSitblockservice() {
        return sitblockservice;
    }
    public TickService getTickService() {
        return tickService;
    }

    public dataYmlService getDataYmlService() {
        return dataYmlService;
    }

    public itemListener getItemListener() {
        return itemListener;
    }
}
