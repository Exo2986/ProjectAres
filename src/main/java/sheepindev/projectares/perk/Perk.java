package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.registries.ForgeRegistryEntry;
import sheepindev.projectares.enums.PerkCompatibilityEnum;
import sheepindev.projectares.registry.RegisterEffects;
import sheepindev.projectares.registry.RegisterPerks;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Perk extends ForgeRegistryEntry<Perk> {
    protected ArrayList<PerkCompatibilityEnum> compatibilityTags;
    protected ArrayList<Perk> compatiblePerks;

    protected void instantiateIfNeeded() {
        if (compatiblePerks == null) {
            compatiblePerks = new ArrayList<>();
            compatibilityTags = new ArrayList<>();
            populateCompatible();
        }
    }

    public ArrayList<Perk> getCompatiblePerks() {
        instantiateIfNeeded();
        return compatiblePerks;
    }

    public ArrayList<PerkCompatibilityEnum> getCompatibilityTags() {
        instantiateIfNeeded();
        return compatibilityTags;
    }

    public void populateCompatible() {
        this.compatiblePerks.addAll(Arrays.asList(RegisterPerks.getRegisteredPerks()));
        this.compatiblePerks.remove(RegisterPerks.getRegisteredPerk(this.getRegistryName()));
    }

    public void doCompatabilityTagChecks() {
        compatiblePerks.removeIf((perk) -> {
            AtomicBoolean match = new AtomicBoolean(false);
            ArrayList<PerkCompatibilityEnum> tags = perk.getCompatibilityTags();

            getCompatibilityTags().forEach((tag) -> {
                if (tags.contains(tag)) {
                    match.set(true);
                }
            });
            return match.get();
        });
    }

    public void onTick(ItemStack item, Entity owner) {}

    public void onKill(ItemStack item, LivingDeathEvent event) {} //Event.Entity is the target, not the owner

    public void onIndirectKill(ItemStack item, LivingDeathEvent event) {}

    public void onOwnerDamage(ItemStack item, LivingDamageEvent event) {}

    public void onCrit(ItemStack item, CriticalHitEvent event) {}

    public void onHit(ItemStack item, LivingDamageEvent event) { } //Event.Entity is the target, not the owner

    public void onRightClick(ItemStack item, Entity owner) {}

    public void onOwnerDeath(ItemStack item, Entity owner, LivingDeathEvent event) {}

    public void onSwing(ItemStack item, Entity owner) {}

    public boolean shouldShowDurabilityBar(ItemStack item) { return false; }

    public double getDurability(ItemStack item) { return 0; }

    public boolean isIntrinsic() { return false; }

    public AbstractMap.SimpleEntry<Attribute,AttributeModifier> getAttributeModifiers(ItemStack item) { return null; }
}
