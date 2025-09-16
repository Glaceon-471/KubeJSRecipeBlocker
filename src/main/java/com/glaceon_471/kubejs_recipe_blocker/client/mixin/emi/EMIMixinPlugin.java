package com.glaceon_471.kubejs_recipe_blocker.client.mixin.emi;

import com.glaceon_471.kubejs_recipe_blocker.common.mixin.AddonMixinPlugin;

public class EMIMixinPlugin extends AddonMixinPlugin {
    @Override
    public String[] getRequiredModIds() {
        return new String[] { "emi" };
    }
}
