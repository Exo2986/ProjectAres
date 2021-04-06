package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class VampirismPerk extends Perk {
    @Override
    public String GetID() {
        return "vampirism";
    }

    @Override
    public void OnHit(ItemStack item, LivingDamageEvent event) {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource().isLiving()) {

            LivingEntity owner = (LivingEntity) event.getSource().getTrueSource();

            owner.heal(event.getAmount()/2f);
        }
    }

    @Override
    public void OnKill(ItemStack item, LivingDamageEvent event) {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource().isLiving()) {

            LivingEntity owner = (LivingEntity) event.getSource().getTrueSource();

            owner.heal(event.getAmount());
        }
    }
}
