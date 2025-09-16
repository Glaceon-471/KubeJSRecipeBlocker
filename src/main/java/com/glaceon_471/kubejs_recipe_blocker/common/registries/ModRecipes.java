package com.glaceon_471.kubejs_recipe_blocker.common.registries;

import com.glaceon_471.kubejs_recipe_blocker.common.RecipeBlocker;
import com.glaceon_471.kubejs_recipe_blocker.common.integration.ae2.AE2CondenserRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.integration.apotheosis.ApotheosisGemCuttingRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.integration.minecraft.MinecraftContainerBrewingRecipe;
import com.glaceon_471.kubejs_recipe_blocker.common.integration.minecraft.MinecraftPotionBrewingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    private static final DeferredRegister<RecipeType<?>> MOD_RECIPE_TYPE = DeferredRegister.create(Registries.RECIPE_TYPE, RecipeBlocker.MOD_ID);
    private static final DeferredRegister<RecipeSerializer<?>> MOD_RECIPE_SERIALIZER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, RecipeBlocker.MOD_ID);

    public static final RegistryObject<RecipeType<MinecraftContainerBrewingRecipe>> MINECRAFT_CONTAINER_BREWING_TYPE = registry("minecraft/container_brewing");
    public static final RegistryObject<MinecraftContainerBrewingRecipe.Serializer> MINECRAFT_CONTAINER_BREWING_SERIALIZER = MOD_RECIPE_SERIALIZER.register(
        "minecraft/container_brewing", MinecraftContainerBrewingRecipe.Serializer::new
    );

    public static final RegistryObject<RecipeType<MinecraftPotionBrewingRecipe>> MINECRAFT_POTION_BREWING_TYPE = registry("minecraft/potion_brewing");
    public static final RegistryObject<MinecraftPotionBrewingRecipe.Serializer> MINECRAFT_POTION_BREWING_SERIALIZER = MOD_RECIPE_SERIALIZER.register(
        "minecraft/potion_brewing", MinecraftPotionBrewingRecipe.Serializer::new
    );

    public static final RegistryObject<RecipeType<AE2CondenserRecipe>> AE2_CONDENSER_TYPE = registry("ae2/condenser");
    public static final RegistryObject<AE2CondenserRecipe.Serializer> AE2_CONDENSER_SERIALIZER = MOD_RECIPE_SERIALIZER.register(
        "ae2/condenser", AE2CondenserRecipe.Serializer::new
    );

    public static final RegistryObject<RecipeType<ApotheosisGemCuttingRecipe>> APOTHEOSIS_GEM_CUTTING_TYPE = registry("apotheosis/gem_cutting");
    public static final RegistryObject<ApotheosisGemCuttingRecipe.Serializer> APOTHEOSIS_GEM_CUTTING_SERIALIZER = MOD_RECIPE_SERIALIZER.register(
        "apotheosis/gem_cutting", ApotheosisGemCuttingRecipe.Serializer::new
    );

    public static void register(IEventBus bus) {
        MOD_RECIPE_TYPE.register(bus);
        MOD_RECIPE_SERIALIZER.register(bus);
    }

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registry(String name) {
        return MOD_RECIPE_TYPE.register(name, () -> simple(name));
    }

    private static <T extends Recipe<?>> RecipeType<T> simple(String name) {
        final String toString = RecipeBlocker.MOD_ID + ":" + name;
        return new RecipeType<>() {
            public String toString() {
                return toString;
            }
        };
    }
}
