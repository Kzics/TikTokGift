// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.util;

import java.util.List;
import java.util.Arrays;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder
{
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    
    public ItemBuilder(final Material material, final int subId) {
        this.itemStack = new ItemStack(material, 1, (short)subId);
        this.itemMeta = this.itemStack.getItemMeta();
    }
    
    public ItemBuilder(final Material material) {
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = this.itemStack.getItemMeta();
    }
    
    public ItemBuilder setDisplayName(final String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this;
    }
    
    public ItemBuilder addEnchant(final Enchantment enchantment, final int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }
    
    public ItemBuilder setGlow() {
        this.itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        this.itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        return this;
    }
    
    public ItemBuilder setLore(final String... lore) {
        this.itemMeta.setLore((List)Arrays.asList(lore));
        return this;
    }
    
    public ItemBuilder setAmount(final int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }
    
    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
