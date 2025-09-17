package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import appeng.items.tools.powered.EntropyManipulatorItem;
import appeng.recipes.entropy.EntropyMode;
import appeng.recipes.entropy.EntropyRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntropyManipulatorItem.class, remap = false)
public abstract class EntropyManipulatorItemMixin {
    @Shadow
    @Nullable
    private static EntropyRecipe findRecipe(Level level, EntropyMode mode, BlockState block, FluidState fluid) {
        return null;
    }

    @Redirect(
        method = "tryApplyEffect",
        at = @At(
            value = "INVOKE",
            target = "Lappeng/items/tools/powered/EntropyManipulatorItem;findRecipe(Lnet/minecraft/world/level/Level;Lappeng/recipes/entropy/EntropyMode;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)Lappeng/recipes/entropy/EntropyRecipe;"
        )
    )
    private EntropyRecipe tryApplyEffect(Level level, EntropyMode mode, BlockState block, FluidState fluid, @Local(argsOnly = true) Player p) {
        EntropyRecipe recipe = findRecipe(level, mode, block, fluid);
        return BlockRecipeManager.isBlocked(p.getUUID(), level, recipe) ? null : recipe;
    }
}
