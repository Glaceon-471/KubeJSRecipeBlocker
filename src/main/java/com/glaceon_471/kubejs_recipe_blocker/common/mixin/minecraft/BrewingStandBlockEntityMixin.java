package com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft;

import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.integration.minecraft.MinecraftBrewingRecipeHelper;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.UUID;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin {
    @Shadow
    @Final
    private static int[] SLOTS_FOR_SIDES;

    @Shadow
    private static boolean isBrewable(NonNullList<ItemStack> arg){
        return false;
    }

    @Redirect(
        method = "serverTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/entity/BrewingStandBlockEntity;isBrewable(Lnet/minecraft/core/NonNullList;)Z"
        )
    )
    private static boolean serverTick(NonNullList<ItemStack> container, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BrewingStandBlockEntity arg4) {
        if (!isBrewable(container)) return false;
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(arg4);
        if (capability.isEmpty() || capability.get().getNonOwner()) return false;
        ItemStack input = arg4.getItem(3);
        UUID uuid = capability.get().getOwner();
        for (int index : SLOTS_FOR_SIDES) {
            ItemStack ingredient = arg4.getItem(index);
            Optional<Recipe<Container>> recipe = MinecraftBrewingRecipeHelper.getRecipe(input, ingredient);
            if (recipe.isEmpty()) continue;
            if (BlockRecipeManager.isBlocked(uuid, level, recipe.get(), arg4)) {
                return false;
            }
        }
        return true;
    }
}
