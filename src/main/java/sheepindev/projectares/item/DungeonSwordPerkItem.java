package sheepindev.projectares.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.AbstractMap;
import java.util.Arrays;

public class DungeonSwordPerkItem extends PerkItem {
    public DungeonSwordPerkItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {

            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 7, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 0.9, AttributeModifier.Operation.MULTIPLY_TOTAL));

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
}
