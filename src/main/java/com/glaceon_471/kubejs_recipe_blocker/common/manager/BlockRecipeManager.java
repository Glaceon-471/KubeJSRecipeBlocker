package com.glaceon_471.kubejs_recipe_blocker.common.manager;

import com.glaceon_471.kubejs_recipe_blocker.common.RecipeBlocker;
import com.glaceon_471.kubejs_recipe_blocker.common.kubejs.server.BlockRecipeEventJS;
import com.glaceon_471.kubejs_recipe_blocker.common.network.NetworkHandler;
import com.glaceon_471.kubejs_recipe_blocker.common.network.client.C2SPacketRequestBlockRecipes;
import com.glaceon_471.kubejs_recipe_blocker.common.network.server.S2CPacketSyncBlockRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class BlockRecipeManager {
    private static final List<Predicate<BlockRecipeEventJS<Recipe<?>>>> ALL_PREDICATES = new ArrayList<>();
    private static final Map<RecipeType<?>, List<Predicate<BlockRecipeEventJS<Recipe<?>>>>> PREDICATES = new HashMap<>();
    private static final List<ResourceLocation> BLOCK_RECIPES = new ArrayList<>();

    public static void addPredicate(Predicate<BlockRecipeEventJS<Recipe<?>>> predicate) {
        ALL_PREDICATES.add(predicate);
    }

    public static void addPredicate(RecipeType<?> type, Predicate<BlockRecipeEventJS<Recipe<?>>> predicate) {
        if (!PREDICATES.containsKey(type)) {
            PREDICATES.put(type, new ArrayList<>());
        }
        PREDICATES.get(type).add(predicate);
    }

    public static void resetPredicates() {
        PREDICATES.clear();
        ALL_PREDICATES.clear();
    }

    public static <T extends Recipe<?>> boolean isBlocked(UUID player, @Nullable T recipe, Level level) {
        if (recipe == null) {
            return true;
        }
        BlockRecipeEventJS<Recipe<?>> event = new BlockRecipeEventJS<>(player, recipe, level);
        RecipeType<?> type = recipe.getType();
        if (PREDICATES.containsKey(type)) {
            for (Predicate<BlockRecipeEventJS<Recipe<?>>> predicate : PREDICATES.get(type)) {
                if (predicate.test(event)) {
                    return true;
                }
            }
        }
        for (Predicate<BlockRecipeEventJS<Recipe<?>>> predicate : ALL_PREDICATES) {
            if (predicate.test(event)) {
                return true;
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isBlocked(ResourceLocation location) {
        return BLOCK_RECIPES.stream().anyMatch(location::equals);
    }

    public static void setBlockRecipes(List<ResourceLocation> recipes) {
        RecipeBlocker.LOGGER.info("setBlockRecipes size: {}", recipes.size());
        BLOCK_RECIPES.clear();
        BLOCK_RECIPES.addAll(recipes);
    }

    public static void syncBlockRecipes(PlayerList list) {
        for (ServerPlayer player : list.getPlayers()) {
            syncBlockRecipes(player);
        }
    }

    public static void syncBlockRecipes(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server == null) return;
        List<ResourceLocation> recipes = new ArrayList<>();
        UUID uuid = player.getUUID();
        RecipeManager manager = server.getRecipeManager();
        Level level = player.level();
        for (Recipe<?> recipe : manager.getRecipes()) {
            if (isBlocked(uuid, recipe, level)) {
                recipes.add(recipe.getId());
            }
        }
        NetworkHandler.instance.send(PacketDistributor.PLAYER.with(() -> player), new S2CPacketSyncBlockRecipes(recipes));
    }

    @OnlyIn(Dist.CLIENT)
    public static void requestBlockRecipes() {
        RecipeBlocker.LOGGER.info("Request Block Recipes");
        NetworkHandler.instance.sendToServer(new C2SPacketRequestBlockRecipes());
    }
}
