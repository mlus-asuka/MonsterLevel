package vip.fubuki.monsterlevel;

import net.minecraft.world.effect.AttackDamageMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import vip.fubuki.monsterlevel.config.ConfigForge;

import java.util.Iterator;
import java.util.Objects;

@Mod(MonsterLevel.MODID)
public class MonsterLevel {

    public static final String MODID="monsterlevel";
    private final ConfigForge configuration = new ConfigForge();


    public MonsterLevel() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(Type.COMMON, this.configuration.getSpec());
        MinecraftForge.EVENT_BUS.register(this);
        //if (FMLEnvironment.dist == Dist.CLIENT) {}
    }


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
            if(mob.getMobType()== MobType.UNDEAD) {
                MobEffectInstance Regeneration = new MobEffectInstance(Objects.requireNonNull(MobEffect.byId(10)), 86400, RegenerationLevel - 1, false, false);
                mob.addEffect(Regeneration);
            }

            TotalLevel-=RegenerationLevel;
            if (TotalLevel<0) return;
            int SpeedLevel= (int)Math.floor(configuration.getMAX_SPEED_LEVEL().get()*Math.random());
            MobEffectInstance Speed=new MobEffectInstance(Objects.requireNonNull(MobEffect.byId(1)));
            mob.addEffect(Speed);
            TotalLevel-=SpeedLevel;

            if (TotalLevel<0) return;
            int ResistanceLevel= (int)Math.ceil(configuration.getMAX_RESISTANCE_LEVEL().get()*Math.random());
            ResistanceLevel=Math.min(ResistanceLevel,TotalLevel)/10;
            if(ResistanceLevel==0) return;
            MobEffectInstance Resistance=new MobEffectInstance(Objects.requireNonNull(MobEffect.byId(11)),86400,ResistanceLevel-1,false,false);
            mob.addEffect(Resistance);

        }
    }
    @SubscribeEvent
    public void onHitedByProjectile(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player){
        if (event.getSource() == null) return;
        if (event.getSource().getDirectEntity() instanceof Projectile projectile) {
            Mob owner = (Mob) projectile.getOwner();
            if((Entity)owner instanceof Player) return;
            if (!owner.hasEffect(Objects.requireNonNull(MobEffect.byId(5)))) return;
            int level = owner.getEffect(Objects.requireNonNull(MobEffect.byId(5))).getAmplifier() + 1;
            Iterator<ItemStack> iterator= player.getArmorSlots().iterator();
            int ProtectionLevel=0;
            int Projectile_Protection=0;
            while (iterator.hasNext()){
                ProtectionLevel+=iterator.next().getEnchantmentLevel(Enchantment.byId(0));
                Projectile_Protection+=iterator.next().getEnchantmentLevel(Enchantment.byId(4));
            }
            int Resistance=0;
            if(player.hasEffect(Objects.requireNonNull(MobEffect.byId(11)))){
                Resistance=player.getEffect(Objects.requireNonNull(MobEffect.byId(11))).getAmplifier()+1;
            }
            float percentage= (float) (1-(Math.max((ProtectionLevel+Projectile_Protection*2)/25,0.8))*(1-Resistance*0.2));
            float damage = (float) (percentage*(3*level * ( 1 - Math.min( 20, Math.max(player.getArmorValue() / 5, player.getArmorValue() - (3*level) / (2 + (player.getAttributeValue(Attributes.ARMOR_TOUGHNESS)/ 4) ) ) / 25 ))));
            player.setHealth(player.getHealth()-damage);
        }
        }
    }
}