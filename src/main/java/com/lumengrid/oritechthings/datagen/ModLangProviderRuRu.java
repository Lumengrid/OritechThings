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
        
        // Tag translations
        add("tag."+MOD_ID+".addons", "Oritech Things Дополнения");
        add("tag."+MOD_ID+".tiered_addon_processing", "Дополнения Обработки");
        add("tag."+MOD_ID+".tiered_addon_acceptor", "Дополнения Приемника");
        add("tag."+MOD_ID+".tiered_addon_capacitor", "Дополнения Конденсатора");
        add("tag."+MOD_ID+".tiered_addon_speed", "Дополнения Скорости");
        add("tag."+MOD_ID+".tiered_addon_efficient_speed", "Дополнения Эффективной Скорости");
        add("tag."+MOD_ID+".tiered_addon_efficiency", "Дополнения Эффективности");
        add("tag."+MOD_ID+".particle_accelerator", "Компоненты Ускорителя Частиц");

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

        //particle accelerator magnetic field

        var AMagneticField = MOD_ID + "." + DataGenUtil.getName(ModBlocks.ACCELERATOR_MAGNETIC_FIELD);
        
        add("tooltip." + AMagneticField, "Магнитное Поле Ускорителя Частиц");
        add("tooltip." + AMagneticField + "_desc", "Помогает частицам в крутых поворотах, обеспечивая магнитное наведение");
        add("tooltip." + AMagneticField + "_limitation", "§c⚠ Только одно магнитное поле проверяется на ускоритель частиц");
        add("tooltip." + AMagneticField + "_addon_info", "§6⚡ Совместимые Дополнения: Эффективность, Конденсатор");
        add("tooltip." + AMagneticField + "_disabled", "§c⚠ Магнитные поля отключены в конфигурации сервера");
        add("tooltip." + MOD_ID + ".item_extra_info", "Удерживайте CTRL для получения дополнительной информации");
        
        // Advanced Target Designator usage for Magnetic Field Block
        add("tooltip." + AMagneticField + ".target_designator_usage", "§7Использование Продвинутого Целеуказателя:");
        add("tooltip." + AMagneticField + ".target_designator_step1", "§91. ПКМ по ускорителю частиц с Продвинутым Целеуказателем для сохранения позиции");
        add("tooltip." + AMagneticField + ".target_designator_step2", "§92. Разместите Блок Магнитного Поля внутри кольца ускорителя частиц");
        add("tooltip." + AMagneticField + ".target_designator_step3", "§93. ПКМ по магнитному полю для связи ускорителя частиц с блоком магнитного поля");
        add("tooltip." + AMagneticField + ".target_designator_benefit", "§6⚡ Позволяет точное размещение и автоматическую связь с ускорителями");
        
        // Magnetic field block messages
        add("block.oritechthings.accelerator_magnetic_field.invalid_controller", "Неверный контроллер ускорителя! Должен указывать на контроллер ускорителя частиц.");
        add("block.oritechthings.accelerator_magnetic_field.outside_area", "Магнитное поле должно быть размещено в области ускорителя частиц на том же уровне Y!");
        add("block.oritechthings.accelerator_magnetic_field.error", "Не удалось связать магнитное поле с контроллером ускорителя.");
        add("block.oritechthings.accelerator_magnetic_field.controller_set", "Магнитное поле связано с контроллером ускорителя в ");
        

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
        add("tooltip." + TargetDesignator + ".dimension", "Размерность: ");
        add("message." + TargetDesignator + ".accelerator_saved", "Ускоритель частиц сохранён в ");

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
        add("tooltip." + MOD_ID + ".advanced_target_designator.magnetic_field", "§9• Ускоритель Частиц Магнитное Поле");
        add("tooltip." + MOD_ID + ".advanced_target_designator.drone_port", "§9• Порт Дронов");
        add("tooltip." + MOD_ID + ".advanced_target_designator.laser_arm", "§9• Эндерический Лазер");
        add("tooltip." + MOD_ID + ".advanced_target_designator.cross_dimensional", "§6⚡ Только Порты Дронов поддерживают межмерные переносы");
        add("tooltip." + MOD_ID + ".addon_block_cross_dimensional", "Прикрепите к порту дрона для включения межмерных переносов.\nПозволяет дронам путешествовать между измерениями.");
        add("tooltip." + MOD_ID + ".addon_block_cross_dimensional_disabled", "§c⚠ Межмерные дополнения отключены в конфигурации сервера");
        
        // Accelerator Motor tooltips
        add("tooltip." + MOD_ID + ".accelerator_motor.addon_info", "§6⚡ Совместим с дополнениями");
        add("tooltip." + MOD_ID + ".accelerator_motor.addon_placement", "§7Разместите дополнения скорости или эффективности снизу для улучшения производительности");
    }

}
