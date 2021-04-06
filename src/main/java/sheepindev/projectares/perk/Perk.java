package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import java.util.AbstractMap;

public class Perk {
    public String GetID() {return "";}

    public void OnTick(ItemStack item, Entity owner) {}

    public void OnKill(ItemStack item, LivingDamageEvent event) {} //Event.Entity is the target, not the owner

    public void OnOwnerDamage(ItemStack item, LivingDamageEvent event) {}

    public void OnCrit(ItemStack item, CriticalHitEvent event) {}

    public void OnHit(ItemStack item, LivingDamageEvent event) { } //Event.Entity is the target, not the owner

    public void OnRightClick(ItemStack item, Entity owner) {}

    public void OnOwnerDeath(ItemStack item, Entity owner) {}

    public void OnSwing(ItemStack item, Entity owner) {}

    public AbstractMap.SimpleEntry<Attribute,AttributeModifier> GetAttributeModifiers(ItemStack item) { return null; }
}
