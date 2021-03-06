package sheepindev.projectares.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.AtomicDouble;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;
import sheepindev.projectares.perk.Perk;
import sheepindev.projectares.registry.RegisterPerks;
import sheepindev.projectares.util.SwordRollHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static sheepindev.projectares.registry.RegisterPerks.getRegisteredPerk;
import static sheepindev.projectares.util.ProjectAresConstants.*;
import static sheepindev.projectares.util.ProjectAresConstants.MOD_ID;
import static sheepindev.projectares.util.RegistryHelper.prefix;

public class PerkItem extends Item {

    protected int getMaxPerks() { return 2; }

    protected int getMaxInstrinsics() { return 0; }

    public PerkItem(Properties properties) {
        super(properties);
    }

    public void addPerk(ItemStack stack, Perk perk) {
        System.out.println("adding " + perk.getRegistryName());
        CompoundNBT nbt = stack.getOrCreateTag();

        ListNBT perks = nbt.getList(NBT_TAG_NAME_PERK_LIST, Constants.NBT.TAG_COMPOUND);

        int currentPerkCount = nbt.getInt(NBT_TAG_NAME_PERK_COUNT);

        if (currentPerkCount < getMaxPerks()) {

            CompoundNBT write = new CompoundNBT();
            write.putString(NBT_TAG_NAME_PERK_KEY, perk.getRegistryName().toString());

            perks.add(write); //Add perk to list
            nbt.put(NBT_TAG_NAME_PERK_LIST, perks); //Add list to NBT

            nbt.putInt(NBT_TAG_NAME_PERK_COUNT, currentPerkCount + 1);

            stack.write(nbt); //Update Item NBT with new info

        }
    }

    public Perk getIntrinsic() {
        return getRegisteredPerk(prefix("evasion"));
    }

    public void addIntrinsic(ItemStack stack) {

        System.out.println("intrinsic");
        if (getMaxInstrinsics() == 0) return;

        Perk perk = getIntrinsic();

        System.out.println("intrinsic is " + perk.getRegistryName());
        CompoundNBT nbt = stack.getOrCreateTag();

        ListNBT perks = nbt.getList(NBT_TAG_NAME_PERK_LIST, Constants.NBT.TAG_COMPOUND);

        CompoundNBT write = new CompoundNBT();
        write.putString(NBT_TAG_NAME_PERK_KEY, perk.getRegistryName().toString());

        perks.addNBTByIndex(getMaxPerks(), write);

        nbt.put(NBT_TAG_NAME_PERK_LIST, perks);

        stack.write(nbt);
    }

    public Perk[] getPerks(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        ListNBT perks = nbt.getList(NBT_TAG_NAME_PERK_LIST, Constants.NBT.TAG_COMPOUND);

        Perk[] perkArray = new Perk[getMaxPerks() + getMaxInstrinsics()];

        for (int i = 0; i < perkArray.length; i++) {
            perkArray[i] = getRegisteredPerk(perks.getCompound(i).getString(NBT_TAG_NAME_PERK_KEY));
            if (perkArray[i] == null)
                perkArray[i] = new Perk(); //We don't want nullables
        }

        return perkArray;
    }

    public boolean hasPerk(ItemStack stack, String perkID) {
        Perk[] perks = getPerks(stack);
        boolean found = false;

        for (Perk perk : perks) {
            if (perk.getRegistryName().toString().equals(perkID)) {
                found = true;
            }
        }

        return found;
    }

    public void firePerkEvent(ItemStack stack, Consumer<Perk> action) {
        Arrays.stream(getPerks(stack)).forEach(action);
    }

//    public void shufflePerks(ItemStack stack) {
//        Perk[] perks = getPerks(stack);
//
//        for (Perk perk : perks) {
//            if (perk == null || perk.getClass().equals(Perk.class)) {
//                addPerk(stack,getRegisteredPerk(prefix("extended_blade")));
//                addPerk(stack,getRegisteredPerk(prefix("top_heavy")));
//            }
//        }
//    }

    public Perk randomExcluding(ArrayList<Perk> perks, ArrayList<Perk> excluding) {
        perks.removeAll(excluding);
        int random = new Random().nextInt(perks.size());
        return perks.get(random);
    }

    public void randomizePerk(ItemStack stack) {
        Perk[] perks = getPerks(stack);

        ArrayList<Perk> perkList = new ArrayList<Perk>(Arrays.asList(RegisterPerks.getRegisteredPerks()));
        ArrayList<Perk> excluding = new ArrayList<Perk>();

        if (perks[0] == null || perks[0].getClass().equals(Perk.class)) {

            addPerk(stack,randomExcluding(perkList,excluding));

        } else if (perks[1].getClass().equals(Perk.class)) {

            excluding.add(perks[0]);
            addPerk(stack,randomExcluding(perkList,excluding));

        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {

            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 7, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 0.1, AttributeModifier.Operation.ADDITION));

            Arrays.stream(getPerks(stack)).forEach((perk -> {
                AbstractMap.SimpleEntry<Attribute, AttributeModifier> modifier = perk.getAttributeModifiers(stack);
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
        firePerkEvent(stack, (a) -> a.onSwing(stack, entity));
        return false;
    }

    @Override
    public @Nonnull ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItemMainhand();

        firePerkEvent(stack, (a) -> a.onRightClick(stack, player));


        DungeonSwordPerkItem item = (DungeonSwordPerkItem) ForgeRegistries.ITEMS.getValue(prefix("dungeon_sword"));

        ItemStack itemStack = SwordRollHelper.getRolledItem(item);
        player.addItemStackToInventory(itemStack);

        return ActionResult.resultPass(stack);
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull Entity entityIn, int itemSlot, boolean isSelected) {
        if (isSelected) {
            firePerkEvent(stack, (a) -> a.onTick(stack, entityIn));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, @Nonnull ITooltipFlag advanced) {
        Perk[] perks = getPerks(itemStack);

        if (perks.length-1 == getMaxPerks()) { //check if it has more than the technical max
            tooltip.add(new TranslationTextComponent(MOD_ID + ".tooltip.intrinsic").mergeStyle(TextFormatting.GRAY));

            ResourceLocation intrinsic = perks[getMaxPerks()].getRegistryName();
            if (intrinsic != null)
                tooltip.add(new TranslationTextComponent(MOD_ID + ".perk." + intrinsic.getPath()).mergeStyle(TextFormatting.GOLD));

            tooltip.add(new StringTextComponent(""));
        }

        tooltip.add(new TranslationTextComponent(MOD_ID + ".tooltip.perks").mergeStyle(TextFormatting.GRAY));

        Arrays.stream(perks).forEach((a) -> {
            ResourceLocation name = a.getRegistryName();
            if (name == null || a.isIntrinsic()) return;
            tooltip.add(new TranslationTextComponent(MOD_ID + ".perk." + name.getPath()).mergeStyle(TextFormatting.GOLD));
        });
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        AtomicBoolean value = new AtomicBoolean();
        value.set(false);
        firePerkEvent(stack, (a) -> {
            value.set(a.shouldShowDurabilityBar(stack) || value.get());
        });
        return value.get();
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        AtomicDouble value = new AtomicDouble();
        firePerkEvent(stack, (a) -> {
            value.addAndGet(a.getDurability(stack));
        });
        return value.get();
    }
}
