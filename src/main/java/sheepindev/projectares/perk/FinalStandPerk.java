package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import static sheepindev.projectares.util.ProjectAresConstants.*;

public class FinalStandPerk extends CancelDeathPerk {

    @Override
    protected String getNBTTagName() {
        return NBT_TAG_NAME_FINAL_STAND_COOLDOWN;
    }

    @Override
    protected int getCooldown() {
        return COOLDOWN_FINAL_STAND;
    }

    @Override
    protected void doAbility(LivingEntity living, LivingDeathEvent event) {
        living.setHealth(1);
        living.addPotionEffect(new EffectInstance(Effects.REGENERATION, 20*3, 2));

        living.world.playSound(null, living.getPosX(), living.getPosY(), living.getPosZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 1f, 1.3F);
    }
}
