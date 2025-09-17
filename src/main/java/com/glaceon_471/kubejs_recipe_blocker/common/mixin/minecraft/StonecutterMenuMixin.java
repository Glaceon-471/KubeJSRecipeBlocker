package com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(StonecutterMenu.class)
public class StonecutterMenuMixin {
    @Unique
    private Player player;

    @Shadow
    @Final
    private Level level;

    @Shadow
    private List<StonecutterRecipe> recipes;

    @Inject(
        method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
        at = @At("RETURN")
    )
    public void init(int id, Inventory inventory, ContainerLevelAccess container, CallbackInfo callback) {
        this.player = inventory.player;
    }

    @Redirect(
        method = "setupRecipeList",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/inventory/StonecutterMenu;recipes:Ljava/util/List;",
            opcode = Opcodes.PUTFIELD
        )
    )
    private void setupRecipeList(StonecutterMenu instance, List<StonecutterRecipe> value) {
        UUID uuid = player.getUUID();
        value.removeIf(recipe -> BlockRecipeManager.isBlocked(uuid, level, recipe, instance));
        recipes = value;
    }
}
