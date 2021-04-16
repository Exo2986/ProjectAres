package sheepindev.projectares.perk;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sheepindev.projectares.enums.PerkCompatibilityEnum;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CancelDeathPerk extends Perk {
    protected String getNBTTagName() {
        return "";
    }

    protected int getCooldown() {
        return 0;
    }

    protected void doAbility(LivingEntity living, LivingDeathEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public void onOwnerDeath(ItemStack item, Entity owner, LivingDeathEvent event) {
        if (owner.world.isRemote()) return;
        CompoundNBT nbt = item.getOrCreateTag();

        long cooldown = nbt.getLong(getNBTTagName());

        if (owner.world.getGameTime() - cooldown >= getCooldown() && owner instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) owner;
            event.setCanceled(true);

            this.doAbility(living,event);

            nbt.putLong(getNBTTagName(), owner.world.getGameTime());
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
            float normalized = world.getGameTime() - item.getOrCreateTag().getLong(getNBTTagName());
            normalized = Math.min(getCooldown(), normalized);

            normalized /= getCooldown();
            return 1 - normalized;
        }
        return 1;
    }

    @Override
    public void populateCompatible() {
        super.populateCompatible();
        this.compatibilityTags.add(PerkCompatibilityEnum.UsesDurabilityBarIncompatible);
        super.doCompatabilityTagChecks();
    }
}
