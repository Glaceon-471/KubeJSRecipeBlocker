package com.glaceon_471.kubejs_recipe_blocker.client.mixin.jei;

import com.glaceon_471.kubejs_recipe_blocker.common.RecipeBlocker;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.library.gui.recipes.OutputSlotTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;

@Mixin(value = OutputSlotTooltipCallback.class, remap = false)
public class RecipeLayoutMixin {
    @Shadow
    @Final
    private ResourceLocation recipeName;

    @Inject(
        method = "onRichTooltip",
        at = @At(value = "INVOKE", target = "Lmezz/jei/api/gui/builder/ITooltipBuilder;add(Lnet/minecraft/network/chat/FormattedText;)V")
    )
    private void drawOverlays(IRecipeSlotView view, ITooltipBuilder tooltip, CallbackInfo callback) {
        Minecraft instance = Minecraft.getInstance();
        if (instance.level == null) {
            return;
        }
        Optional<? extends Recipe<?>> recipe = instance.level.getRecipeManager().byKey(recipeName);
        if (recipe.isPresent()) {
            if (BlockRecipeManager.isBlocked(Objects.requireNonNullElse(
                recipe.get().getId(), ResourceLocation.fromNamespaceAndPath(RecipeBlocker.MOD_ID, "null")
            ))) {
                tooltip.add(Component.translatable("text.kubejs_recipe_blocker.locked_recipe").withStyle(ChatFormatting.DARK_RED));
            }
        }
    }
}
