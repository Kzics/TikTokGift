// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.placeholderapi;

import pl.jordii.mctiktokgiftactions.model.Streamer;
import pl.jordii.mctiktokgiftactions.model.StreamStatus;
import org.bukkit.entity.Player;
import pl.jordii.mctiktokgiftactions.UserRepository;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderInject extends PlaceholderExpansion
{
    private final UserRepository userRepository;
    
    public PlaceholderInject(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public String getIdentifier() {
        return "mctiktok";
    }
    
    public String getAuthor() {
        return "Jordii";
    }
    
    public String getVersion() {
        return "2.0";
    }
    
    public String onPlaceholderRequest(final Player player, final String identifier) {
        if (player == null) {
            return "";
        }
        final Streamer streamer = this.userRepository.getStreamer(player.getUniqueId());
        switch (identifier) {
            case "streamStatus": {
                return (streamer == null) ? StreamStatus.OFF.name() : streamer.getStreamStatus().name();
            }
            case "streamerName": {
                return (streamer == null) ? "None" : streamer.getTiktokName();
            }
            case "watchers": {
                return (streamer == null) ? "0" : String.valueOf(streamer.getWatchersCount());
            }
            case "lastGifter": {
                return (streamer == null) ? "None" : streamer.getLastGifter();
            }
            case "lastGift": {
                return (streamer == null) ? "None" : streamer.getLastGift();
            }
            case "streamers": {
                return String.valueOf(this.userRepository.getStreamersAmount());
            }
            default: {
                return null;
            }
        }
    }
}
