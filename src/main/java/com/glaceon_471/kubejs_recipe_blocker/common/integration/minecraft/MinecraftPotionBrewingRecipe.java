package com.glaceon_471.kubejs_recipe_blocker.common.integration.minecraft;

import com.glaceon_471.kubejs_recipe_blocker.common.integration.EmptySerializer;
import com.glaceon_471.kubejs_recipe_blocker.common.registries.ModRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MinecraftPotionBrewingRecipe implements Recipe<Container> {
    private final PotionBrewing.Mix<Potion> mix;
    private final ItemStack item;

    public MinecraftPotionBrewingRecipe(PotionBrewing.Mix<Potion> mix, ItemStack item) {
        this.mix = mix;
        this.item = item;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access) {
        return getResultItem(access);
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return PotionUtils.setPotion(item, getResultPotion(access));
    }

    public Potion getResultPotion(RegistryAccess access) {
        return mix.to.get();
    }

    @Override
    public ResourceLocation getId() {
        return ModRecipes.MINECRAFT_POTION_BREWING_TYPE.getId();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MINECRAFT_POTION_BREWING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MINECRAFT_POTION_BREWING_TYPE.get();
    }

    public static class Serializer extends EmptySerializer<MinecraftPotionBrewingRecipe> {
        @Override
        protected MinecraftPotionBrewingRecipe empty() {
            return new MinecraftPotionBrewingRecipe(
                new PotionBrewing.Mix<>(ForgeRegistries.POTIONS, Potions.EMPTY, Ingredient.of(Items.AIR), Potions.EMPTY),
                new ItemStack(Items.AIR)
            );
        }
    }
}
