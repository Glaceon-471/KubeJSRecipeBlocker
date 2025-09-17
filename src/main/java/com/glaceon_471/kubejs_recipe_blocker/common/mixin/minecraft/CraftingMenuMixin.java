package com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {
    @Redirect(
        method = "slotChangedCraftingGrid",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/Optional;isPresent()Z"
        )
    )
    private static boolean slotChangedCraftingGrid(
        Optional<CraftingRecipe> instance, @Local(argsOnly = true) AbstractContainerMenu arg,
        @Local(argsOnly = true) Level arg2, @Local(argsOnly = true) Player arg3
    ) {
        return instance.isPresent() && !BlockRecipeManager.isBlocked(arg3.getUUID(), arg2, instance.get(), arg);
    }
}
