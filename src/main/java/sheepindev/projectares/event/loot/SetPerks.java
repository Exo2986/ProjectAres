package sheepindev.projectares.event.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import sheepindev.projectares.item.PerkItem;

import javax.annotation.Nonnull;

public class SetPerks extends LootFunction {
    public SetPerks(ILootCondition[] conditions) {
        super(conditions);
    }

    @Override
    @Nonnull
    protected ItemStack doApply(ItemStack stack, @Nonnull LootContext context) {
        if (stack.getItem() instanceof PerkItem) {
            PerkItem item = (PerkItem) stack.getItem();
            item.randomizePerk(stack);
            item.randomizePerk(stack);

            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public LootFunctionType getFunctionType() {
        return LootTableEventHandler.SET_PERKS;
    }

    public static class Serializer extends LootFunction.Serializer<SetPerks> {
        @Override
        @Nonnull
        public SetPerks deserialize(@Nonnull JsonObject object, @Nonnull JsonDeserializationContext deserializationContext, @Nonnull ILootCondition[] conditionsIn) {
            return new SetPerks(conditionsIn);
        }
    }
}
