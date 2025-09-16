package com.glaceon_471.kubejs_recipe_blocker.common.kubejs.server;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public class BlockRecipesEventJS extends EventJS {

    public BlockRecipesEventJS() { }

    public void block(Predicate<BlockRecipeEventJS<Recipe<?>>> predicate) {
        BlockRecipeManager.addPredicate(predicate);
    }

    public void block(ResourceLocation type, Predicate<BlockRecipeEventJS<Recipe<?>>> predicate) {
        BlockRecipeManager.addPredicate(ForgeRegistries.RECIPE_TYPES.getValue(type), predicate);
    }
}
