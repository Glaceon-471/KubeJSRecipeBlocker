package com.glaceon_471.kubejs_recipe_blocker.common.mixin.expatternprovider;

import com.glodblock.github.extendedae.api.IRecipeMachine;
import com.glodblock.github.extendedae.util.recipe.RecipeExecutor;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = RecipeExecutor.class, remap = false)
public interface RecipeExecutorAccessor<T extends Recipe<?>> {
    @Accessor
    IRecipeMachine<?, T> getMachine();
}
