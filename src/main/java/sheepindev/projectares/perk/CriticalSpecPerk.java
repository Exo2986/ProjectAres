package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

public class CriticalSpecPerk extends Perk {

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) { //this is so hacky
        if (event.getEntity().world.isRemote()) return;
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity owner = (PlayerEntity) event.getSource().getTrueSource();

            LivingEntity target = event.getEntityLiving();

            event.setAmount(event.getAmount() * 1.15f);

            owner.world.playSound(null, owner.getPosX(), owner.getPosY(), owner.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, owner.getSoundCategory(), 1.0F, 1.0F);
            owner.onCriticalHit(target);

            CriticalHitEvent hitResult = new CriticalHitEvent(owner, target, 1.15f, true); //i feel like i should apologize for this
            MinecraftForge.EVENT_BUS.post(hitResult);
        }
    }
}
