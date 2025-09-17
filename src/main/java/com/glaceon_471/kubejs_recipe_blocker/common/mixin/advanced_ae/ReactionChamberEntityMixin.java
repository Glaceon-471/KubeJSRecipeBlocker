package com.glaceon_471.kubejs_recipe_blocker.common.mixin.advanced_ae;

import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.pedroksl.advanced_ae.common.entities.ReactionChamberEntity;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(value = ReactionChamberEntity.class, remap = false)
public abstract class ReactionChamberEntityMixin {
    @Shadow
    private ReactionChamberRecipe cachedTask;

    @Shadow
    protected abstract boolean hasCraftWork();

    @Redirect(
        method = "tickingRequest",
        at = @At(
            value = "INVOKE",
            target = "Lnet/pedroksl/advanced_ae/common/entities/ReactionChamberEntity;hasCraftWork()Z"
        )
    )
    private boolean tickingRequest(ReactionChamberEntity instance) {
        ReactionChamberEntity self = (ReactionChamberEntity)(Object)this;
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(self);
        return hasCraftWork() && capability.isPresent() && !capability.get().getNonOwner() && !BlockRecipeManager.isBlocked(
            capability.get().getOwner(), self.getLevel(), cachedTask, instance
        );
    }
}
