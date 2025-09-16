package com.glaceon_471.kubejs_recipe_blocker.common;

import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.network.NetworkHandler;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = RecipeBlocker.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonModEventHandler {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        NetworkHandler.register();
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(@NotNull RegisterCapabilitiesEvent event) {
        event.register(IBlockOwnerCapability.class);
    }
}
