package com.glaceon_471.kubejs_recipe_blocker.common.network.server;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class S2CPacketSyncBlockRecipes {
    private final List<ResourceLocation> recipes;

    public S2CPacketSyncBlockRecipes(List<ResourceLocation> recipes) {
        this.recipes = recipes;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.recipes.size());
        for (ResourceLocation recipe : this.recipes) {
            buf.writeResourceLocation(recipe);
        }
    }

    public static S2CPacketSyncBlockRecipes decode(FriendlyByteBuf buf) {
        List<ResourceLocation> recipes = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            recipes.add(buf.readResourceLocation());
        }
        return new S2CPacketSyncBlockRecipes(recipes);
    }

    public void handle(Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> BlockRecipeManager.setBlockRecipes(recipes));
        ctx.get().setPacketHandled(true);
    }
}
