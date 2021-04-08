package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class SurroundedPerk extends Perk{

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) {
        if (event.getEntity().world.isRemote()) return;
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity owner = (LivingEntity) event.getSource().getTrueSource();

            World world = owner.world;
            List<Entity> found = world.getEntitiesInAABBexcluding(owner, owner.getBoundingBox().grow(4), (e) -> e.isLiving() && e instanceof MonsterEntity);

            float modifier = Math.max(1, ( ( Math.max(0,found.size() - 3) + 1f ) / 2f ) + 0.1f ); //4 enemies = 1.1, 5 enemies = 1.6, 6 = 2.1
            float amount = event.getAmount() * modifier;

            event.setAmount(amount);
        }
    }
}
