package com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft;

import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.function.Function;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin {
    @Redirect(
        method = "cookTick",
        at = @At(value = "INVOKE", target = "Ljava/util/Optional;map(Ljava/util/function/Function;)Ljava/util/Optional;")
    )
    private static Optional<ItemStack> cookTick(
        Optional<CampfireCookingRecipe> instance,
        Function<? super CampfireCookingRecipe, ? extends ItemStack> mapper,
        @Local(argsOnly = true) Level arg, @Local(argsOnly = true) CampfireBlockEntity arg4,
        @Local Container container
    ) {
        if (instance.isEmpty()) return instance.map(mapper);
        CampfireCookingRecipe recipe = instance.get();
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(arg4);
        if (capability.isEmpty() || capability.get().getNonOwner() || BlockRecipeManager.isBlocked(capability.get().getOwner(), arg, recipe, arg4)) {
            return Optional.empty();
        }
        return instance.map(mapper);
    }
}
