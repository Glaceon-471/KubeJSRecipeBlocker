package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import appeng.blockentity.misc.ChargerBlockEntity;
import appeng.blockentity.misc.ChargerRecipes;
import appeng.recipes.handlers.ChargerRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(value = ChargerBlockEntity.class, remap = false)
public class ChargerBlockEntityMixin {
    @Redirect(
        method = "doWork",
        at = @At(
            value = "INVOKE",
            target = "Lappeng/blockentity/misc/ChargerRecipes;findRecipe(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lappeng/recipes/handlers/ChargerRecipe;"
        )
    )
    private ChargerRecipe doWork(Level level, ItemStack input) {
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability((ChargerBlockEntity)(Object)this);
        if (capability.isEmpty() || capability.get().getNonOwner()) {
            return null;
        }
        ChargerRecipe recipe = ChargerRecipes.findRecipe(level, input);
        return BlockRecipeManager.isBlocked(capability.get().getOwner(), recipe, level) ? null : recipe;
    }
}
