package com.glaceon_471.kubejs_recipe_blocker.common.registries;

import com.glaceon_471.kubejs_recipe_blocker.common.RecipeBlocker;
import com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner.IBlockOwnerCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final ResourceLocation BLOCK_OWNER_CAPABILITY_LOCATION = ResourceLocation.fromNamespaceAndPath(RecipeBlocker.MOD_ID, "block_owner");
    public static final Capability<IBlockOwnerCapability> BLOCK_OWNER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
}
