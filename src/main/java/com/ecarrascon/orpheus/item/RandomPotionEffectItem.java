package com.ecarrascon.orpheus.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;

public class RandomPotionEffectItem extends HoneyBottleItem {
    public RandomPotionEffectItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide()) {
            pEntityLiving.addEffect(getRandomEffect(pLevel));
            pLevel.playSound(null, pEntityLiving.blockPosition(), SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.PLAYERS, 1, 1);
            pStack.shrink(1);
        }
        return super.finishUsingItem(pStack, pLevel, pEntityLiving);
    }

    private MobEffectInstance getRandomEffect(Level pLevel) {
        List<MobEffect> possibleEffects = Arrays.asList(
                MobEffects.MOVEMENT_SPEED,
                MobEffects.DAMAGE_BOOST,
                MobEffects.ABSORPTION,
                MobEffects.DAMAGE_RESISTANCE,
                MobEffects.REGENERATION
        );

        MobEffect randomEffect = possibleEffects.get(pLevel.getRandom().nextInt(0, possibleEffects.size() - 1));

        return new MobEffectInstance(randomEffect, 1800, 3);
    }
}
