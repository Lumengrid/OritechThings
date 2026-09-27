package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.entity.custom.AmethystFishEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import org.jetbrains.annotations.NotNull;

public class InfestedAmethystBlock extends InfestedBlock {

    public InfestedAmethystBlock(
            Block hostBlock,
            Properties properties
    ) {
        super(hostBlock, properties);
    }

    @Override
    protected void spawnAfterBreak(
            @NotNull BlockState state,
            @NotNull ServerLevel level,
            @NotNull BlockPos pos,
            @NotNull ItemStack stack,
            boolean dropExperience
    ) {
        boolean blockDropsEnabled =
                level.getGameRules()
                        .get(GameRules.BLOCK_DROPS);

        if (!blockDropsEnabled) {
            return;
        }

        if (EnchantmentHelper.hasTag(
                stack,
                EnchantmentTags.PREVENTS_INFESTED_SPAWNS
        )) {
            return;
        }

        RandomSource random = level.getRandom();

        if (!AmethystFishEntity.checkAmethystFishSpawnRules(
                ModEntities.AMETHYST_FISH.get(),
                level,
                EntitySpawnReason.SPAWNER,
                pos,
                random
        )) {
            return;
        }

        AmethystFishEntity fish =
                ModEntities.AMETHYST_FISH.get().create(
                        level,
                        EntitySpawnReason.SPAWNER
                );

        if (fish == null) {
            return;
        }

        fish.setPos(
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D
        );

        fish.setDeltaMovement(
                random.nextDouble() * 0.2D - 0.1D,
                0.2D,
                random.nextDouble() * 0.2D - 0.1D
        );

        level.addFreshEntity(fish);
    }
}
