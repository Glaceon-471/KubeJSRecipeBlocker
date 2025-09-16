package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import appeng.api.networking.IGridNode;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.blockentity.crafting.IMolecularAssemblerSupportedPattern;
import appeng.blockentity.crafting.MolecularAssemblerBlockEntity;
import appeng.crafting.pattern.AECraftingPattern;
import appeng.crafting.pattern.AESmithingTablePattern;
import appeng.crafting.pattern.AEStonecuttingPattern;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.world.item.crafting.Recipe;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = MolecularAssemblerBlockEntity.class, remap = false)
public class MolecularAssemblerBlockEntityMixin {
    @Shadow
    private IMolecularAssemblerSupportedPattern myPlan;

    @Shadow
    private boolean isAwake;

    @Inject(
        method = "tickingRequest",
        at = @At(
            value = "FIELD",
            target = "Lappeng/blockentity/crafting/MolecularAssemblerBlockEntity;reboot:Z",
            opcode = Opcodes.GETFIELD
        ),
        cancellable = true
    )
    private void tickingRequest(IGridNode node, int ticksSinceLastCall, CallbackInfoReturnable<TickRateModulation> callback) {
        MolecularAssemblerBlockEntity block = (MolecularAssemblerBlockEntity)(Object)this;
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(block);
        if (capability.isEmpty() || capability.get().getNonOwner()) {
            callback.setReturnValue(isAwake ? TickRateModulation.IDLE : TickRateModulation.SLEEP);
            return;
        }

        Recipe<?> recipe = null;
        if (myPlan instanceof AECraftingPattern crafting) {
            recipe = ((AECraftingPatternAccessor)crafting).getRecipe();
        }
        else if (myPlan instanceof AESmithingTablePattern smithing) {
            recipe = ((AESmithingTablePatternAccessor)smithing).getRecipe();
        }
        else if (myPlan instanceof AEStonecuttingPattern stoncutting) {
            recipe = ((AEStonecuttingPatternAccessor)stoncutting).getRecipe();
        }

        if (recipe == null) {
            callback.setReturnValue(isAwake ? TickRateModulation.IDLE : TickRateModulation.SLEEP);
            return;
        }

        if (BlockRecipeManager.isBlocked(capability.get().getOwner(), recipe, block.getLevel())) {
            callback.setReturnValue(isAwake ? TickRateModulation.IDLE : TickRateModulation.SLEEP);
        }
    }
}
