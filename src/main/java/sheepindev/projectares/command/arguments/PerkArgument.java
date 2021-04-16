package sheepindev.projectares.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import sheepindev.projectares.ProjectAres;
import sheepindev.projectares.perk.Perk;
import sheepindev.projectares.registry.RegisterPerks;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static sheepindev.projectares.util.ProjectAresConstants.MOD_ID;

public class PerkArgument implements ArgumentType<Perk> {
    private static final Collection<String> EXAMPLES = Arrays.asList("rampage", "top_heavy");
    public static final DynamicCommandExceptionType PERK_UNKNOWN = new DynamicCommandExceptionType((perk) ->
            new TranslationTextComponent(MOD_ID + ".perk.unknown", perk));

    public static PerkArgument perk () {
        return new PerkArgument();
    }

    public static Perk getPerk(CommandContext<CommandSource> context, String name) {
        return context.getArgument(name, Perk.class);
    }

    @Override
    public Perk parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(reader);

        System.out.println(resourceLocation.toString());

        return RegisterPerks.PERK_REGISTRY.getValues()
                .stream()
                .filter((a) -> a.getRegistryName().equals(resourceLocation))
                .findFirst()
                .orElseThrow(() -> PERK_UNKNOWN.create(resourceLocation));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggestIterable(RegisterPerks.PERK_REGISTRY.getKeys(), builder);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
