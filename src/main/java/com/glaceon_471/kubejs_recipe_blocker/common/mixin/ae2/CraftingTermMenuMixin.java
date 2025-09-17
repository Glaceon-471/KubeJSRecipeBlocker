package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import appeng.menu.me.items.CraftingTermMenu;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.Recipe;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = CraftingTermMenu.class, remap = false)
public class CraftingTermMenuMixin {
    @Shadow
    private Recipe<CraftingContainer> currentRecipe;

    @Redirect(
        method = "updateCurrentRecipeAndOutput",
        at = @At(
            value = "FIELD",
            target = "Lappeng/menu/me/items/CraftingTermMenu;currentRecipe:Lnet/minecraft/world/item/crafting/Recipe;",
            opcode = Opcodes.PUTFIELD
        )
    )
    private void updateCurrentRecipeAndOutput(CraftingTermMenu instance, Recipe<CraftingContainer> value) {
        Player player = instance.getPlayer();
        currentRecipe = BlockRecipeManager.isBlocked(player.getUUID(), player.level(), value, instance) ? null : value;
    }
}
