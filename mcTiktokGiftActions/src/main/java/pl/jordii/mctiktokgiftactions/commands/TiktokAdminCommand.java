// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.commands;

import pl.jordii.mctiktokgiftactions.rest.requests.StatusType;
import pl.jordii.mctiktokgiftactions.model.Streamer;
import pl.jordii.mctiktokgiftactions.model.StreamStatus;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import pl.jordii.mctiktokgiftactions.UserRepository;
import org.bukkit.command.CommandExecutor;

public class TiktokAdminCommand implements CommandExecutor
{
    private final UserRepository userRepository;
    
    public TiktokAdminCommand(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player player = (Player)sender;
        if (!player.hasPermission("mctiktok.command.tiktokadmin")) {
            player.sendMessage("§cBrak permisji §fmctiktok.command.tiktokadmin");
            return true;
        }
        if ((args[0].equalsIgnoreCase("register") || args[0].equalsIgnoreCase("unregister")) && args.length != 2) {
            player.sendMessage("Niepoprawne uzycie komendy");
            return true;
        }
        if (args.length != 1) {
            player.sendMessage("Niepoprawne uzycie komendy");
            return true;
        }
        final Streamer streamer = this.userRepository.getStreamer(player.getUniqueId());
        final String s = args[0];
        switch (s) {
            case "register": {
                if (streamer == null) {
                    this.userRepository.addStreamer(new Streamer(player.getUniqueId(), player.getName(), args[1], StreamStatus.OFF, 0, "None", "None"));
                    break;
                }
                player.sendMessage("Jestes juz na liscie streamerow");
                player.sendMessage(streamer.toString());
                break;
            }
            case "unregister": {
                if (streamer == null) {
                    player.sendMessage("Nie ma cie na liscie streamerow");
                    break;
                }
                break;
            }
            case "start": {
                if (streamer == null) {
                    player.sendMessage("Nie ma cie na liscie streamerow");
                    break;
                }
                if (streamer.getStreamStatus() == StreamStatus.ON) {
                    player.sendMessage("Masz juz wlaczonego streama.");
                    break;
                }
                this.userRepository.getHandleStatusRequest().sendRequest(streamer.getTiktokName(), StatusType.START, callback -> player.sendMessage(callback.toString()));
                break;
            }
            case "stop": {
                if (streamer == null) {
                    player.sendMessage("Nie ma cie na liscie streamerow");
                    break;
                }
                if (streamer.getStreamStatus() == StreamStatus.OFF) {
                    player.sendMessage("Nie masz wlaczonego streama.");
                    break;
                }
                this.userRepository.getHandleStatusRequest().sendRequest(streamer.getTiktokName(), StatusType.STOP, callback -> player.sendMessage(callback.toString()));
                break;
            }
            case "list": {
                player.sendMessage("Opcja zostanie dodana w nastepnym update");
                break;
            }
            case "info": {
                player.sendMessage(streamer.toString());
                break;
            }
        }
        return false;
    }
}
