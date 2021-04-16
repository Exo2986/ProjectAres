package sheepindev.projectares.perk;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import static sheepindev.projectares.util.ProjectAresConstants.*;

public class FinalStandPerk extends Perk {

    @Override
    public void onOwnerDeath(ItemStack item, Entity owner, LivingDeathEvent event) {
        if (owner.world.isRemote()) return;
        CompoundNBT nbt = item.getOrCreateTag();

        long cooldown = nbt.getLong(NBT_TAG_NAME_FINAL_STAND_COOLDOWN);

        if (owner.world.getGameTime() - cooldown >= COOLDOWN_FINAL_STAND && owner instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) owner;

            event.setCanceled(true);
            living.setHealth(1);
            living.addPotionEffect(new EffectInstance(Effects.REGENERATION, 20*3, 3));

            living.world.playSound(null, living.getPosX(), living.getPosY(), living.getPosZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 1f, 1.3F);

            nbt.putLong(NBT_TAG_NAME_FINAL_STAND_COOLDOWN, owner.world.getGameTime());
            item.write(nbt);
        }
    }

    @Override
    public boolean shouldShowDurabilityBar(ItemStack item) {
        return true;
    }

    @Override
    public double getDurability(ItemStack item) {
        World world = Minecraft.getInstance().world;
        if (world != null) {
            float normalized = world.getGameTime() - item.getOrCreateTag().getLong(NBT_TAG_NAME_FINAL_STAND_COOLDOWN);
            normalized = Math.min(COOLDOWN_FINAL_STAND, normalized);

            normalized /= COOLDOWN_FINAL_STAND;
            return 1 - normalized;
        }
        return 1;
    }
}
