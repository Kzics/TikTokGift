// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.jordii.mctiktokgiftactions.commands.TiktokCommand;
import pl.jordii.mctiktokgiftactions.configuration.GiftCodesConfig;
import pl.jordii.mctiktokgiftactions.giftactions.GiftActionListeners;
import pl.jordii.mctiktokgiftactions.placeholderapi.PlaceholderInject;
import pl.jordii.mctiktokgiftactions.util.FileCopy;

public final class McTiktokGiftActions extends JavaPlugin
{
    private static ScheduledExecutorService executorServiceScheduled;
    private static ExecutorService executorService;
    private UserRepository userRepository;
    private GiftCodesConfig giftCodesConfig;
    private File giftsConfig;
    private File licenseConfig;
    private final String gifstConfigFileName = "gifts.json";
    private final String licenseConfigFileName = "license.json";
    
    public void onEnable() {
        this.giftCodesConfig = new GiftCodesConfig();
        this.setupFiles();
        try {
            this.giftCodesConfig.loadCodesFromJson(this.getDataFolder() + "/" + gifstConfigFileName);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        (this.userRepository = new UserRepository(McTiktokGiftActions.executorServiceScheduled, Bukkit.getScheduler(), this.giftCodesConfig)).start();
        this.getCommand("mctiktok").setExecutor(new TiktokCommand(this.userRepository));
        Bukkit.getPluginManager().registerEvents(this.userRepository, this);
        Bukkit.getPluginManager().registerEvents(new GiftActionListeners(), this);
        new PlaceholderInject(this.userRepository).register();
    }
    
    public void onDisable() {
    }
    
    public static ExecutorService getExecutorService() {
        return McTiktokGiftActions.executorService;
    }
    
    public static ScheduledExecutorService getExecutorServiceScheduled() {
        return McTiktokGiftActions.executorServiceScheduled;
    }
    
    public static McTiktokGiftActions getMcTiktok() {
        return getPlugin(McTiktokGiftActions.class);
    }
    
    private void setupFiles() {
        final File dataFolder = this.getDataFolder();
        this.giftsConfig = new File(dataFolder, gifstConfigFileName);
        this.licenseConfig = new File(dataFolder, licenseConfigFileName);
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        FileCopy.createFileFromResource(gifstConfigFileName, this.giftsConfig, this);
        FileCopy.createFileFromResource(licenseConfigFileName, this.licenseConfig, this);
    }
    
    static {
        McTiktokGiftActions.executorServiceScheduled = Executors.newScheduledThreadPool(1);
        McTiktokGiftActions.executorService = Executors.newCachedThreadPool();
    }
}
