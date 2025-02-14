package dev.doctor4t.redwood.datagen;

import com.google.common.collect.ImmutableList;
import dev.doctor4t.redwood.Redwood;
import dev.doctor4t.redwood.index.RedwoodBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.concurrent.CompletableFuture;

public class RedwoodConfiguredFeatures extends FabricDynamicRegistryProvider {
    public static final RegistryKey<ConfiguredFeature<?, ?>> SEQUOIA = of("sequoia");

    public static RegistryKey<ConfiguredFeature<?, ?>> of(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Redwood.id(name));
    }

    public RedwoodConfiguredFeatures(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        entries.addAll(wrapperLookup.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
    }

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(
                featureRegisterable,
                SEQUOIA,
                Feature.TREE,
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(RedwoodBlocks.SEQUOIA_LOG),
                        new GiantTrunkPlacer(13, 2, 14),
                        BlockStateProvider.of(RedwoodBlocks.SEQUOIA_LEAVES),
                        new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(10, 14)),
                        new TwoLayersFeatureSize(1, 1, 2)
                )
                        .decorators(ImmutableList.of(new AlterGroundTreeDecorator(BlockStateProvider.of(Blocks.PODZOL))))
                        .build()
        );
    }

    @Override
    public String getName() {
        return Redwood.MOD_ID + "_configured_features";
    }
}
