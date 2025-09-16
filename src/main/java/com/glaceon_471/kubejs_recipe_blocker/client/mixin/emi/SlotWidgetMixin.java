package com.glaceon_471.kubejs_recipe_blocker.client.mixin.emi;

import com.glaceon_471.kubejs_recipe_blocker.common.RecipeBlocker;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.llamalad7.mixinextras.sugar.Local;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.widget.SlotWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(value = SlotWidget.class, remap = false)
public class SlotWidgetMixin {
    @Unique
    private static void addTooltip(List<ClientTooltipComponent> list) {
        list.add(ClientTooltipComponent.create(
            Component.translatable("text.kubejs_recipe_blocker.locked_recipe").withStyle(ChatFormatting.DARK_RED).getVisualOrderText()
        ));
    }

    @Inject(
        method = "addSlotTooltip",
        at = @At(
            value = "INVOKE",
            target = "Ldev/emi/emi/api/widget/SlotWidget;canResolve()Z"
        )
    )
    private void addSlotTooltip(List<ClientTooltipComponent> list, CallbackInfo callback, @Local EmiRecipe recipe) {
        if (recipe != null) {
            ResourceLocation id = Objects.requireNonNullElse(recipe.getId(), ResourceLocation.fromNamespaceAndPath(RecipeBlocker.MOD_ID, "null"));
            if (BlockRecipeManager.isBlocked(id)) {
                addTooltip(list);
            }
        }
    }
}
