package com.glaceon_471.kubejs_recipe_blocker.client.mixin.jei;

import com.glaceon_471.kubejs_recipe_blocker.common.mixin.AddonMixinPlugin;

public class JEIMixinPlugin extends AddonMixinPlugin {
    @Override
    public String[] getRequiredModIds() {
        return new String[] { "jei" };
    }
}
