package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;

public class CriticalSpecPerk extends Perk {

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) { //this is so hacky
        if (event.getEntity().world.isRemote()) return;
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity owner = (PlayerEntity) event.getSource().getTrueSource();

            LivingEntity target = event.getEntityLiving();

            //this part is copied from vanilla don't blame me
            float f2 = owner.getCooledAttackStrength(0.5F);
            boolean flag = f2 > 0.9f;
            boolean flag2 = flag && owner.fallDistance > 0.0F && !owner.isOnGround() && !owner.isOnLadder() && !owner.isInWater() && !owner.isPotionActive(Effects.BLINDNESS) && !owner.isPassenger() && target != null;
            flag2 = flag2 && !owner.isSprinting();

            if (flag2) return;

            event.setAmount(event.getAmount() * 1.15f);

            owner.world.playSound(null, owner.getPosX(), owner.getPosY(), owner.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, owner.getSoundCategory(), 1.0F, 1.0F);
            owner.onCriticalHit(target);

            CriticalHitEvent hitResult = new CriticalHitEvent(owner, target, 1.15f, true); //i feel like i should apologize for this
            MinecraftForge.EVENT_BUS.post(hitResult);
        }
    }
}
