package com.glaceon_471.kubejs_recipe_blocker.common.mixin.apotheosis;

import com.glaceon_471.kubejs_recipe_blocker.common.integration.apotheosis.ApotheosisGemCuttingRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.cutting.GemCuttingMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GemCuttingMenu.class, remap = false)
public class GemCuttingMenuMixin {
    @Shadow
    @Final
    protected Player player;

    @Redirect(
        method = "clickMenuButton",
        at = @At(
            value = "INVOKE",
            target = "Ldev/shadowsoffire/apotheosis/adventure/socket/gem/cutting/GemCuttingMenu$GemCuttingRecipe;matches(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"
        ),
        remap = true
    )
    private boolean clickMenuButton(GemCuttingMenu.GemCuttingRecipe instance, ItemStack gem, ItemStack left, ItemStack bot, ItemStack right) {
        if (!instance.matches(gem, left, bot, right)) return false;
        return !BlockRecipeManager.isBlocked(player.getUUID(), player.level(), new ApotheosisGemCuttingRecipe(
            instance, gem, left, bot, right
        ), (GemCuttingMenu)(Object)this);
    }
}
