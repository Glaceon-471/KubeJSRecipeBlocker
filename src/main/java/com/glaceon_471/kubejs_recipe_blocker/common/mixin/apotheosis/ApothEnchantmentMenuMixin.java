package com.glaceon_471.kubejs_recipe_blocker.common.mixin.apotheosis;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import dev.shadowsoffire.apotheosis.ench.table.ApothEnchantmentMenu;
import dev.shadowsoffire.apotheosis.ench.table.EnchantingRecipe;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ApothEnchantmentMenu.class, remap = false)
public class ApothEnchantmentMenuMixin {
    @Shadow
    @Final
    protected Player player;

    @Redirect(
        method = "lambda$clickMenuButton$0",
        at = @At(
            value = "INVOKE",
            target = "Ldev/shadowsoffire/apotheosis/ench/table/EnchantingRecipe;findMatch(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;FFF)Ldev/shadowsoffire/apotheosis/ench/table/EnchantingRecipe;"
        )
    )
    private EnchantingRecipe lambda$clickMenuButton$0(Level level, ItemStack input, float eterna, float quanta, float arcana) {
        EnchantingRecipe recipe = EnchantingRecipe.findMatch(level, input, eterna, quanta, arcana);
        if (BlockRecipeManager.isBlocked(player.getUUID(), level, recipe, (ApothEnchantmentMenu)(Object)this)) {
            return null;
        }
        return recipe;
    }

    @Redirect(
        method = "lambda$getEnchantmentList$2",
        at = @At(
            value = "INVOKE",
            target = "Ldev/shadowsoffire/apotheosis/ench/table/EnchantingRecipe;findMatch(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;FFF)Ldev/shadowsoffire/apotheosis/ench/table/EnchantingRecipe;"
        )
    )
    private EnchantingRecipe lambda$getEnchantmentList$2(Level level, ItemStack input, float eterna, float quanta, float arcana) {
        EnchantingRecipe recipe = EnchantingRecipe.findMatch(level, input, eterna, quanta, arcana);
        if (BlockRecipeManager.isBlocked(player.getUUID(), level, recipe, (ApothEnchantmentMenu)(Object)this)) {
            return null;
        }
        return recipe;
    }
}
