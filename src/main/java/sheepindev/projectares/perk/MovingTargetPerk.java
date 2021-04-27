package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sheepindev.projectares.registry.RegisterEffects;

import java.util.ArrayList;
import java.util.HashMap;

public class MovingTargetPerk extends Perk{

    protected HashMap<Integer, Vector3d> positionLastFrame = new HashMap<>();

    @Override
    public void onTick(ItemStack item, Entity owner) {
        if (owner.world.isRemote) return;

        if (owner instanceof LivingEntity && positionLastFrame.containsKey(owner.getEntityId())) {
            LivingEntity living = (LivingEntity) owner;

            if (!positionLastFrame.get(owner.getEntityId()).equals(owner.getPositionVec())) {
                living.addPotionEffect(new EffectInstance(RegisterEffects.MOVING_TARGET_EFFECT.get(), 1, 0, false, false));
            }

        } else if (!positionLastFrame.containsKey(owner.getEntityId())) {
            positionLastFrame.put(owner.getEntityId(), owner.getPositionVec());
        }

        positionLastFrame.put(owner.getEntityId(), owner.getPositionVec());
    }
}
