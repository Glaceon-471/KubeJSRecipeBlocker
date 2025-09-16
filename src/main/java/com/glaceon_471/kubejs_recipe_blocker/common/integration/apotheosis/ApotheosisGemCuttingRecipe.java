package com.glaceon_471.kubejs_recipe_blocker.common.integration.apotheosis;

import com.glaceon_471.kubejs_recipe_blocker.common.integration.EmptySerializer;
import com.glaceon_471.kubejs_recipe_blocker.common.registries.ModRecipes;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.cutting.GemCuttingMenu;
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
public class ApotheosisGemCuttingRecipe implements Recipe<Container> {
    private final GemCuttingMenu.GemCuttingRecipe recipe;
    private final ItemStack gem;
    private final ItemStack left;
    private final ItemStack bottom;
    private final ItemStack right;
    private final ItemStack result;

    public ApotheosisGemCuttingRecipe(GemCuttingMenu.GemCuttingRecipe recipe, ItemStack gem, ItemStack left, ItemStack bottom, ItemStack right) {
        this.recipe = recipe;
        this.gem = gem;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.result = recipe.getResult(gem, left, bottom, right);
    }

    public GemCuttingMenu.GemCuttingRecipe getRecipe() {
        return recipe;
    }

    @Override
    public boolean matches(Container arg, Level arg2) {
        return recipe.matches(gem, left, bottom, right);
    }

    @Override
    public ItemStack assemble(Container arg, RegistryAccess arg2) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    public ItemStack getGem() {
        return gem.copy();
    }

    public ItemStack getLeft() {
        return left.copy();
    }

    public ItemStack getBottom() {
        return bottom.copy();
    }

    public ItemStack getRight() {
        return right.copy();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess arg) {
        return this.result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return ModRecipes.APOTHEOSIS_GEM_CUTTING_TYPE.getId();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.APOTHEOSIS_GEM_CUTTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.APOTHEOSIS_GEM_CUTTING_TYPE.get();
    }

    public static class Serializer extends EmptySerializer<ApotheosisGemCuttingRecipe> {
        @Override
        protected ApotheosisGemCuttingRecipe empty() {
            return new ApotheosisGemCuttingRecipe(
                new EmptyRecipe(), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY
            );
        }

        public static class EmptyRecipe implements GemCuttingMenu.GemCuttingRecipe {
            @Override
            public boolean matches(ItemStack itemStack, ItemStack itemStack1, ItemStack itemStack2, ItemStack itemStack3) {
                return false;
            }

            @Override
            public ItemStack getResult(ItemStack itemStack, ItemStack itemStack1, ItemStack itemStack2, ItemStack itemStack3) {
                return ItemStack.EMPTY;
            }

            @Override
            public void decrementInputs(ItemStack itemStack, ItemStack itemStack1, ItemStack itemStack2, ItemStack itemStack3) { }
        }
    }
}
