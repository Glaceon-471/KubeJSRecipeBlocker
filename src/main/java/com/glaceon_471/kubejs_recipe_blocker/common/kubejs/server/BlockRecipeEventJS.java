package com.glaceon_471.kubejs_recipe_blocker.common.kubejs.server;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.UUID;

public class BlockRecipeEventJS {
    private final UUID uuid;
    private final Level level;
    private final Recipe<?> recipe;
    @Nullable
    private final BlockEntity block;
    @Nullable
    private final AbstractContainerMenu menu;
    private final Player player;
    private final ResourceLocation type;

    public BlockRecipeEventJS(UUID player, Level level, Recipe<?> recipe, @Nullable BlockEntity block, @Nullable AbstractContainerMenu menu) {
        this.uuid = player;
        this.level = level;
        this.recipe = recipe;
        this.block = block;
        this.menu = menu;
        this.player = level.getPlayerByUUID(player);
        this.type = ForgeRegistries.RECIPE_TYPES.getKey(recipe.getType());
    }

    public UUID getUUID() {
        return uuid;
    }

    public Level getLevel() {
        return level;
    }

    public Recipe<?> getRecipe() {
        return recipe;
    }

    @Nullable
    public BlockEntity getBlock() {
        return block;
    }

    @Nullable
    public AbstractContainerMenu getMenu() {
        return menu;
    }

    public Player getPlayer() {
        return player;
    }

    public ResourceLocation getType() {
        return type;
    }
}
