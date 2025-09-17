package com.glaceon_471.kubejs_recipe_blocker.common;

import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapability;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.BlockOwnerCapabilityProvider;
import com.glaceon_471.kubejs_recipe_blocker.common.kubejs.RecipeBlockerPlugin;
import com.glaceon_471.kubejs_recipe_blocker.common.kubejs.server.BlockRecipesEventJS;
import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import com.glaceon_471.kubejs_recipe_blocker.common.registries.ModCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = RecipeBlocker.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class CommonForgeEventHandler {
    @SubscribeEvent
    public static void onAttachCapabilitiesBlockEntity(@NotNull AttachCapabilitiesEvent<BlockEntity> event) {
        event.addCapability(ModCapabilities.BLOCK_OWNER_CAPABILITY_LOCATION, new BlockOwnerCapabilityProvider());
    }

    @SubscribeEvent
    public static void onBlockEntityPlace(@NotNull BlockEvent.EntityPlaceEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockEntity block = level.getBlockEntity(pos);
        ServerPlayer player = event.getEntity() instanceof ServerPlayer p ? p : null;
        BlockOwnerCapability.getCapability(block).ifPresent(capability -> {
            if (player == null || player instanceof FakePlayer) capability.setNonOwner();
            else capability.setOwner(player.getUUID());
        });
    }

    @SubscribeEvent
    public static void onDatapackSync(@NotNull OnDatapackSyncEvent event) {
        BlockRecipeManager.clearFilter();
        RecipeBlockerPlugin.BLOCK_RECIPES.post(new BlockRecipesEventJS());
        BlockRecipeManager.syncBlockRecipes(event.getPlayerList());
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        onPlayerEvent(event);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        onPlayerEvent(event);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        onPlayerEvent(event);
    }

    private static void onPlayerEvent(@NotNull PlayerEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            BlockRecipeManager.syncBlockRecipes(player);
        }
    }
}
