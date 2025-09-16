package com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PotionBrewing.class)
public interface PotionBrewingAccessor {
    @Accessor(value = "POTION_MIXES")
    static List<PotionBrewing.Mix<Potion>> getPotionMixes() {
        throw new RuntimeException("PotionBrewingAccessor.getPotionMixes");
    }

    @Accessor(value = "CONTAINER_MIXES")
    static List<PotionBrewing.Mix<Item>> getContainerMixes() {
        throw new RuntimeException("PotionBrewingAccessor.getContainerMixes");
    }
}
