package com.ecarrascon.orpheus.registry;

import com.ecarrascon.orpheus.OrpheusMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


import java.util.function.Supplier;

public enum BlocksRegistry {



    PEGASUS_FEATHERS_BLOCK("pegasus_feathers_block", () -> new Block(FabricBlockSettings
            .of(Material.WOOL)
            .sounds(BlockSoundGroup.WOOL)
            .resistance(0.2f)
            .hardness(0.2f)
            ));

    private static FlammableBlockRegistry.Entry flammable(int burnChance, @SuppressWarnings("SameParameterValue") int spreadChance) {
        return new FlammableBlockRegistry.Entry(burnChance, spreadChance);
    }

    private static boolean isValidFlammableEntry(FlammableBlockRegistry.Entry flammableRate) {
        return flammableRate != null && flammableRate.getBurnChance() > 0 && flammableRate.getSpreadChance() > 0;
    }

    private final String pathName;
    private final Supplier<Block> blockSupplier;
    private final FlammableBlockRegistry.Entry flammableRate;
    private final boolean isCutout;
    private Block block;

    BlocksRegistry(String pathName, Supplier<Block> blockSupplier) {
        this(pathName, blockSupplier, false, new FlammableBlockRegistry.Entry(0, 0));
    }

    BlocksRegistry(String pathName, Supplier<Block> blockSupplier, boolean isCutout) {
        this(pathName, blockSupplier, isCutout, new FlammableBlockRegistry.Entry(0, 0));
    }

    BlocksRegistry(String pathName, Supplier<Block> blockSupplier, boolean isCutout, FlammableBlockRegistry.Entry flammableRate) {
        this.pathName = pathName;
        this.blockSupplier = blockSupplier;
        this.flammableRate = flammableRate;
        this.isCutout = isCutout;
    }

    public static void registerAll() {
        for (BlocksRegistry value : values()) {
            Block block = value.get();
            Registry.register(Registry.BLOCK, new Identifier(OrpheusMod.MOD_ID, value.pathName), block);
            if (isValidFlammableEntry(value.flammableRate)) {
                FlammableBlockRegistry.getDefaultInstance().add(block, value.flammableRate);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayer() {
        for (BlocksRegistry value : values()) {
            if (value.isCutout) {
                BlockRenderLayerMap.INSTANCE.putBlock(value.get(), RenderLayer.getCutout());
            }
        }
    }

    public Block get() {
        if (block == null) {
            block = blockSupplier.get();
        }

        return block;
    }

    public String getId() {
        return Registry.BLOCK.getId(get()).toString();
    }

}
