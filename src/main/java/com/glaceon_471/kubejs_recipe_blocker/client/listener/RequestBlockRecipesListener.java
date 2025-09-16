package com.glaceon_471.kubejs_recipe_blocker.client.listener;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.jetbrains.annotations.NotNull;

public class RequestBlockRecipesListener implements ResourceManagerReloadListener {
    public static final RequestBlockRecipesListener INSTANCE = new RequestBlockRecipesListener();

    private RequestBlockRecipesListener() { }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager manager) {
        BlockRecipeManager.requestBlockRecipes();
    }
}
