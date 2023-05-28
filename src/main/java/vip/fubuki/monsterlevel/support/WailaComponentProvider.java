package vip.fubuki.monsterlevel.support;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Mob;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.Objects;

public enum WailaComponentProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
            Mob target =(Mob) entityAccessor.getEntity();
            if(!target.getTags().contains("hasLevel")) return;
            int level=0;
             if(target.hasEffect(Objects.requireNonNull(MobEffect.byId(21)))){
                 level += Objects.requireNonNull(target.getEffect(Objects.requireNonNull(MobEffect.byId(21)))).getAmplifier()+1;
             }
            if(target.hasEffect(Objects.requireNonNull(MobEffect.byId(5)))){
                level += Objects.requireNonNull(target.getEffect(Objects.requireNonNull(MobEffect.byId(5)))).getAmplifier()+1;
            }
            if(target.hasEffect(Objects.requireNonNull(MobEffect.byId(10)))){
                level += Objects.requireNonNull(target.getEffect(Objects.requireNonNull(MobEffect.byId(10)))).getAmplifier()+1;
            }
            if(target.hasEffect(Objects.requireNonNull(MobEffect.byId(1)))){
                level += Objects.requireNonNull(target.getEffect(Objects.requireNonNull(MobEffect.byId(1)))).getAmplifier()+1;
            }
            if(target.hasEffect(Objects.requireNonNull(MobEffect.byId(11)))){
                level += (Objects.requireNonNull(target.getEffect(Objects.requireNonNull(MobEffect.byId(11)))).getAmplifier()+1)*10;
            }
            if(level==0) return;
            iTooltip.add(2,Component.literal("Level:"+level));
            //level correct
    }

    @Override
    public ResourceLocation getUid() {
        return WailaSupport.LevelDisplay;
    }

}
