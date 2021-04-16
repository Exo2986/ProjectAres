package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sheepindev.projectares.enums.PerkCompatibilityEnum;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static sheepindev.projectares.util.ProjectAresConstants.NBT_TAG_NAME_SOUL_CAPACITOR_CHARGE;

public class SoulCapacitorPerk extends Perk {
    public static final float CHARGE_AMOUNT = 0.25f;

    @Override
    public void onKill(ItemStack item, LivingDeathEvent event) {
        if (event.getEntity().world.isRemote()) return;
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity owner = (PlayerEntity) event.getSource().getTrueSource();

            CompoundNBT nbt = item.getOrCreateTag();

            nbt.putFloat(NBT_TAG_NAME_SOUL_CAPACITOR_CHARGE, nbt.getFloat(NBT_TAG_NAME_SOUL_CAPACITOR_CHARGE) + CHARGE_AMOUNT);

            item.write(nbt);
        }
    }

    @Override
    public void onRightClick(ItemStack item, Entity entityIn) {
        if (!(entityIn instanceof PlayerEntity) ) return;
        PlayerEntity owner = (PlayerEntity) entityIn;

        CompoundNBT nbt = item.getOrCreateTag();

        if (nbt.getFloat(NBT_TAG_NAME_SOUL_CAPACITOR_CHARGE) >= 1f) {

            if (owner.world.isRemote()) {
                owner.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, owner.getPosX(), owner.getPosY(), owner.getPosZ(), 1.0D, 0.0D, 0.0D);
                return;
            }

            AxisAlignedBB aabb = owner.getBoundingBox().grow(3);

            List<Entity> entities = owner.world.getEntitiesInAABBexcluding(owner, aabb,
                    (e) -> e instanceof LivingEntity);

            owner.world.playSound(null, owner.getPosX(), owner.getPosY(), owner.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f, 0.8f);

            for (Entity entity : entities) {
                DamageSource source = DamageSource.causeExplosionDamage((LivingEntity) entity);
                entity.attackEntityFrom(source, 25f);

                entity.setMotion(entity.getMotion().mul(3, 3, 3));
            }

            owner.addVelocity(0, 1.1f, 0);
            owner.velocityChanged = true;

            nbt.putFloat(NBT_TAG_NAME_SOUL_CAPACITOR_CHARGE, nbt.getFloat(NBT_TAG_NAME_SOUL_CAPACITOR_CHARGE)-1f);

            item.write(nbt);
        }
    }

    @Override
    public boolean shouldShowDurabilityBar(ItemStack item) {
        return true;
    }

    @Override
    public double getDurability(ItemStack item) {
        return Math.max(1 - item.getOrCreateTag().getFloat(NBT_TAG_NAME_SOUL_CAPACITOR_CHARGE), 0);
    }

    @Override
    public void populateCompatible() {
        super.populateCompatible();
        this.compatibilityTags.add(PerkCompatibilityEnum.UsesDurabilityBarIncompatible);
        this.compatibilityTags.add(PerkCompatibilityEnum.OnRightClickIncompatible);
        super.doCompatabilityTagChecks();
    }
}
