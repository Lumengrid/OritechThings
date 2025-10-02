package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.util.DataGenUtil;

import static com.lumengrid.oritechthings.main.OritechThings.MOD_ID;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import rearth.oritech.init.ToolsContent;

public class ModLangProviderZhCn extends LanguageProvider {

    public ModLangProviderZhCn(PackOutput output) {
        super(output, MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {

        //misc
        add("itemGroup." + MOD_ID, "Oritech Things");
        add("message."+ToolsContent.EXO_JETPACK.getDescriptionId().replace("item.oritech.", "")+".energy_low", "§c⚠ 外骨骼喷气背包 - 能量不足 ⚠");
        add("tooltip." + MOD_ID + ".tiered_addons.chambers_desc", "额外舱室 ");
        add("tooltip."+MOD_ID+".tier_addon", "等级 ");
        add("tooltip."+MOD_ID+".state.on", "开启");
        add("tooltip."+MOD_ID+".state.off", "关闭");
        
        

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
        
        add("gui." + AspeedSensor + ".title", "速度传感器");
        add("gui." + AspeedSensor + ".controller_not_set", "未设置！");
        add("gui." + AspeedSensor + ".controller", "粒子加速器 ");
        add("block." + AspeedSensor + ".controller_set", "目标粒子加速器已设置为 ");
        add("block." + AspeedSensor + ".invalid_controller", "⚠ 无效的目标粒子加速器 ⚠");
        add("block." + AspeedSensor + ".invalid_controller.to_far",
                "⚠ 目标粒子加速器过远 - 最大距离 128 格 ⚠");
        add("gui."+AspeedSensor+".speed_input","速度输入" );

        // frame placer

        var FramePlacer = MOD_ID + "." + DataGenUtil.getName(ModItems.FRAME_PLACER);

        add("message." + FramePlacer + ".missing_frame", "⚠ 框架放置器 - 缺少框架 ⚠");
        add("tooltip." + FramePlacer,
                "Shift + 右键点击兼容框架的机器（如破坏者）以自动构建框架结构");

        add("message." + FramePlacer + ".wrong_machine", "§c⚠ 不兼容框架的机器 ⚠");
        add("message." + FramePlacer + ".not_assembled", "§c⚠ 机器尚未组装 ⚠");

        add("message."+FramePlacer+".offset", "偏移 ");
        add("message."+FramePlacer+".abort", "取消");
        add("message."+FramePlacer+".confirm", "确认");

        add("message."+FramePlacer+".blocks_required", "所需框架方块：");
        add("message."+FramePlacer+".frame_size", "设置框架尺寸");

        //advanced target designator

        var TargetDesignator = MOD_ID + "." + DataGenUtil.getName(ModItems.ADVANCED_TARGET_DESIGNATOR);

        add("message." + TargetDesignator + ".position_invalid", "⚠ 无效位置 ⚠");
        add("message." + TargetDesignator + ".different_dimension", "⚠ 不同维度 ⚠");

        add("tooltip." + MOD_ID + ".accelerator_target_designator",
                "用于存储粒子加速器的位置");

        // drone cross-dimensional messages
        add("message." + MOD_ID + ".drone.server_unavailable", "§c⚠ 服务器不可用于跨维度传输 ⚠");
        add("message." + MOD_ID + ".drone.dimension_unavailable", "§c⚠ 目标维度不可用 ⚠");
        add("message." + MOD_ID + ".drone.cross_dimensional_target_set", "跨维度目标端口已设置。\n当库存不为空时，无人机将跨维度传送物品。");
    }

}
