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
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class BlockRecipeManager {
    private static final Map<RecipeType<?>, List<Predicate<BlockRecipeEventJS>>> RECIPE_TYPE_FILTERS = new HashMap<>();
    private static final Map<BlockEntityType<?>, List<Predicate<BlockRecipeEventJS>>> BLOCK_ENTITY_TYPE_FILTERS = new HashMap<>();
    private static final Map<MenuType<?>, List<Predicate<BlockRecipeEventJS>>> MENU_TYPE_FILTERS = new HashMap<>();
    private static final List<Predicate<BlockRecipeEventJS>> FILTERS = new ArrayList<>();
    private static final List<ResourceLocation> BLOCK_RECIPES = new ArrayList<>();

    public static void addFilter(Predicate<BlockRecipeEventJS> filter) {
        FILTERS.add(filter);
    }

    public static void addFilter(RecipeType<?> type, Predicate<BlockRecipeEventJS> filter) {
        if (!RECIPE_TYPE_FILTERS.containsKey(type)) {
            RECIPE_TYPE_FILTERS.put(type, new ArrayList<>());
        }
        RECIPE_TYPE_FILTERS.get(type).add(filter);
    }

    public static void addFilter(BlockEntityType<?> type, Predicate<BlockRecipeEventJS> filter) {
        if (!BLOCK_ENTITY_TYPE_FILTERS.containsKey(type)) {
            BLOCK_ENTITY_TYPE_FILTERS.put(type, new ArrayList<>());
        }
        BLOCK_ENTITY_TYPE_FILTERS.get(type).add(filter);
    }

    public static void addFilter(MenuType<?> type, Predicate<BlockRecipeEventJS> filter) {
        if (!MENU_TYPE_FILTERS.containsKey(type)) {
            MENU_TYPE_FILTERS.put(type, new ArrayList<>());
        }
        MENU_TYPE_FILTERS.get(type).add(filter);
    }

    public static void clearFilter() {
        RECIPE_TYPE_FILTERS.clear();
        BLOCK_ENTITY_TYPE_FILTERS.clear();
        MENU_TYPE_FILTERS.clear();
        FILTERS.clear();
    }

    public static boolean isBlocked(UUID player, Level level, Recipe<?> recipe) {
        return isBlocked(player, level, recipe, null, null);
    }

    public static boolean isBlocked(UUID player, Level level, Recipe<?> recipe, BlockEntity block) {
        return isBlocked(player, level, recipe, block, null);
    }

    public static boolean isBlocked(UUID player, Level level, Recipe<?> recipe, AbstractContainerMenu menu) {
        return isBlocked(player, level, recipe, null, menu);
    }

    private static boolean isBlocked(UUID player, Level level, Recipe<?> recipe, @Nullable BlockEntity block, @Nullable AbstractContainerMenu menu) {
        if (recipe == null) {
            return true;
        }
        BlockRecipeEventJS event = new BlockRecipeEventJS(player, level, recipe, block, menu);
        RecipeType<?> recipe_type = recipe.getType();
        if (RECIPE_TYPE_FILTERS.containsKey(recipe_type)) {
            for (Predicate<BlockRecipeEventJS> filter : RECIPE_TYPE_FILTERS.get(recipe_type)) {
                if (!filter.test(event)) {
                    return true;
                }
            }
        }
        if (block != null) {
            BlockEntityType<?> block_type = block.getType();
            if (BLOCK_ENTITY_TYPE_FILTERS.containsKey(block_type)) {
                for (Predicate<BlockRecipeEventJS> filter : BLOCK_ENTITY_TYPE_FILTERS.get(block_type)) {
                    if (!filter.test(event)) {
                        return true;
                    }
                }
            }
        }
        if (menu != null) {
            MenuType<?> menu_type = menu.getType();
            if (MENU_TYPE_FILTERS.containsKey(menu_type)) {
                for (Predicate<BlockRecipeEventJS> filter : MENU_TYPE_FILTERS.get(menu_type)) {
                    if (!filter.test(event)) {
                        return true;
                    }
                }
            }
        }
        for (Predicate<BlockRecipeEventJS> filter : FILTERS) {
            if (!filter.test(event)) {
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
            if (isBlocked(uuid, level, recipe)) {
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
