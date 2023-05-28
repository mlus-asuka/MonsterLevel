package vip.fubuki.monsterlevel;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import vip.fubuki.monsterlevel.config.ConfigForge;

import java.util.Objects;

@Mod(MonsterLevel.MODID)
public class MonsterLevel {

    public static final String MODID="monsterlevel";
    private final ConfigForge configuration = new ConfigForge();

    public MonsterLevel() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(Type.COMMON, this.configuration.getSpec());
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        //if (FMLEnvironment.dist == Dist.CLIENT) {}
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof Mob mob){
            if(mob.getTags().contains("hasLevel")) return;
            String name = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType())).toString();

            if(!this.configuration.getACTIVE_ENTITY().get().contains(name)) return;
            mob.addTag("hasLevel");
            int TotalLevel= configuration.getTOTAL_MAX_LEVEL().get();
            int HealthLevel= (int)Math.ceil(configuration.getMAX_HEALTH_LEVEL().get()*Math.random());
            MobEffectInstance Health=new MobEffectInstance(Objects.requireNonNull(MobEffect.byId(21)),86400,HealthLevel-1,false,false);
            mob.addEffect(Health);
            mob.heal(HealthLevel*4);

            TotalLevel-=HealthLevel;
            int StrengthLevel=(int)Math.ceil(configuration.getMAX_STRENGTH_LEVEL().get()*Math.random());
            StrengthLevel= Math.min(StrengthLevel, TotalLevel);
            MobEffectInstance Strength=new MobEffectInstance(Objects.requireNonNull(MobEffect.byId(5)),86400,StrengthLevel-1,false,false);
            mob.addEffect(Strength);

            TotalLevel-=StrengthLevel;
            if (TotalLevel<0) return;
            int RegenerationLevel=(int)Math.ceil(configuration.getMAX_REGENERATION_LEVEL().get()*Math.random());
            RegenerationLevel=Math.min(RegenerationLevel,TotalLevel);
            MobEffectInstance Regeneration=new MobEffectInstance(Objects.requireNonNull(MobEffect.byId(10)),86400,RegenerationLevel-1,false,false);
            mob.addEffect(Regeneration);

            TotalLevel-=RegenerationLevel;
            if (TotalLevel<0) return;
            int ResistanceLevel= (int)Math.ceil(configuration.getMAX_RESISTANCE_LEVEL().get()*Math.random());
            ResistanceLevel=Math.min(ResistanceLevel,TotalLevel)/10;
            if(ResistanceLevel==0) return;
            MobEffectInstance Resistance=new MobEffectInstance(Objects.requireNonNull(MobEffect.byId(11)),86400,ResistanceLevel-1,false,false);
            mob.addEffect(Resistance);
        }
    }
}