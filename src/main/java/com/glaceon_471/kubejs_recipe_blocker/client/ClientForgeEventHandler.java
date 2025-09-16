package com.glaceon_471.kubejs_recipe_blocker.client;

import com.glaceon_471.kubejs_recipe_blocker.client.listener.RequestBlockRecipesListener;
import com.glaceon_471.kubejs_recipe_blocker.common.RecipeBlocker;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = RecipeBlocker.MOD_ID, value = Dist.CLIENT, bus = Bus.FORGE)
public class ClientForgeEventHandler {
    @SubscribeEvent
    public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(RequestBlockRecipesListener.INSTANCE);
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
            Commands.literal("request_block_recipes").executes((source) -> {
                BlockRecipeManager.requestBlockRecipes();
                source.getSource().sendSystemMessage(Component.translatable("text.kubejs_recipe_blocker.request_block_recipes"));
                return 1;
            })
        );
    }
}
