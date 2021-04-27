package sheepindev.projectares.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sheepindev.projectares.item.PerkItem;
import sheepindev.projectares.util.PerkItemHelper;

public class HostilePerkEffect extends Effect {
    protected LivingEntity sourceEntity;

    protected HostilePerkEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    public void setSourceEntity(LivingEntity source) {
        this.sourceEntity = source;
    }

    public ResourceLocation getSourcePerk() {
        return new ResourceLocation("dababy");
    }

    protected void checkIndirectDeathEvent(LivingEntity target, float damage) {
        if (!target.isAlive() || target.getHealth() - damage <= 0) {
            ItemStack held = sourceEntity.getHeldItemMainhand();
            if (PerkItemHelper.isPerkItemAndHasPerk(held, getSourcePerk())){
                PerkItem item = (PerkItem) held.getItem();

                LivingDeathEvent event = new LivingDeathEvent(target, DamageSource.causeMobDamage(sourceEntity));
                item.firePerkEvent(held, (a) -> a.onIndirectKill(held, event));

                target.removePotionEffect(this);
            }
        }
    }
}
