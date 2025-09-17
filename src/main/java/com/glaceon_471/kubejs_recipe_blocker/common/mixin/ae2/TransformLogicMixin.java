package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import appeng.recipes.transform.TransformLogic;
import appeng.recipes.transform.TransformRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(value = TransformLogic.class, remap = false)
public class TransformLogicMixin {
    @Redirect(
        method = "tryTransform",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;isEmpty()Z"
        )
    )
    private static boolean tryTransform(List<Ingredient> instance, @Local(argsOnly = true) ItemEntity entity, @Local Level level, @Local TransformRecipe recipe) {
        Entity player = entity.getOwner();
        if (!(player instanceof Player)) {
            return false;
        }
        return instance.isEmpty() && !BlockRecipeManager.isBlocked(
            entity.getOwner().getUUID(), level, recipe
        );
    }
}
