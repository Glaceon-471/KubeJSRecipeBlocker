package com.glaceon_471.kubejs_recipe_blocker.common.network;

import com.glaceon_471.kubejs_recipe_blocker.common.RecipeBlocker;
import com.glaceon_471.kubejs_recipe_blocker.common.network.client.C2SPacketRequestBlockRecipes;
import com.glaceon_471.kubejs_recipe_blocker.common.network.server.S2CPacketSyncBlockRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1.0.0";
    public static SimpleChannel instance;
    private static int id = 0;

    public static void register() {
        instance = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(RecipeBlocker.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
        );

        // Server to Client
        register(S2CPacketSyncBlockRecipes.class, S2CPacketSyncBlockRecipes::encode, S2CPacketSyncBlockRecipes::decode, S2CPacketSyncBlockRecipes::handle);

        // Client to Server
        register(C2SPacketRequestBlockRecipes.class, C2SPacketRequestBlockRecipes::encode,C2SPacketRequestBlockRecipes::decode, C2SPacketRequestBlockRecipes::handle);
    }

    private static <M> void register(
        Class<M> messageType, BiConsumer<M, FriendlyByteBuf> encoder,
        Function<FriendlyByteBuf, M> decoder, BiConsumer<M, Supplier<NetworkEvent.Context>> messageConsumer
    ) {
        instance.registerMessage(id++, messageType, encoder, decoder, messageConsumer);
    }
}
