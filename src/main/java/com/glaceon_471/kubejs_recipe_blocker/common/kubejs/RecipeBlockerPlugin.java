package com.glaceon_471.kubejs_recipe_blocker.common.kubejs;

import com.glaceon_471.kubejs_recipe_blocker.common.kubejs.server.BlockRecipesEventJS;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public class RecipeBlockerPlugin extends KubeJSPlugin {
    public static final EventGroup RECIPE_BLOCKER_EVENTS = EventGroup.of("RecipeBlockerEvents");
    public static final EventHandler BLOCK_RECIPES = RECIPE_BLOCKER_EVENTS.server("blockRecipes", () -> BlockRecipesEventJS.class);

    @Override
    public void registerEvents() {
        RECIPE_BLOCKER_EVENTS.register();
    }
}
