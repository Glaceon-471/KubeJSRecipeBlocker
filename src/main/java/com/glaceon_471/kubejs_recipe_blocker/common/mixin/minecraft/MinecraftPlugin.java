package com.glaceon_471.kubejs_recipe_blocker.common.mixin.minecraft;

import com.glaceon_471.kubejs_recipe_blocker.common.mixin.AddonMixinPlugin;

public class MinecraftPlugin extends AddonMixinPlugin {
    @Override
    public String[] getRequiredModIds() {
        return new String[] { "kubejs" };
    }
}
