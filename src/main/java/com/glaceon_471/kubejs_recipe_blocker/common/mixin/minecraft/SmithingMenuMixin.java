package com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(SmithingMenu.class)
public class SmithingMenuMixin {
    @Shadow
    @Final
    private Level level;

    @Redirect(
        method = "createResult",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;isEmpty()Z"
        )
    )
    private boolean createResult(List<SmithingRecipe> instance) {
        Player player = ((ItemCombinerMenuAccessor)this).getPlayer();
        return instance.isEmpty() || BlockRecipeManager.isBlocked(player.getUUID(), instance.get(0), level);
    }
}
