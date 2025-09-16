package com.glaceon_471.kubejs_recipe_blocker.common.mixin.apotheosis;

import com.glaceon_471.kubejs_recipe_blocker.common.mixin.AddonMixinPlugin;

public class ApotheosisPlugin extends AddonMixinPlugin {
    @Override
    public String[] getRequiredModIds() {
        return new String[] { "apotheosis", "placebo", "kubejs" };
    }
}
