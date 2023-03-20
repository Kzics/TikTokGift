// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.commands;

import org.bukkit.ChatColor;
import pl.jordii.mctiktokgiftactions.rest.requests.StatusType;
import pl.jordii.mctiktokgiftactions.model.Streamer;
import pl.jordii.mctiktokgiftactions.model.StreamStatus;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatMessageType;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import pl.jordii.mctiktokgiftactions.UserRepository;
import org.bukkit.command.CommandExecutor;

public class TiktokCommand implements CommandExecutor
{
    private final UserRepository userRepository;
    private final Map<UUID, Long> commandCooldown;
    
    public TiktokCommand(final UserRepository userRepository) {
        this.commandCooldown = new HashMap<UUID, Long>();
        this.userRepository = userRepository;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player player = (Player)sender;
        if (!player.hasPermission("mctiktok.command.tiktok")) {
            player.sendMessage("§cYou do not have required permission: §fmctiktok.command.tiktok");
            return true;
        }
        if (args.length == 0 || args.length > 2) {
            Arrays.asList("", "§eMcTiktok §f2.0 §7by §8Jordan \"Jordii\" Mruczynski §9§o(Jordii#7622)", "§6/mctiktok register <tiktokName> §8- §7register player as streamer", "§6/mctiktok unregister §8- §7unregister yourself", "§6/mctiktok start §8- §astart §7your stream", "§6/mctiktok stop §8- §cstop §7your stream", "§6/mctiktok info §8- §7info about you", "§6/mctiktok list §8- §7streamers list", "").forEach(x -> player.sendMessage(x));
            return true;
        }
        if (args[0].equalsIgnoreCase("register") && args.length != 2) {
            player.sendMessage("");
            player.sendMessage("§cMcTiktok error:");
            player.sendMessage("§7Incorrect command usage, please use §f/mctiktok register <yourTiktokName>");
            return true;
        }
        if (args[0].equalsIgnoreCase("start") || args[0].equalsIgnoreCase("stop")) {
            if (this.commandCooldown.containsKey(player.getUniqueId())) {
                if (this.commandCooldown.get(player.getUniqueId()) + 30L >= System.currentTimeMillis() / 1000L) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent("§cPlease wait before use this command §7(" + (this.commandCooldown.get(player.getUniqueId()) + 30L - System.currentTimeMillis() / 1000L) + ")s"));
                    return true;
                }
                this.commandCooldown.remove(player.getUniqueId());
            }
            else {
                this.commandCooldown.put(player.getUniqueId(), System.currentTimeMillis() / 1000L);
            }
        }
        final Streamer streamer = this.userRepository.getStreamer(player.getUniqueId());
        final String s = args[0];
        switch (s) {
            case "register": {
                if (streamer == null) {
                    this.userRepository.addStreamer(new Streamer(player.getUniqueId(), player.getName(), args[1], StreamStatus.OFF, 0, "None", "None"));
                    player.sendMessage("");
                    player.sendMessage("§aMcTiktok success:");
                    player.sendMessage("§You have been registered as streamer!");
                    break;
                }
                player.sendMessage("");
                player.sendMessage("§cMcTiktok error:");
                player.sendMessage("§7You are already on streamer list, please unregister first yourself.");
                player.sendMessage(streamer.toString());
                break;
            }
            case "unregister": {
                if (streamer == null) {
                    player.sendMessage("");
                    player.sendMessage("§cMcTiktok error:");
                    player.sendMessage("§7You are not the streamer, please register first yourself.");
                    break;
                }
                this.userRepository.removeStreamer(player.getUniqueId());
                if (streamer.getStreamStatus() == StreamStatus.ON) {
                    this.userRepository.getHandleStatusRequest().sendRequest(streamer.getTiktokName(), StatusType.STOP, callback -> System.out.println("UNREGISTER STREAMER WITH ENABLED LIVE, HANDLE STOP REQUEST: " + callback));
                }
                player.sendMessage("");
                player.sendMessage("§aMcTiktok success:");
                player.sendMessage("§7You have been unregistered.");
                break;
            }
            case "start": {
                if (streamer == null) {
                    player.sendMessage("");
                    player.sendMessage("§cMcTiktok error:");
                    player.sendMessage("§7You are not the streamer, please register first yourself.");
                    break;
                }
                if (streamer.getStreamStatus() == StreamStatus.ON) {
                    player.sendMessage("");
                    player.sendMessage("§cMcTiktok error:");
                    player.sendMessage("§7You have already stream, please stop it first.");
                    break;
                }
                streamer.setStreamStatus(StreamStatus.ON);
                this.userRepository.replaceStreamer(player.getUniqueId(), streamer);
                player.sendMessage("");
                player.sendMessage("§3McTiktok information:");
                player.sendMessage("§7Trying to connect with your stream session...");
                this.userRepository.getHandleStatusRequest().sendRequest(streamer.getTiktokName(), StatusType.START, callback -> {
                    player.sendMessage(callback.toString());
                    if (callback.equalsIgnoreCase("ok")) {
                        player.sendMessage("");
                        player.sendMessage("§aMcTiktok success:");
                        player.sendMessage("§7You have started your stream!");
                    } else {
                        player.sendMessage(callback.toString());
                    }
                });
                break;
            }
            case "stop": {
                if (streamer == null) {
                    player.sendMessage("");
                    player.sendMessage("§cMcTiktok error:");
                    player.sendMessage("§7You are not the streamer, please register first yourself.");
                    break;
                }
                if (streamer.getStreamStatus() == StreamStatus.OFF) {
                    player.sendMessage("");
                    player.sendMessage("§cMcTiktok error:");
                    player.sendMessage("§7You do not have enabled stream, please start it first.");
                    break;
                }
                streamer.setStreamStatus(StreamStatus.OFF);
                this.userRepository.replaceStreamer(player.getUniqueId(), streamer);
                this.userRepository.getHandleStatusRequest().sendRequest(streamer.getTiktokName(), StatusType.STOP, callback -> {
                    if (callback.equalsIgnoreCase("ok")) {
                        player.sendMessage("");
                        player.sendMessage("§aMcTiktok success:");
                        player.sendMessage("§7You have stopped your stream!");
                    } else {
                        player.sendMessage(callback.toString());
                    }
                });
                break;
            }
            case "list": {
                player.sendMessage("§3This option will be in next update..");
                break;
            }
            case "info": {
                if (streamer != null) {
                    player.sendMessage(ChatColor.BLUE + streamer.toString());
                    break;
                }
                break;
            }
            default: {
                Arrays.asList("", "§eMcTiktok §f2.0 §7by §8Jordan \"Jordii\" Mruczynski §9§o(Jordii#7622)", "§6/mctiktok register <tiktokName> §8- §7register player as streamer", "§6/mctiktok unregister §8- §7unregister yourself", "§6/mctiktok start §8- §astart §7your stream", "§6/mctiktok stop §8- §cstop §7your stream", "§6/mctiktok info §8- §7info about you", "§6/mctiktok list §8- §7streamers list", "").forEach(x -> player.sendMessage(x));
                break;
            }
        }
        return false;
    }
}
