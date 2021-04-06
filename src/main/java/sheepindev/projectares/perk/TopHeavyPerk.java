package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import static sheepindev.projectares.ProjectAres.LOGGER;

public class TopHeavyPerk extends Perk {
    @Override
    public String GetID() {
        return "top_heavy";
    }

    @Override
    public void OnHit(ItemStack item, LivingDamageEvent event) {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource().isLiving()) {
            LivingEntity owner = (LivingEntity) event.getSource().getTrueSource();

            float modifier = (float) owner.getPositionVec().distanceTo(event.getEntityLiving().getPositionVec());
            modifier /= 2; //Decrease returns at distance
            modifier = Math.max(modifier, 0.8f); //Do not let it get below 0.8

            event.setAmount((event.getAmount() * modifier));



            LOGGER.debug("Modifier: " + modifier + ", Damage Dealt: " + event.getAmount());
        }
    }
}
