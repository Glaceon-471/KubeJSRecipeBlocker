package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import appeng.crafting.pattern.AECraftingPattern;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AECraftingPattern.class, remap = false)
public interface AECraftingPatternAccessor {
    @Accessor
    CraftingRecipe getRecipe();
}
