package com.glaceon_471.kubejs_recipe_blocker.common.kubejs.server;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public class BlockRecipesEventJS extends EventJS {

    public BlockRecipesEventJS() { }

    public void filter(Predicate<BlockRecipeEventJS> filter) {
        BlockRecipeManager.addFilter(filter);
    }

    public void filterRecipe(ResourceLocation type, Predicate<BlockRecipeEventJS> filter) {
        BlockRecipeManager.addFilter(ForgeRegistries.RECIPE_TYPES.getValue(type), filter);
    }

    public void filterBlock(ResourceLocation type, Predicate<BlockRecipeEventJS> filter) {
        BlockRecipeManager.addFilter(ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(type), filter);
    }

    public void filterMenu(ResourceLocation type, Predicate<BlockRecipeEventJS> filter) {
        BlockRecipeManager.addFilter(ForgeRegistries.MENU_TYPES.getValue(type), filter);
    }
}
