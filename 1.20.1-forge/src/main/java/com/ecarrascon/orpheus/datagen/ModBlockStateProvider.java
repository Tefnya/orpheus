package com.ecarrascon.orpheus.datagen;

import com.ecarrascon.orpheus.Orpheus;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Orpheus.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }

}
