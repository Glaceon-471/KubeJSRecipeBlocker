package com.glaceon_471.kubejs_recipe_blocker.common.mixin.expatternprovider;

import appeng.recipes.handlers.InscriberRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.glodblock.github.extendedae.common.me.InscriberThread;
import com.glodblock.github.extendedae.common.tileentities.TileExInscriber;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(value = InscriberThread.class, remap = false)
public abstract class InscriberThreadMixin {
    @Shadow
    @Final
    @NotNull
    private TileExInscriber host;

    @Shadow
    private InscriberRecipe cachedTask;

    @Shadow
    protected abstract boolean hasCraftWork();

    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lcom/glodblock/github/extendedae/common/me/InscriberThread;hasCraftWork()Z",
            ordinal = 0
        )
    )
    private boolean tickingRequestHasCraftWork(InscriberThread instance) {
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(host);
        return hasCraftWork() && capability.isPresent() && !capability.get().getNonOwner() && !BlockRecipeManager.isBlocked(
            capability.get().getOwner(), cachedTask, host.getLevel()
        );
    }
}
