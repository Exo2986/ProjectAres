package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class VampirismPerk extends Perk {

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource().isLiving()) {

            LivingEntity owner = (LivingEntity) event.getSource().getTrueSource();

            owner.heal(event.getAmount()/2f);
        }
    }

    @Override
    public void onKill(ItemStack item, LivingDamageEvent event) {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource().isLiving()) {

            LivingEntity owner = (LivingEntity) event.getSource().getTrueSource();

            owner.heal(event.getAmount());
        }
    }
}
