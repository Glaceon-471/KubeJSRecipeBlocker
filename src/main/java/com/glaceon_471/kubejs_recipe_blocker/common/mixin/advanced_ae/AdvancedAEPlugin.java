package com.glaceon_471.kubejs_recipe_blocker.common.mixin.advanced_ae;

import com.glaceon_471.kubejs_recipe_blocker.common.mixin.AddonMixinPlugin;

public class AdvancedAEPlugin extends AddonMixinPlugin {
    @Override
    public String[] getRequiredModIds() {
        return new String[] { "ae2", "advanced_ae", "kubejs" };
    }
}
