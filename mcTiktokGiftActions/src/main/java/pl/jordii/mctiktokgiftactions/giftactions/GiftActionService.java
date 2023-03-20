// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.giftactions;

import org.bukkit.entity.Player;

public interface GiftActionService
{
    void spawnZombie(final Player p0, final String p1);
    
    void spawnFriendlyIronGolem(final Player p0, final String p1);
    
    void spawnChicken(final Player p0, final String p1);
    
    void spawnBlackSkeletons(final Player p0, final String p1);
    
    void spawnCreeper(final Player p0, final String p1);
    
    void giveChlebeek(final Player p0);
    
    void giveEnderpearl(final Player p0);
    
    void giveKox(final Player p0);
    
    void clearEq(final Player p0);
    
    void giveTotem(final Player p0);
    
    void throwTnt(final Player p0);
    
    void setCowweb(final Player p0);
    
    void setFreeze(final Player p0);
    
    void kill(final Player p0);
    
    void gamemode(final Player p0);
    
    void addOneHeart(final Player p0);
    
    void removeOneHeart(final Player p0);
}
