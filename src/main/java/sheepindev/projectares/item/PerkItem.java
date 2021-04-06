package sheepindev.projectares.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import sheepindev.projectares.ProjectAres;
import sheepindev.projectares.perk.Perk;
import sheepindev.projectares.perk.PerkRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PerkItem extends Item {
    public static final String NBT_TAG_NAME_PERK_LIST = "perks_list";
    public static final String NBT_TAG_NAME_PERK_COUNT = "num_perks";
    public static final String NBT_TAG_NAME_PERK_KEY = "perk_id";

    protected int GetMaxPerks() { return 2; }

    public PerkItem(Properties properties) {
        super(properties);
    }

    public void AddPerk(ItemStack stack, Perk perk) {
        System.out.println("adding " + perk.GetID());
        CompoundNBT nbt = stack.getOrCreateTag();

        ListNBT perks = nbt.getList(NBT_TAG_NAME_PERK_LIST, Constants.NBT.TAG_COMPOUND);

        int currentPerkCount = nbt.getInt(NBT_TAG_NAME_PERK_COUNT);

        if (currentPerkCount < GetMaxPerks()) {

            CompoundNBT write = new CompoundNBT();
            write.putString(NBT_TAG_NAME_PERK_KEY, perk.GetID());

            perks.add(write); //Add perk to list
            nbt.put(NBT_TAG_NAME_PERK_LIST, perks); //Add list to NBT

            nbt.putInt(NBT_TAG_NAME_PERK_COUNT, currentPerkCount + 1);

            stack.write(nbt); //Update Item NBT with new info

        }
    }

    public Perk[] GetPerks(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        ListNBT perks = nbt.getList(NBT_TAG_NAME_PERK_LIST, Constants.NBT.TAG_COMPOUND);

        Perk[] perkArray = new Perk[GetMaxPerks()];

        for (int i = 0; i < GetMaxPerks(); i++) {
            perkArray[i] = PerkRegistry.GetPerk(perks.getCompound(i).getString(NBT_TAG_NAME_PERK_KEY));
            if (perkArray[i] == null)
                perkArray[i] = new Perk(); //We don't want nullables
        }

        return perkArray;
    }

    public boolean HasPerk(ItemStack stack, String perkID) {
        Perk[] perks = GetPerks(stack);
        boolean found = false;

        for (Perk perk : perks) {
            if (perk.GetID().equals(perkID)) {
                found = true;
            }
        }

        return found;
    }

    public void FirePerkEvent(ItemStack stack, Consumer<Perk> action) {
        Arrays.stream(GetPerks(stack)).forEach(action);
    }

    public void ShufflePerks(ItemStack stack) {
        Perk[] perks = GetPerks(stack);

        for (Perk perk : perks) {
            if (perk == null || perk.getClass().equals(Perk.class)) {
                AddPerk(stack,PerkRegistry.GetPerk("extended_blade"));
                AddPerk(stack,PerkRegistry.GetPerk("top_heavy"));
                System.out.println("perks added");
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {

            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 1, AttributeModifier.Operation.ADDITION));

            Arrays.stream(GetPerks(stack)).forEach((perk -> {
                AbstractMap.SimpleEntry<Attribute, AttributeModifier> modifier = perk.GetAttributeModifiers(stack);
                if (modifier != null) {
                    builder.put(modifier.getKey(), modifier.getValue());
                }
            }));

            return builder.build();

        }
        return super.getAttributeModifiers(equipmentSlot,stack);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity)
    {
        FirePerkEvent(stack, (a) -> a.OnSwing(stack, entity));
        return false;
    }

    @Override
    public @Nonnull ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItemMainhand();

        if (world.isRemote()) return ActionResult.resultPass(stack);

        FirePerkEvent(stack, (a) -> a.OnRightClick(stack, player));

        ShufflePerks(stack);

        return ActionResult.resultPass(stack);
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull Entity entityIn, int itemSlot, boolean isSelected) {
        if (isSelected) {
            FirePerkEvent(stack, (a) -> a.OnTick(stack, entityIn));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, @Nonnull ITooltipFlag advanced) {
        tooltip.add(new TranslationTextComponent("projectares.tooltip.perks").mergeStyle(TextFormatting.GRAY));

        Arrays.stream(GetPerks(itemStack)).forEach((a) ->
                tooltip.add(new TranslationTextComponent(ProjectAres.MOD_ID + ".perk." + a.GetID()).mergeStyle(TextFormatting.GOLD)));
    }
}
