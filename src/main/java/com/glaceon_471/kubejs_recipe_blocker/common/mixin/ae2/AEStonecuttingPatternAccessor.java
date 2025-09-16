package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import appeng.crafting.pattern.AEStonecuttingPattern;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AEStonecuttingPattern.class, remap = false)
public interface AEStonecuttingPatternAccessor {
    @Accessor
    StonecutterRecipe getRecipe();
}
