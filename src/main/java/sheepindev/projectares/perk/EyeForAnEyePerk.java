package sheepindev.projectares.perk;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import static sheepindev.projectares.util.ProjectAresConstants.NBT_TAG_NAME_EYE_PERK;

public class EyeForAnEyePerk extends Perk {
    public static final float CHARGE_PERCENT = 0.5f;

    protected void dischargePower(ItemStack item) {
        CompoundNBT nbt = item.getOrCreateTag();

        nbt.putFloat(NBT_TAG_NAME_EYE_PERK, 0);

        item.write(nbt);
    }

    protected void absorbDamage(ItemStack item, float damage) {
        damage *= CHARGE_PERCENT;

        CompoundNBT nbt = item.getOrCreateTag();

        nbt.putFloat(NBT_TAG_NAME_EYE_PERK, nbt.getFloat(NBT_TAG_NAME_EYE_PERK) + damage);

        item.write(nbt);
    }

    protected float getChargedDamage(ItemStack item, float damage) {
        CompoundNBT nbt = item.getOrCreateTag();
        return damage + nbt.getFloat(NBT_TAG_NAME_EYE_PERK);
    }

    @Override
    public void onOwnerDamage(ItemStack item, LivingDamageEvent event) {
        this.absorbDamage(item, event.getAmount());
    }

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) {
        event.setAmount(this.getChargedDamage(item, event.getAmount()));
        this.dischargePower(item);
    }

    @Override
    public void populateCompatible() {
        super.populateCompatible();
        super.doCompatabilityTagChecks();
    }
}
