package com.glaceon_471.kubejs_recipe_blocker.common.network.client;

import com.glaceon_471.kubejs_recipe_blocker.common.manager.BlockRecipeManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SPacketRequestBlockRecipes {
    public C2SPacketRequestBlockRecipes() { }

    public void encode(FriendlyByteBuf buf) { }

    public static C2SPacketRequestBlockRecipes decode(FriendlyByteBuf buf) {
        return new C2SPacketRequestBlockRecipes();
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player == null) {
                return;
            }
            BlockRecipeManager.syncBlockRecipes(player);
        });
        context.get().setPacketHandled(true);
    }
}
