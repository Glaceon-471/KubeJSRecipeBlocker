package com.glaceon_471.kubejs_recipe_blocker.common.integration;

import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class EmptySerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
    protected abstract T empty();

    @Override
    public T fromJson(ResourceLocation arg, JsonObject jsonObject) {
        return empty();
    }

    @Override
    @Nullable
    public T fromNetwork(ResourceLocation arg, FriendlyByteBuf arg2) {
        return empty();
    }

    @Override
    public void toNetwork(FriendlyByteBuf arg, T arg2) { }
}
