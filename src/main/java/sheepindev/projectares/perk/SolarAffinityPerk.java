package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;

import java.util.List;

public class SolarAffinityPerk extends Perk {

    public boolean isIntrinsic() { return true; }

    @Override
    public void onTick(ItemStack item, Entity owner) {
        if (owner.world.isRemote()) return;

        if (owner.world.getGameTime() % 40 == 0) {
            if (owner.world.canBlockSeeSky(owner.getPosition())) {
                int adjustedLightLevel = owner.world.getLightFor(LightType.SKY, owner.getPosition())-owner.world.getSkylightSubtracted();

                System.out.println(adjustedLightLevel);

                if (adjustedLightLevel >= 10) {
                    LivingEntity entity = (LivingEntity) owner;
                    entity.setHealth(entity.getHealth()+1);
                }
            }
        }
    }
}
