package com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner;

import com.glaceon_471.kubejs_recipe_blocker.common.registries.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BlockOwnerCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    private final IBlockOwnerCapability instance;
    private final LazyOptional<IBlockOwnerCapability> optional;

    public BlockOwnerCapabilityProvider() {
        this.instance = new BlockOwnerCapability();
        this.optional = LazyOptional.of(() -> this.instance);
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
        return ModCapabilities.BLOCK_OWNER_CAPABILITY.orEmpty(capability, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        instance.deserializeNBT(tag);
    }
}
