package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import appeng.api.config.Settings;
import appeng.blockentity.misc.CondenserBlockEntity;
import appeng.util.ConfigManager;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.integration.ae2.AE2CondenserRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(value = CondenserBlockEntity.class, remap = false)
public class CondenserBlockEntityMixin {
    @Shadow
    @Final
    private ConfigManager cm;

    @Redirect(
        method = "canAddOutput",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"
        ),
        remap = true
    )
    private boolean canAddOutput(ItemStack instance) {
        CondenserBlockEntity self = (CondenserBlockEntity)(Object)this;
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(self);
        if (capability.isEmpty() || capability.get().getNonOwner()) return false;
        return instance.isEmpty() && !BlockRecipeManager.isBlocked(capability.get().getOwner(), self.getLevel(), new AE2CondenserRecipe(
            cm.getSetting(Settings.CONDENSER_OUTPUT).requiredPower, instance
        ), self);
    }
}
