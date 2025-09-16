package com.glaceon_471.kubejs_recipe_blocker.common.capability.block_owner;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public interface IBlockOwnerCapability extends INBTSerializable<CompoundTag> {
    void setOwner(UUID owner);

    void setNonOwner();

    UUID getOwner();

    boolean getNonOwner();
}
