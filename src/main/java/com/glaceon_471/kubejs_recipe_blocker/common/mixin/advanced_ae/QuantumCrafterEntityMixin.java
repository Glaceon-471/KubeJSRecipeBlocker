package com.glaceon_471.kubejs_recipe_blocker.common.mixin.advanced_ae;

import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2.AECraftingPatternAccessor;
import net.pedroksl.advanced_ae.common.entities.QuantumCrafterEntity;
import net.pedroksl.advanced_ae.common.entities.QuantumCrafterEntity.CraftingJob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(value = QuantumCrafterEntity.class, remap = false)
public abstract class QuantumCrafterEntityMixin {
    @Shadow
    protected abstract void performCraft(CraftingJob job, int toCraft);

    @Redirect(
        method = "performCrafts",
        at = @At(
            value = "INVOKE",
            target = "Lnet/pedroksl/advanced_ae/common/entities/QuantumCrafterEntity;performCraft(Lnet/pedroksl/advanced_ae/common/entities/QuantumCrafterEntity$CraftingJob;I)V"
        )
    )
    private void makeCraftingRecipeList(QuantumCrafterEntity instance, CraftingJob job, int toCraft) {
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(instance);
        if (capability.isEmpty() || capability.get().getNonOwner() || BlockRecipeManager.isBlocked(
            capability.get().getOwner(), ((AECraftingPatternAccessor)job.pattern).getRecipe(), instance.getLevel()
        )) {
            return;
        }
        performCraft(job, toCraft);
    }
}
