package com.glaceon_471.kubejs_recipe_blocker.common.mixin.expatternprovider;

import appeng.api.networking.ticking.TickRateModulation;
import appeng.blockentity.AEBaseBlockEntity;
import appeng.blockentity.crafting.IMolecularAssemblerSupportedPattern;
import appeng.crafting.pattern.AECraftingPattern;
import appeng.crafting.pattern.AESmithingTablePattern;
import appeng.crafting.pattern.AEStonecuttingPattern;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2.AECraftingPatternAccessor;
import com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2.AESmithingTablePatternAccessor;
import com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2.AEStonecuttingPatternAccessor;
import com.glodblock.github.extendedae.common.me.CraftingThread;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = CraftingThread.class, remap = false)
public class CraftingThreadMixin {
    @Shadow
    @Final
    @NotNull
    private AEBaseBlockEntity host;

    @Shadow
    private boolean isAwake;

    @Shadow
    protected IMolecularAssemblerSupportedPattern myPlan;

    @Inject(
        method = "tick",
        at = @At(
            value = "FIELD",
            target = "Lcom/glodblock/github/extendedae/common/me/CraftingThread;reboot:Z",
            opcode = Opcodes.PUTFIELD
        ),
        cancellable = true
    )
    private void tick(int cards, int ticksSinceLastCall, CallbackInfoReturnable<TickRateModulation> callback) {
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(host);
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

        if (BlockRecipeManager.isBlocked(capability.get().getOwner(), recipe, host.getLevel())) {
            callback.setReturnValue(isAwake ? TickRateModulation.IDLE : TickRateModulation.SLEEP);
        }
    }
}
