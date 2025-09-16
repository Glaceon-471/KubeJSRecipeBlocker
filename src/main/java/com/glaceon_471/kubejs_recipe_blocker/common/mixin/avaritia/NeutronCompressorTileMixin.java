package com.glaceon_471.kubejs_recipe_blocker.common.mixin.avaritia;

import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.llamalad7.mixinextras.sugar.Local;
import committee.nova.mods.avaritia.api.common.crafting.ICompressorRecipe;
import committee.nova.mods.avaritia.common.tile.NeutronCompressorTile;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(value = NeutronCompressorTile.class, remap = false)
public class NeutronCompressorTileMixin {
    @Shadow
    private ICompressorRecipe recipe;

    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lcommittee/nova/mods/avaritia/api/common/crafting/ICompressorRecipe;matches(Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Z"
        ),
        remap = true
    )
    private static boolean tickMatches(
        ICompressorRecipe instance, Container container, Level level,
        @Local(argsOnly = true) NeutronCompressorTile block
    ) {
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(block);

        return instance.matches(container, level) && capability.isPresent() && !capability.get().getNonOwner() && !BlockRecipeManager.isBlocked(
            capability.get().getOwner(), instance, level
        );
    }

    @Redirect(
        method = "tick",
        at = @At(
            value = "FIELD",
            target = "Lcommittee/nova/mods/avaritia/common/tile/NeutronCompressorTile;recipe:Lcommittee/nova/mods/avaritia/api/common/crafting/ICompressorRecipe;",
            opcode = Opcodes.PUTFIELD
        )
    )
    private static void tickOrElse(
        NeutronCompressorTile instance, ICompressorRecipe value,
        @Local(argsOnly = true) Level level,
        @Local(argsOnly = true) NeutronCompressorTile block
    ) {
        NeutronCompressorTileMixin mixin = (NeutronCompressorTileMixin)(Object)instance;
        if (value == null) {
            mixin.recipe = null;
            return;
        }
        Optional<IBlockOwnerCapability> capability = BlockOwnerCapability.getCapability(block);
        mixin.recipe = capability.isEmpty() || capability.get().getNonOwner() || BlockRecipeManager.isBlocked(
            capability.get().getOwner(), value, level
        ) ? null : value;
    }
}
