package com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft;

import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @Redirect(
        method = "serverTick",
        at = @At(value = "INVOKE", target = "Ljava/util/Optional;orElse(Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private static Object serverTick(
        Optional<AbstractCookingRecipe> instance, Object other,
        @Local(argsOnly = true) Level arg, @Local(argsOnly = true) AbstractFurnaceBlockEntity arg4
    ) {
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(arg4);
        return instance.isEmpty() || capability.isEmpty() || capability.get().getNonOwner() || BlockRecipeManager.isBlocked(
            capability.get().getOwner(), arg, instance.get(), arg4
        ) ? other : instance.get();
    }
}
