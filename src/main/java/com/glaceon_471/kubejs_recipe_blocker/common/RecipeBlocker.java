package com.glaceon_471.kubejs_recipe_blocker.common;

import com.glaceon_471.kubejs_recipe_blocker.common.registries.ModRecipes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(RecipeBlocker.MOD_ID)
public final class RecipeBlocker {
    public static final String MOD_ID = "kubejs_recipe_blocker";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RecipeBlocker() {
        this(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public RecipeBlocker(IEventBus bus) {
        ModRecipes.register(bus);
    }
}
