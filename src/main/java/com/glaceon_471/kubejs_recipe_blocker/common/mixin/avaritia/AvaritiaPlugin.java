package com.glaceon_471.kubejs_recipe_blocker.common.mixin.avaritia;

import com.glaceon_471.kubejs_recipe_blocker.common.mixin.AddonMixinPlugin;

public class AvaritiaPlugin extends AddonMixinPlugin {
    @Override
    public String[] getRequiredModIds() {
        return new String[] { "avaritia", "kubejs" };
    }
}
