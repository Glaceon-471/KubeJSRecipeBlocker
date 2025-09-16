package com.glaceon_471.kubejs_recipe_blocker.common.integration.minecraft;

import com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft.PotionBrewingAccessor;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

public class MinecraftBrewingRecipeHelper {
    public static Optional<Recipe<Container>> getRecipe(ItemStack input, ItemStack ingredient) {
        if (!ingredient.isEmpty()) {
            Item item = ingredient.getItem();
            Potion potion = PotionUtils.getPotion(ingredient);

            Optional<PotionBrewing.Mix<Item>> container_mix = PotionBrewingAccessor.getContainerMixes().stream().filter(
                m -> m.from.get() == item && m.ingredient.test(input)
            ).findFirst();
            if (container_mix.isPresent()) return container_mix.map((mix) -> new MinecraftContainerBrewingRecipe(mix, potion));

            Optional<PotionBrewing.Mix<Potion>> potion_mix = PotionBrewingAccessor.getPotionMixes().stream().filter(
                m -> m.from.get() == potion && m.ingredient.test(input)
            ).findFirst();
            if (potion_mix.isPresent()) return potion_mix.map((mix) -> new MinecraftPotionBrewingRecipe(mix, input));
        }
        return Optional.empty();
    }
}
