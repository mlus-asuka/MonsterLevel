package vip.fubuki.monsterlevel.support;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IWailaConfig;

@WailaPlugin(WailaSupport.ID)
public class WailaSupport implements IWailaPlugin{

    public static final String ID="monsterlevel";
    public static final ResourceLocation LevelDisplay=new ResourceLocation(ID,"level_display");

    @Override
    public void register(IWailaCommonRegistration registration) {
        IWailaPlugin.super.register(registration);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(IWailaClientRegistration registration) {
        registration.addConfig(LevelDisplay, true);
        IWailaConfig.get().getPlugin().get(LevelDisplay);

        IWailaPlugin.super.registerClient(registration);
        registration.registerEntityComponent(WailaComponentProvider.INSTANCE, Mob.class);
    }
}
