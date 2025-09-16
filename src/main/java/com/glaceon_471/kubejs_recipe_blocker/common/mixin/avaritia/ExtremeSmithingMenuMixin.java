package com.glaceon_471.kubejs_recipe_blocker.common.mixin.avaritia;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft.ItemCombinerMenuAccessor;
import committee.nova.mods.avaritia.common.crafting.recipe.ExtremeSmithingRecipe;
import committee.nova.mods.avaritia.common.menu.ExtremeSmithingMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(value = ExtremeSmithingMenu.class, remap = false)
public class ExtremeSmithingMenuMixin {
    @Shadow
    @Final
    private Level level;

    @Redirect(
        method = "createResult",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;isEmpty()Z"
        ),
        remap = true
    )
    private boolean createResult(List<ExtremeSmithingRecipe> instance) {
        if (instance.isEmpty()) return true;
        return BlockRecipeManager.isBlocked(
            ((ItemCombinerMenuAccessor)this).getPlayer().getUUID(),
            instance.get(0), level
        );
    }
}
