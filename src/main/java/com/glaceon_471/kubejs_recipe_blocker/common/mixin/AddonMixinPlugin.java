package com.glaceon_471.kubejs_recipe_blocker.common.mixin;

import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public abstract class AddonMixinPlugin implements IMixinConfigPlugin {
    public String[] getRequiredModIds() {
        return new String[] { };
    }

    public String[] getIncompatibleModIds() {
        return new String[] { };
    }

    @Override
    public void onLoad(String mixinPackage) { }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        LoadingModList list = LoadingModList.get();
        for(String id : getRequiredModIds()) {
            if(list.getModFileById(id) == null) {
                return false;
            }
        }
        for(String id : getIncompatibleModIds()) {
            if(list.getModFileById(id) != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) { }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) { }
}
