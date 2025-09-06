package mc.david123.datagen;

import mc.david123.blocks.ModBlocks;
import mc.david123.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CUSTOM_DATA_STRIP_MACHINE.get())
                .pattern("XXX")
                .pattern("SBS")
                .pattern("XXX")
                .define('X', Items.BRUSH)
                .define('B', Items.STONE)
                .define('S', Items.SHEARS)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput);
    }
}
