package com.glaceon_471.kubejs_recipe_blocker.common.mixin.avaritia;

import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import committee.nova.mods.avaritia.common.crafting.recipe.ITierCraftingRecipe;
import committee.nova.mods.avaritia.common.menu.TierCraftMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(value = TierCraftMenu.class, remap = false)
public class TierCraftMenuMixin {
    @Shadow
    @Final
    private Level world;

    @Redirect(
        method = "slotsChanged",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/Optional;isPresent()Z"
        ),
        remap = true
    )
    private boolean slotsChanged(Optional<ITierCraftingRecipe> instance) {
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(((TierCraftMenu)(Object)this).getTileEntity());
        return instance.isPresent() && capability.isPresent() && !(capability.get().getNonOwner() || BlockRecipeManager.isBlocked(
            capability.get().getOwner(), instance.get(), world
        ));
    }
}
