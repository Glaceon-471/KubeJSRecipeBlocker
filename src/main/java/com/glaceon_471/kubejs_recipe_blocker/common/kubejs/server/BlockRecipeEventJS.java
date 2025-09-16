package com.glaceon_471.kubejs_recipe_blocker.common.kubejs.server;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class BlockRecipeEventJS<T extends Recipe<?>> {
    private final UUID uuid;
    private final T recipe;
    private final Level level;
    private final Player player;
    private final ResourceLocation type;

    public BlockRecipeEventJS(UUID player, T recipe, Level level) {
        this.uuid = player;
        this.recipe = recipe;
        this.level = level;
        this.player = level.getPlayerByUUID(player);
        this.type = ForgeRegistries.RECIPE_TYPES.getKey(recipe.getType());
    }

    public UUID getUUID() {
        return uuid;
    }

    public T getRecipe() {
        return recipe;
    }

    public Level getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }

    public ResourceLocation getType() {
        return type;
    }
}
