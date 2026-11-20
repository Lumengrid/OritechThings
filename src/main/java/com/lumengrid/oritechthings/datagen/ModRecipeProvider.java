package com.lumengrid.oritechthings.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @SuppressWarnings("null")
    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput);
    }
}
