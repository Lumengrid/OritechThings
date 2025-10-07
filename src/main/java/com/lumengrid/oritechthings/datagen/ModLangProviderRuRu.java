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

        // drone cross-dimensional messages
        add("message." + MOD_ID + ".drone.server_unavailable", "§c⚠ Сервер недоступен для межмерного переноса ⚠");
        add("message." + MOD_ID + ".drone.dimension_unavailable", "§c⚠ Целевое измерение недоступно ⚠");
        add("message." + MOD_ID + ".drone.cross_dimensional_target_set", "Межмерный целевой порт установлен.\nДрон будет доставлять через измерения, когда инвентарь не пуст.");
        add("message." + MOD_ID + ".drone.addon_required", "§c⚠ Требуется межмерное дополнение для межмерных переносов ⚠");
        
        // Advanced Target Designator tooltips
        add("tooltip." + MOD_ID + ".advanced_target_designator.usage", "§7Совместимые Машины:");
        add("tooltip." + MOD_ID + ".advanced_target_designator.speed_sensor", "§9• Ускоритель Частиц Датчик Скорости");
        add("tooltip." + MOD_ID + ".advanced_target_designator.drone_port", "§9• Порт Дронов");
        add("tooltip." + MOD_ID + ".advanced_target_designator.laser_arm", "§9• Эндерический Лазер");
        add("tooltip." + MOD_ID + ".advanced_target_designator.cross_dimensional", "§6⚡ Только Порты Дронов поддерживают межмерные переносы");
        add("tooltip." + MOD_ID + ".addon_block_cross_dimensional", "Прикрепите к порту дрона для включения межмерных переносов.\nПозволяет дронам путешествовать между измерениями.");
        
        // Accelerator Motor tooltips
        add("tooltip." + MOD_ID + ".accelerator_motor.addon_info", "§6⚡ Совместим с дополнениями");
        add("tooltip." + MOD_ID + ".accelerator_motor.addon_placement", "§7Разместите дополнения скорости или эффективности снизу для улучшения производительности");
    }

}
