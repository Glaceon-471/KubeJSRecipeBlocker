package com.glaceon_471.kubejs_recipe_blocker.common.integration.ae2;

import com.glaceon_471.kubejs_recipe_blocker.common.integration.EmptySerializer;
import com.glaceon_471.kubejs_recipe_blocker.common.registries.ModRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AE2CondenserRecipe implements Recipe<Container> {
    private final long input;
    private final ItemStack result;

    public AE2CondenserRecipe(long input, ItemStack result) {
        this.input = input;
        this.result = result;
    }

    @Override
    public boolean matches(Container arg, Level arg2) {
        return true;
    }

    @Override
    public ItemStack assemble(Container arg, RegistryAccess arg2) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    public long getInputValue(RegistryAccess arg) {
        return input;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess arg) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return ModRecipes.AE2_CONDENSER_TYPE.getId();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.AE2_CONDENSER_TYPE.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.AE2_CONDENSER_SERIALIZER.get();
    }

    public static class Serializer extends EmptySerializer<AE2CondenserRecipe> {
        @Override
        protected AE2CondenserRecipe empty() {
            return new AE2CondenserRecipe(0, ItemStack.EMPTY);
        }
    }
}
