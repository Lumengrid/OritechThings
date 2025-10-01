package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.util.DataGenUtil;

import static com.lumengrid.oritechthings.main.OritechThings.MOD_ID;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import rearth.oritech.init.ToolsContent;

public class ModLangProviderRuRu extends LanguageProvider {

    public ModLangProviderRuRu(PackOutput output) {
        super(output, MOD_ID, "ru_ru");
    }

    @Override
    protected void addTranslations() {

        //misc
        add("itemGroup." + MOD_ID, "Oritech: Вещи");
        add("message."+ToolsContent.EXO_JETPACK.getDescriptionId().replace("item.oritech.", "")+".energy_low", "§c⚠ Экзо-джетпак — мало энергии ⚠");
        add("tooltip." + MOD_ID + ".tiered_addons.chambers_desc", "Дополнительные камеры ");
        add("tooltip."+MOD_ID+".tier_addon", "Уровень ");
        add("tooltip."+MOD_ID+".state.on", "ВКЛ.");
        add("tooltip."+MOD_ID+".state.off", "ВЫКЛ.");
        
        

        //generic blocks
        ModBlocks.OTHER.getEntries().forEach(e -> addBlock(e,
                DataGenUtil.formatted(e.getRegisteredName())));
        //block addons
        ModBlocks.ADDONS.getEntries().forEach(e -> addBlock(e,
                DataGenUtil.specificReplace(e.getRegisteredName())));
        //generic items
        ModItems.ITEMS.getEntries().forEach(e -> addItem(e,
                DataGenUtil.formatted(e.getRegisteredName())));
        //generic mobs
        ModEntities.MOD_MOB_ENTITIES.getEntries().forEach(e -> addEntityType(e,
                DataGenUtil.formatted(e.getRegisteredName())));

        //particle accelerator speed sensor

        var AspeedSensor = MOD_ID + "." + DataGenUtil.getName(ModBlocks.ACCELERATOR_SPEED_SENSOR);
        
        add("gui." + AspeedSensor + ".title", "Датчик скорости");
        add("gui." + AspeedSensor + ".controller_not_set", "НЕ УСТАНОВЛЕНО!");
        add("gui." + AspeedSensor + ".controller", "Ускоритель частиц ");
        add("block." + AspeedSensor + ".controller_set", "Целевой ускоритель частиц установлен: ");
        add("block." + AspeedSensor + ".invalid_controller", "⚠ Недопустимый целевой ускоритель частиц ⚠");
        add("block." + AspeedSensor + ".invalid_controller.to_far",
                "⚠ Целевой ускоритель частиц слишком далеко — макс. дистанция 128 блоков ⚠");
        add("gui."+AspeedSensor+".speed_input","Вход скорости" );

        // frame placer

        var FramePlacer = MOD_ID + "." + DataGenUtil.getName(ModItems.FRAME_PLACER);

        add("message." + FramePlacer + ".missing_frame", "⚠ Установщик рам — отсутствует рама ⚠");
        add("tooltip." + FramePlacer,
                "Нажмите Shift+ПКМ по совместимой с рамой машине \nнапример, Разрушителю, чтобы автоматически построить структуру рамы");

        add("message." + FramePlacer + ".wrong_machine", "§c⚠ Несовместимая с рамой машина ⚠");
        add("message." + FramePlacer + ".not_assembled", "§c⚠ Машина ещё не собрана ⚠");

        add("message."+FramePlacer+".offset", "Смещение ");
        add("message."+FramePlacer+".abort", "Отмена");
        add("message."+FramePlacer+".confirm", "Подтвердить");

        add("message."+FramePlacer+".blocks_required", "Требуемые блоки рамы: ");
        add("message."+FramePlacer+".frame_size", "Задайте размеры рамы");

        //advanced target designator

        var TargetDesignator = MOD_ID + "." + DataGenUtil.getName(ModItems.ADVANCED_TARGET_DESIGNATOR);

        add("message." + TargetDesignator + ".position_invalid", "⚠ Недопустимое положение ⚠");
        add("message." + TargetDesignator + ".different_dimension", "⚠ Другая размерность ⚠");

        // (?) deprecated (?)
        add("tooltip." + MOD_ID + ".accelerator_target_designator",
                "Можно использовать для сохранения позиции ускорителя частиц");

        // Configuration translations
        add("configuration." + MOD_ID + ".title", "Конфигурация Oritech Things");
        
        // ExoJetPack Settings
        add("configuration." + MOD_ID + ".exoJetPack", "Настройки экзо-джетпака");
        add(MOD_ID + ".configuration.exoJetPack", "Настройки экзо-джетпака");
        add("configuration." + MOD_ID + ".exoJetPack.enabledCreativeFlight", "Включить творческий полёт");
        add("configuration." + MOD_ID + ".exoJetPack.enabledCreativeFlight.tooltip", "Включает творческий полёт при ношении экзо-джетпака");
        add("configuration." + MOD_ID + ".exoJetPack.rfThreshold", "RF порог");
        add("configuration." + MOD_ID + ".exoJetPack.rfThreshold.tooltip", "Минимальная RF энергия, необходимая для творческого полёта экзо-джетпака");
        
        // Tier Settings
        for (int i = 0; i < 8; i++) {
            add("configuration." + MOD_ID + ".tier" + i, "Дополнения Настройки уровня " + (i + 2));
            add(MOD_ID + ".configuration.tier" + i, "Дополнения Настройки уровня " + (i + 2));
        }
        
        // Configuration Parameters
        add("configuration." + MOD_ID + ".speedMultiplier", "Множитель скорости");
        add("configuration." + MOD_ID + ".speedMultiplier.tooltip", "Множитель скорости обработки машин");
        
        add("configuration." + MOD_ID + ".efficiencyDown", "Снижение эффективности");
        add("configuration." + MOD_ID + ".efficiencyDown.tooltip", "Штраф эффективности за улучшения скорости");
        
        add("configuration." + MOD_ID + ".efficiencyUp", "Повышение эффективности");
        add("configuration." + MOD_ID + ".efficiencyUp.tooltip", "Бонус эффективности за улучшения эффективности");
        
        add("configuration." + MOD_ID + ".capacitorCapacity", "Ёмкость конденсатора");
        add("configuration." + MOD_ID + ".capacitorCapacity.tooltip", "Дополнительная ёмкость хранения энергии");
        
        add("configuration." + MOD_ID + ".capacitorRate", "Скорость конденсатора");
        add("configuration." + MOD_ID + ".capacitorRate.tooltip", "Дополнительная скорость ввода/вывода энергии");
        
        add("configuration." + MOD_ID + ".acceptorCapacity", "Ёмкость приёмника");
        add("configuration." + MOD_ID + ".acceptorCapacity.tooltip", "Дополнительная ёмкость хранения энергии приёмника");
        
        add("configuration." + MOD_ID + ".acceptorRate", "Скорость приёмника");
        add("configuration." + MOD_ID + ".acceptorRate.tooltip", "Дополнительная скорость ввода/вывода энергии приёмника");
        
        add("configuration." + MOD_ID + ".processingEfficiency", "Эффективность обработки");
        add("configuration." + MOD_ID + ".processingEfficiency.tooltip", "Множитель эффективности операций обработки");
        
        add("configuration." + MOD_ID + ".processingChambers", "Камеры обработки");
        add("configuration." + MOD_ID + ".processingChambers.tooltip", "Количество камер обработки для параллельных операций");

    }

}
