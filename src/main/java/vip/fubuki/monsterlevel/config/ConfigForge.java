package vip.fubuki.monsterlevel.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigForge{

    private final ForgeConfigSpec COMMON_CONFIG;

    private final IntValue TOTAL_MAX_LEVEL;

    private final IntValue MAX_RESISTANCE_LEVEL;

    private final IntValue MAX_HEALTH_LEVEL;

    private final IntValue MAX_REGENERATION_LEVEL;

    private final IntValue MAX_STRENGTH_LEVEL;

    private final ForgeConfigSpec.ConfigValue<List<? extends String>> ACTIVE_ENTITY;

    public ConfigForge() {

        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        // General Configs
        builder.comment("General settings for the mod.");
        builder.push("general");

        builder.comment("Max level of one mob.");
        builder.comment("0<=MaxLevel<=100");
        this.TOTAL_MAX_LEVEL = builder.defineInRange("MaxLevel", 20,0,100);

        builder.comment("Max Health level of one mob.");
        builder.comment("0<=MaxHealthLevel<=50");
        this.MAX_HEALTH_LEVEL = builder.defineInRange("MaxHealthLevel", 5,0,50);

        builder.comment("Max Strength level of one mob.");
        builder.comment("0<=MaxStrengthLevel<=50");
        this.MAX_STRENGTH_LEVEL = builder.defineInRange("MaxSpeedLevel", 5,0,50);

        builder.comment("Max Regeneration level of one mob.");
        builder.comment("0<=MaxRegenerationLevel<=50");
        this.MAX_REGENERATION_LEVEL = builder.defineInRange("MaxRegenerationLevel", 5,0,50);

        builder.comment("Max Resistance level of one mob.");
        builder.comment("=Effect Amplifier*10");
        builder.comment("0<=MaxResistanceLevel<=50");
        this.MAX_RESISTANCE_LEVEL = builder.defineInRange("MaxResistanceLevel", 10,0,50);

        builder.comment("Active Entity List.");
        List<String> defaultList = Stream.of("minecraft:slime", "minecraft:silverfish", "minecraft:zombie",
                "minecraft:skeleton", "minecraft:creeper", "minecraft:spider", "minecraft:cave_spider",
                "minecraft:husk", "minecraft:stray", "minecraft:magma_cube", "minecraft:zombie_villager",
                "minecraft:drowned", "minecraft:guardian", "minecraft:elder_guardian", "minecraft:phantom",
                "minecraft:blaze", "minecraft:ghast", "minecraft:witch", "minecraft:hoglin", "minecraft:piglin_brute",
                "minecraft:piglin", "minecraft:zombified_piglin", "minecraft:enderman", "minecraft:evoker", "minecraft:vindicator",
                "minecraft:pillager", "minecraft:ravager", "minecraft:shulker", "minecraft:wither_skeleton").collect(Collectors.toList());
        this.ACTIVE_ENTITY= builder.defineList("ActiveEntityList", defaultList, o->o instanceof String);

        this.COMMON_CONFIG = builder.build();
    }

    public ForgeConfigSpec getSpec () {

        return this.COMMON_CONFIG;
    }

    public IntValue getTOTAL_MAX_LEVEL() {
        return TOTAL_MAX_LEVEL;
    }

    public IntValue getMAX_RESISTANCE_LEVEL() {
        return MAX_RESISTANCE_LEVEL;
    }

    public IntValue getMAX_HEALTH_LEVEL() {
        return MAX_HEALTH_LEVEL;
    }

    public IntValue getMAX_REGENERATION_LEVEL() {return MAX_REGENERATION_LEVEL;}

    public IntValue getMAX_STRENGTH_LEVEL() {
        return MAX_STRENGTH_LEVEL;
    }

    public ForgeConfigSpec.ConfigValue<List<? extends String>> getACTIVE_ENTITY() {return ACTIVE_ENTITY;}
}
