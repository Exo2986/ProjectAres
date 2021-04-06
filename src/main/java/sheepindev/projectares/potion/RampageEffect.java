package sheepindev.projectares.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class RampageEffect extends Effect {
    protected final double bonusPerLevel;

    public RampageEffect(EffectType typeIn, int liquidColorIn, double bonusPerLevel) {
        super(typeIn, liquidColorIn);
        this.bonusPerLevel = bonusPerLevel;
    }

    public double getAttributeModifierAmount(int amplifier, @Nonnull AttributeModifier modifier) {
        return this.bonusPerLevel * (double)(amplifier + 1);
    }
}
