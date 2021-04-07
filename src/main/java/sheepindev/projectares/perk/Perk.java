package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.AbstractMap;

public class Perk extends ForgeRegistryEntry<Perk> {

    public void onTick(ItemStack item, Entity owner) {}

    public void onKill(ItemStack item, LivingDeathEvent event) {} //Event.Entity is the target, not the owner

    public void onOwnerDamage(ItemStack item, LivingDamageEvent event) {}

    public void onCrit(ItemStack item, CriticalHitEvent event) {}

    public void onHit(ItemStack item, LivingDamageEvent event) { } //Event.Entity is the target, not the owner

    public void onRightClick(ItemStack item, Entity owner) {}

    public void onOwnerDeath(ItemStack item, Entity owner) {}

    public void onSwing(ItemStack item, Entity owner) {}

    public AbstractMap.SimpleEntry<Attribute,AttributeModifier> getAttributeModifiers(ItemStack item) { return null; }
}
