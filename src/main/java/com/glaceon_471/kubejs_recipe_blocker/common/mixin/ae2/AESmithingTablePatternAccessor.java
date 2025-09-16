package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import appeng.crafting.pattern.AESmithingTablePattern;
import net.minecraft.world.item.crafting.SmithingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AESmithingTablePattern.class, remap = false)
public interface AESmithingTablePatternAccessor {
    @Accessor
    SmithingRecipe getRecipe();
}
