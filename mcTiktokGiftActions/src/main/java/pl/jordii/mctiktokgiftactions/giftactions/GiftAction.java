// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.giftactions;

import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Zombie;
import org.bukkit.attribute.Attribute;
import org.bukkit.plugin.Plugin;
import pl.jordii.mctiktokgiftactions.McTiktokGiftActions;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.GameMode;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.ChatColor;
import pl.jordii.mctiktokgiftactions.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;

public class GiftAction implements GiftActionService
{
    @Override
    public void spawnChicken(final Player player, final String name) {
        final Chicken chicken = (Chicken)player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
        chicken.setCustomName(name);
        chicken.setAI(false);
    }
    
    @Override
    public void spawnBlackSkeletons(final Player player, final String name) {
        final WitherSkeleton witherSkeleton = (WitherSkeleton)player.getWorld().spawnEntity(player.getLocation(), EntityType.WITHER_SKELETON);
        witherSkeleton.setCustomName(name);
    }
    
    @Override
    public void spawnCreeper(final Player player, final String name) {
        final Creeper creeper = (Creeper)player.getWorld().spawnEntity(player.getLocation(), EntityType.CREEPER);
        creeper.setCustomName(name);
    }
    
    @Override
    public void giveChlebeek(final Player player) {
        player.getInventory().addItem(new ItemStack[] { new ItemBuilder(Material.BREAD).setDisplayName(ChatColor.GOLD + "Chleb").build() });
    }
    
    @Override
    public void giveEnderpearl(final Player player) {
        player.getInventory().addItem(new ItemStack[] { new ItemBuilder(Material.ENDER_PEARL).setDisplayName(ChatColor.AQUA + "Perelka").build() });
    }
    
    @Override
    public void giveKox(final Player player) {
        player.getInventory().addItem(new ItemStack[] { new ItemBuilder(Material.GOLDEN_APPLE, 1).setDisplayName(ChatColor.YELLOW + "Kox").build() });
    }
    
    @Override
    public void clearEq(final Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents((ItemStack[])null);
    }
    
    @Override
    public void giveTotem(final Player player) {
        player.getInventory().addItem(new ItemStack[] { new ItemBuilder(Material.TOTEM_OF_UNDYING).setDisplayName(ChatColor.RED + "Totem").build() });
    }
    
    @Override
    public void throwTnt(final Player player) {
        final TNTPrimed tnt = (TNTPrimed)player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
    }
    
    @Override
    public void setCowweb(final Player player) {
        player.getLocation().getBlock().setType(Material.COBWEB);
    }
    
    @Override
    public void setFreeze(final Player player) {
        GiftActionListeners.freezedPlayers.put(player.getUniqueId(), System.currentTimeMillis() / 1000L);
    }
    
    @Override
    public void kill(final Player player) {
        player.setHealth(0.0);
    }
    
    @Override
    public void gamemode(final Player player) {
        player.setGameMode(GameMode.CREATIVE);
        new BukkitRunnable() {
            public void run() {
                if (player != null) {
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
        }.runTaskLater((Plugin)McTiktokGiftActions.getMcTiktok(), 200L);
    }
    
    @Override
    public void addOneHeart(final Player player) {
        final double hpNow = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (hpNow + 2.0 >= 40.0) {
            return;
        }
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2.0);
    }
    
    @Override
    public void removeOneHeart(final Player player) {
        final double hpNow = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (hpNow - 2.0 <= 0.0) {
            return;
        }
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2.0);
    }
    
    @Override
    public void spawnZombie(final Player player, final String name) {
        final Zombie zombie = (Zombie)player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
        zombie.setCustomName(name);
    }
    
    @Override
    public void spawnFriendlyIronGolem(final Player player, final String name) {
        final IronGolem ironGolem = (IronGolem)player.getWorld().spawnEntity(player.getLocation(), EntityType.IRON_GOLEM);
        ironGolem.setPlayerCreated(true);
        ironGolem.setCustomName(name);
    }
}
