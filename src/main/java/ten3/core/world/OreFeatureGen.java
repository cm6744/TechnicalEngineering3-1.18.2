package ten3.core.world;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import ten3.TConst;
import ten3.init.BlockInit;

import java.util.List;
import java.util.Objects;

import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;

@Mod.EventBusSubscriber(modid = TConst.modid, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreFeatureGen {

    static boolean alreadySetup;
    static void setup() {
        List<OreConfiguration.TargetBlockState> tinList =
                List.of(OreConfiguration.target(
                                STONE_ORE_REPLACEABLES,
                                BlockInit.getBlock("tin_ore").defaultBlockState()
                        ),
                        OreConfiguration.target(
                                DEEPSLATE_ORE_REPLACEABLES,
                                BlockInit.getBlock("deep_tin_ore").defaultBlockState()
                        ));
        List<OreConfiguration.TargetBlockState> nickelList =
                List.of(OreConfiguration.target(
                                STONE_ORE_REPLACEABLES,
                                BlockInit.getBlock("nickel_ore").defaultBlockState()
                        ),
                        OreConfiguration.target(
                                DEEPSLATE_ORE_REPLACEABLES,
                                BlockInit.getBlock("deep_nickel_ore").defaultBlockState()
                        ));
        tin_ore_cfg = FeatureUtils.register("tin_ore", Feature.ORE,
                new OreConfiguration(tinList, 10));
        nickel_ore_cfg = FeatureUtils.register("nickel_ore", Feature.ORE,
                new OreConfiguration(nickelList, 8, 0.2F));
        TIN = PlacementUtils.register("ore_tin", tin_ore_cfg,
                commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80),
                        VerticalAnchor.belowTop(48))));
        NKL = PlacementUtils.register("ore_nickel", nickel_ore_cfg,
                commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80),
                        VerticalAnchor.belowTop(12))));
    }

    public static Holder<ConfiguredFeature<OreConfiguration, ?>> tin_ore_cfg;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> nickel_ore_cfg;

    public static Holder<PlacedFeature> TIN;
    public static Holder<PlacedFeature> NKL;

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        BiomeGenerationSettingsBuilder genEvent = event.getGeneration();
        ResourceKey<Biome> biomeKey = ResourceKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName()));

        if (BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.OVERWORLD)) {

            if(!alreadySetup) {
                setup();
                alreadySetup = true;
            }
            genEvent.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TIN);
            genEvent.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, NKL);

        }
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

}
