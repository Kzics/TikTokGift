// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.giftactions;

import java.util.HashMap;
import org.bukkit.event.EventHandler;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.event.player.PlayerMoveEvent;
import java.util.UUID;
import java.util.Map;
import org.bukkit.event.Listener;

public class GiftActionListeners implements Listener
{
    protected static Map<UUID, Long> freezedPlayers;
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        if (GiftActionListeners.freezedPlayers.containsKey(e.getPlayer().getUniqueId())) {
            if (GiftActionListeners.freezedPlayers.get(e.getPlayer().getUniqueId()) + 5L >= System.currentTimeMillis() / 1000L) {
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent("§cNie mozesz sie ruszac! §7(" + (GiftActionListeners.freezedPlayers.get(e.getPlayer().getUniqueId()) + 5L - System.currentTimeMillis() / 1000L) + ")"));
                e.setCancelled(true);
            }
            else {
                GiftActionListeners.freezedPlayers.remove(e.getPlayer().getUniqueId());
            }
        }
    }
    
    static {
        GiftActionListeners.freezedPlayers = new HashMap<UUID, Long>();
    }
}
