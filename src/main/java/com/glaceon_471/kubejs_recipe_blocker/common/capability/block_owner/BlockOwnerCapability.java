package com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner;

import com.glaceon_471.kubejs_recipe_blocker.common.registries.ModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class BlockOwnerCapability implements IBlockOwnerCapability {
    private UUID owner;
    private boolean nonOwner;

    public BlockOwnerCapability() {
        this.owner = null;
        this.nonOwner = true;
    }

    @Override
    public void setOwner(@NotNull UUID owner) {
        this.owner = owner;
        this.nonOwner = false;
    }

    @Override
    public void setNonOwner() {
        this.owner = null;
        this.nonOwner = true;
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public boolean getNonOwner() {
        return this.nonOwner;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (this.owner != null) tag.putUUID("owner", this.owner);
        tag.putBoolean("non_owner", this.nonOwner);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.owner = tag.contains("owner") ? tag.getUUID("owner") : null;
        this.nonOwner = tag.getBoolean("non_owner");
    }

    @NotNull
    public static Optional<IBlockOwnerCapability> getCapability(BlockEntity entity) {
        if (entity == null) return Optional.empty();
        return entity.getCapability(ModCapabilities.BLOCK_OWNER_CAPABILITY).resolve();
    }
}
