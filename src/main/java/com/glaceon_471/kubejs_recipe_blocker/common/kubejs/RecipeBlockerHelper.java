package com.glaceon_471.kubejs_recipe_blocker.common.kubejs;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class RecipeBlockerHelper {
    private RecipeBlockerHelper() { }

    public static void syncBlockRecipes(MinecraftServer server) {
        BlockRecipeManager.syncBlockRecipes(server.getPlayerList());
    }

    public static void syncBlockRecipes(ServerLevel server) {
        syncBlockRecipes(server.getServer());
    }

    public static void syncBlockRecipes(ServerPlayer player) {
        BlockRecipeManager.syncBlockRecipes(player);
    }

    public static void requestBlockRecipes() {
        BlockRecipeManager.requestBlockRecipes();
    }
}
