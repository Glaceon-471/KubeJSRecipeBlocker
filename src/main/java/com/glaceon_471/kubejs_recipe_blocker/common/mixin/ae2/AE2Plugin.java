package com.glaceon_471.kubejs_recipe_blocker.common.mixin.ae2;

import com.glaceon_471.kubejs_recipe_blocker.common.mixin.AddonMixinPlugin;

public class AE2Plugin extends AddonMixinPlugin {
    @Override
    public String[] getRequiredModIds() {
        return new String[] { "ae2", "kubejs" };
    }
}
