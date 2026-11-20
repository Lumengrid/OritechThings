package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.entity.ModEntities;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.init.ItemContent;

import java.util.stream.Stream;

public class ModEntityLootTableProvider extends EntityLootSubProvider {
    protected ModEntityLootTableProvider() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return ModEntities.MOD_MOB_ENTITIES.getEntries().stream().map(RegistryObject::get);
    }

    @Override
    public void generate() {
        this.add(
                ModEntities.AMETHYST_FISH.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(ItemContent.FLUXITE)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                                                        .when(LootItemRandomChanceCondition.randomChance(0.5F))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.AMETHYST_SHARD)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                                                        .when(LootItemRandomChanceCondition.randomChance(0.5F))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.AMETHYST_BLOCK)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(-3.0F, 1.0F)))
                                                        .when(LootItemRandomChanceCondition.randomChance(0.2F))
                                        ).when(LootItemKilledByPlayerCondition.killedByPlayer())
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.BUDDING_AMETHYST)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(-5.0F, 1.0F)))
                                                        .when(LootItemRandomChanceCondition.randomChance(0.1F))
                                        ).when(LootItemKilledByPlayerCondition.killedByPlayer())
                        )
        );
    }
}
