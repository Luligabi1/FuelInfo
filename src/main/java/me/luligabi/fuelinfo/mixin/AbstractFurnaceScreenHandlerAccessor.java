package me.luligabi.fuelinfo.mixin;

import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractFurnaceScreenHandler.class)
public interface AbstractFurnaceScreenHandlerAccessor {

    @Accessor
    PropertyDelegate getPropertyDelegate();

    @Accessor
    RecipeType<? extends AbstractCookingRecipe> getRecipeType();
}