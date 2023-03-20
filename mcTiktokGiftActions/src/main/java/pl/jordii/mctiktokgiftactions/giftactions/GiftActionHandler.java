// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.giftactions;

import pl.jordii.mctiktokgiftactions.rest.objects.Gift;
import org.bukkit.entity.Player;

public class GiftActionHandler
{
    private final GiftAction giftAction;
    
    public GiftActionHandler() {
        this.giftAction = new GiftAction();
    }
    
    public void handleGift(final int giftNumber, final Player player, final Gift gift) {
        player.sendTitle("", "§e" + gift.uniqueId + " §7wys\u0142a\u0142 §f" + gift.data.giftName);
        player.sendMessage("§e" + gift.uniqueId + " §7wys\u0142a\u0142 §f" + gift.data.giftName);
        switch (giftNumber) {
            case 1: {
                this.giftAction.spawnZombie(player, gift.uniqueId);
                break;
            }
            case 2: {
                this.giftAction.setCowweb(player);
                break;
            }
            case 3: {
                this.giftAction.giveChlebeek(player);
                break;
            }
            case 4: {
                this.giftAction.spawnBlackSkeletons(player, gift.uniqueId);
                break;
            }
            case 5: {
                this.giftAction.spawnCreeper(player, gift.uniqueId);
                break;
            }
            case 6: {
                this.giftAction.giveEnderpearl(player);
                break;
            }
            case 7: {
                this.giftAction.giveKox(player);
                break;
            }
            case 8: {
                this.giftAction.clearEq(player);
                break;
            }
            case 9: {
                this.giftAction.giveTotem(player);
                break;
            }
            case 10: {
                this.giftAction.throwTnt(player);
                break;
            }
            case 11: {
                this.giftAction.setFreeze(player);
                break;
            }
            case 12: {
                this.giftAction.kill(player);
                break;
            }
            case 13: {
                this.giftAction.gamemode(player);
                break;
            }
            case 14: {
                this.giftAction.addOneHeart(player);
                break;
            }
            case 15: {
                this.giftAction.removeOneHeart(player);
                break;
            }
            case 16: {
                this.giftAction.spawnFriendlyIronGolem(player, gift.uniqueId);
                break;
            }
        }
    }
}
