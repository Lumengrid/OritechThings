package com.lumengrid.oritechthings.block.custom;

import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.entity.custom.AmethystFishEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class InfestedAmethystBlock extends InfestedBlock {
    public InfestedAmethystBlock(Block hostBlock, Properties properties) {
        super(hostBlock, properties);
    }

    @Override
    protected void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        if (!level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) || EnchantmentHelper.hasTag(stack, EnchantmentTags.PREVENTS_INFESTED_SPAWNS)) {
            return;
        }
        if (!AmethystFishEntity.checkAmethystFishSpawnRules(ModEntities.AMETHYST_FISH.get(), level,
                MobSpawnType.SPAWNER, pos, RandomSource.create())) {
            return;
        }
        AmethystFishEntity amethystFish = ModEntities.AMETHYST_FISH.get().create(level);
        if (amethystFish != null) {
            amethystFish.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            amethystFish.setDeltaMovement(level.random.nextDouble() * 0.2 - 0.1, 0.2, level.random.nextDouble() * 0.2 - 0.1);
            level.addFreshEntity(amethystFish);
        }
    }
}

