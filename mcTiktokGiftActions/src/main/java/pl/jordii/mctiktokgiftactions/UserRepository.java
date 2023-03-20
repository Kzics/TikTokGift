// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions;

import pl.jordii.mctiktokgiftactions.rest.objects.Gift;
import pl.jordii.mctiktokgiftactions.rest.objects.Message;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import pl.jordii.mctiktokgiftactions.util.ServerMainThread;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.jordii.mctiktokgiftactions.rest.requests.StatusType;
import pl.jordii.mctiktokgiftactions.model.StreamStatus;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.HashMap;
import pl.jordii.mctiktokgiftactions.model.Streamer;
import java.util.UUID;
import java.util.Map;
import pl.jordii.mctiktokgiftactions.rest.requests.HandleStatusRequest;
import pl.jordii.mctiktokgiftactions.rest.requests.ChatMessageRequest;
import pl.jordii.mctiktokgiftactions.rest.requests.GiftRequest;
import pl.jordii.mctiktokgiftactions.giftactions.GiftActionHandler;
import pl.jordii.mctiktokgiftactions.configuration.GiftCodesConfig;
import org.bukkit.scheduler.BukkitScheduler;
import java.util.concurrent.ScheduledExecutorService;
import org.bukkit.event.Listener;

public class UserRepository implements Listener
{
    private final ScheduledExecutorService scheduledExecutorService;
    private final BukkitScheduler bukkitScheduler;
    private final GiftCodesConfig giftCodesConfig;
    private GiftActionHandler giftActionHandler;
    private GiftRequest giftRequest;
    private ChatMessageRequest chatMessageRequest;
    private HandleStatusRequest handleStatusRequest;
    private Map<UUID, Streamer> streamers;
    
    public UserRepository(final ScheduledExecutorService scheduledExecutorService, final BukkitScheduler bukkitScheduler, final GiftCodesConfig giftCodesConfig) {
        this.giftActionHandler = new GiftActionHandler();
        this.giftRequest = new GiftRequest();
        this.chatMessageRequest = new ChatMessageRequest();
        this.handleStatusRequest = new HandleStatusRequest();
        this.streamers = new HashMap<UUID, Streamer>();
        this.scheduledExecutorService = scheduledExecutorService;
        this.bukkitScheduler = bukkitScheduler;
        this.giftCodesConfig = giftCodesConfig;
    }
    
    @EventHandler
    public void handleJoin(final PlayerJoinEvent event) {
    }
    
    @EventHandler
    public void handleQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (this.streamers.containsKey(player.getUniqueId())) {
            final Streamer streamer = this.streamers.get(player.getUniqueId());
            if (streamer.getStreamStatus() == StreamStatus.ON) {
                this.handleStatusRequest.sendRequest(streamer.getTiktokName(), StatusType.STOP, callback -> System.out.println("STREAMER QUIT WITH ENABLED LIVE, TRYING TURN OFF LIVE.. CALLBACK: " + callback));
            }
            this.streamers.remove(player.getUniqueId());
        }
    }
    
    public void start() {
        this.scheduledExecutorService.scheduleWithFixedDelay(() -> {
            if (this.streamers.isEmpty()) {
                return;
            }
            for (Streamer streamer : this.streamers.values()) {
                Player player = Bukkit.getPlayer(streamer.getPlayerUuid());
                if (streamer.getStreamStatus() != StreamStatus.ON) continue;
                this.giftRequest.sendRequest(streamer.getTiktokName(), callback -> callback.values().forEach(g -> ServerMainThread.RunParallel.run(() -> {
                    int giftNumber = this.giftCodesConfig.getNumberFromCode(g.data.giftId);
                    if (giftNumber != -1) {
                        this.giftActionHandler.handleGift(giftNumber, player, (Gift)g);
                        streamer.setLastGifter(g.uniqueId);
                    }
                })));
                this.chatMessageRequest.sendRequest(streamer.getTiktokName(), callback -> callback.values().forEach(g -> player.sendMessage(ChatColor.GRAY + g.uniqueId + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + g.data.comment)));
            }
        }, 0L, 1L, TimeUnit.SECONDS);
    }
    
    public GiftRequest getGiftRequest() {
        return this.giftRequest;
    }
    
    public ChatMessageRequest getChatMessageRequest() {
        return this.chatMessageRequest;
    }
    
    public HandleStatusRequest getHandleStatusRequest() {
        return this.handleStatusRequest;
    }
    
    public void addStreamer(final Streamer streamer) {
        this.streamers.put(streamer.getPlayerUuid(), streamer);
    }
    
    public void removeStreamer(final UUID uuid) {
        this.streamers.remove(uuid);
    }
    
    public void replaceStreamer(final UUID uuid, final Streamer streamer) {
        this.streamers.replace(uuid, streamer);
    }
    
    public Streamer getStreamer(final UUID uuid) {
        if (this.streamers.containsKey(uuid)) {
            return this.streamers.get(uuid);
        }
        return null;
    }
    
    public int getStreamersAmount() {
        return this.streamers.size();
    }
}
