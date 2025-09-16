package com.glaceon_471.kubejs_recipe_blocker.common.mixin.expatternprovider;

import com.glaceon_471.kubejs_recipe_blocker.common.mixin.AddonMixinPlugin;

public class ExtendedAEPlugin extends AddonMixinPlugin {
    @Override
    public String[] getRequiredModIds() {
        return new String[] { "ae2", "expatternprovider", "kubejs" };
    }
}
