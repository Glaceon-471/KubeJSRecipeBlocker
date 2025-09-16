package com.glaceon_471.kubejs_recipe_blocker.common.mixin.expatternprovider;

import appeng.api.networking.ticking.TickRateModulation;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.glodblock.github.extendedae.api.IRecipeMachine;
import com.glodblock.github.extendedae.common.tileentities.TileCircuitCutter;
import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.glodblock.github.extendedae.util.recipe.RecipeExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(value = TileCircuitCutter.class, remap = false)
public class TileCircuitCutterMixin {
    @Redirect(
        method = "tickingRequest",
        at = @At(
            value = "INVOKE",
            target = "Lcom/glodblock/github/extendedae/util/recipe/RecipeExecutor;execute(IZ)Lappeng/api/networking/ticking/TickRateModulation;"
        )
    )
    private TickRateModulation tickingRequest(RecipeExecutor<CircuitCutterRecipe> instance, int speed, boolean usePower) {
        IRecipeMachine<?, CircuitCutterRecipe> machine = ((RecipeExecutorAccessor<CircuitCutterRecipe>)instance).getMachine();
        CircuitCutterRecipe recipe = machine.getContext().currentRecipe;
        if (recipe != null) {
            TileCircuitCutter block = (TileCircuitCutter)(Object)this;
            Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(block);
            if (capability.isEmpty() || capability.get().getNonOwner() || BlockRecipeManager.isBlocked(
                capability.get().getOwner(), recipe, block.getLevel()
            )) {
                return TickRateModulation.URGENT;
            }
        }
        return instance.execute(speed, usePower);
    }
}
