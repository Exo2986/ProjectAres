package sheepindev.projectares.perk;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import static sheepindev.projectares.util.ProjectAresConstants.*;

public class ChorusImbuedPerk extends CancelDeathPerk {
    @Override
    protected String getNBTTagName() {
        return NBT_TAG_NAME_CHORUS_IMBUED_COOLDOWN;
    }

    @Override
    protected int getCooldown() {
        return COOLDOWN_CHORUS_IMBUED;
    }

    @Override
    protected void doAbility(LivingEntity living, LivingDeathEvent event) {
        living.setHealth(1);
        doTeleport(living);
    }

    protected void doTeleport(LivingEntity living) { //copied from vanilla don't blame me
        if (living.world.isRemote()) return;

        double d0 = living.getPosX();
        double d1 = living.getPosY();
        double d2 = living.getPosZ();

        for(int i = 0; i < 16; ++i) {
            double d3 = living.getPosX() + (living.getRNG().nextDouble() - 0.5D) * 16.0D;
            double d4 = MathHelper.clamp(living.getPosY() + (double)(living.getRNG().nextInt(16) - 8), 0.0D, living.world.func_234938_ad_() - 1);
            double d5 = living.getPosZ() + (living.getRNG().nextDouble() - 0.5D) * 16.0D;
            if (living.isPassenger()) {
                living.stopRiding();
            }

            if (living.attemptTeleport(d3, d4, d5, true)) {
                SoundEvent soundevent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                living.world.playSound(null, d0, d1, d2, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                living.playSound(soundevent, 1.0F, 1.0F);
                break;
            }
        }
    }
}
