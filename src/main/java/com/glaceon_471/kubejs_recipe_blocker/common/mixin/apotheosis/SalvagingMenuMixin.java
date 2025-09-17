package com.glaceon_471.kubejs_recipe_blocker.common.mixin.apotheosis;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.llamalad7.mixinextras.sugar.Local;
import dev.shadowsoffire.apotheosis.adventure.affix.salvaging.SalvagingMenu;
import dev.shadowsoffire.apotheosis.adventure.affix.salvaging.SalvagingRecipe;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collections;
import java.util.List;

@Mixin(value = SalvagingMenu.class, remap = false)
public abstract class SalvagingMenuMixin {
    @Shadow
    @Final
    protected Player player;

    @Redirect(
        method = "salvageAll",
        at = @At(
            value = "INVOKE",
            target = "Ldev/shadowsoffire/apotheosis/adventure/affix/salvaging/SalvagingMenu;salvageItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;"
        )
    )
    private List<ItemStack> salvageAll$SalvageItem(Level level, ItemStack stack) {
        SalvagingRecipe recipe = SalvagingMenu.findMatch(level, stack);
        if (BlockRecipeManager.isBlocked(player.getUUID(), level, recipe, (SalvagingMenu)(Object)this)) {
            return Collections.emptyList();
        }
        return SalvagingMenu.salvageItem(level, stack);
    }

    @Redirect(
        method = "salvageAll",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/inventory/Slot;set(Lnet/minecraft/world/item/ItemStack;)V"
        ),
        remap = true
    )
    private void salvageAll$Set(Slot instance, ItemStack arg, @Local List<ItemStack> outputs) {
        if (outputs != null && !outputs.isEmpty()) {
            instance.set(arg);
        }
    }
}
