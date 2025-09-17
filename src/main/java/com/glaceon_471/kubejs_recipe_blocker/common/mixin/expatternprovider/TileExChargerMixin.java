package com.glaceon_471.kubejs_recipe_blocker.common.mixin.expatternprovider;

import appeng.blockentity.misc.ChargerRecipes;
import appeng.recipes.handlers.ChargerRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.glodblock.github.extendedae.common.tileentities.TileExCharger;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(value = TileExCharger.class, remap = false)
public class TileExChargerMixin {
    @Redirect(
        method = "doWork",
        at = @At(
            value = "INVOKE",
            target = "Lappeng/blockentity/misc/ChargerRecipes;findRecipe(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lappeng/recipes/handlers/ChargerRecipe;"
        )
    )
    private ChargerRecipe doWork(Level level, ItemStack input) {
        TileExCharger self = (TileExCharger)(Object)this;
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(self);
        if (capability.isEmpty() || capability.get().getNonOwner()) {
            return null;
        }
        ChargerRecipe recipe = ChargerRecipes.findRecipe(level, input);
        return BlockRecipeManager.isBlocked(capability.get().getOwner(), level, recipe, self) ? null : recipe;
    }
}
