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
        
        // Tag translations
        add("tag."+MOD_ID+".addons", "Oritech Things 插件");
        add("tag."+MOD_ID+".tiered_addon_processing", "处理插件");
        add("tag."+MOD_ID+".tiered_addon_acceptor", "接收器插件");
        add("tag."+MOD_ID+".tiered_addon_capacitor", "电容器插件");
        add("tag."+MOD_ID+".tiered_addon_speed", "速度插件");
        add("tag."+MOD_ID+".tiered_addon_efficient_speed", "高效速度插件");
        add("tag."+MOD_ID+".tiered_addon_efficiency", "效率插件");
        add("tag."+MOD_ID+".particle_accelerator", "粒子加速器组件");

        // Manual block translations
        addBlock(ModBlocks.INFESTED_AMETHYST_BLOCK, "被虫蚀的紫水晶块");
        addBlock(ModBlocks.ACCELERATOR_SPEED_SENSOR, "粒子加速器速度传感器");
        addBlock(ModBlocks.ACCELERATOR_MAGNETIC_FIELD, "加速器磁场");
        
        // Block addons - Manual translations
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_2, "处理插件 等级2");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_3, "处理插件 等级3");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_4, "处理插件 等级4");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_5, "处理插件 等级5");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_6, "处理插件 等级6");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_7, "处理插件 等级7");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_8, "处理插件 等级8");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_9, "处理插件 等级9");
        
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_2, "接收器插件 等级2");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_3, "接收器插件 等级3");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_4, "接收器插件 等级4");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_5, "接收器插件 等级5");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_6, "接收器插件 等级6");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_7, "接收器插件 等级7");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_8, "接收器插件 等级8");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_9, "接收器插件 等级9");
        
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_2, "电容器插件 等级2");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_3, "电容器插件 等级3");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_4, "电容器插件 等级4");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_5, "电容器插件 等级5");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_6, "电容器插件 等级6");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_7, "电容器插件 等级7");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_8, "电容器插件 等级8");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_9, "电容器插件 等级9");
        
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_2, "速度插件 等级2");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_3, "速度插件 等级3");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_4, "速度插件 等级4");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_5, "速度插件 等级5");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_6, "速度插件 等级6");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_7, "速度插件 等级7");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_8, "速度插件 等级8");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_9, "速度插件 等级9");
        
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_2, "高效速度插件 等级2");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_3, "高效速度插件 等级3");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_4, "高效速度插件 等级4");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_5, "高效速度插件 等级5");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_6, "高效速度插件 等级6");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_7, "高效速度插件 等级7");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_8, "高效速度插件 等级8");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_9, "高效速度插件 等级9");
        
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_2, "效率插件 等级2");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_3, "效率插件 等级3");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_4, "效率插件 等级4");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_5, "效率插件 等级5");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_6, "效率插件 等级6");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_7, "效率插件 等级7");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_8, "效率插件 等级8");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_9, "效率插件 等级9");
        
        addBlock(ModBlocks.ADDON_BLOCK_CROSS_DIMENSIONAL, "跨维度");
        
        // Manual item translations
        addItem(ModItems.ADVANCED_TARGET_DESIGNATOR, "高级目标指示器");
        addItem(ModItems.FRAME_PLACER, "框架放置器");
        addItem(ModItems.AMETHYST_FISH_SPAWN_EGG, "紫水晶鱼刷怪蛋");
        
        // Manual entity translations
        addEntityType(ModEntities.AMETHYST_FISH, "紫水晶鱼");

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
        add("gui." + AspeedSensor + ".auto", "自动");
        add("gui." + AspeedSensor + ".manual", "手动");
        
        // Speed Sensor Block tooltips
        add("tooltip." + AspeedSensor, "粒子加速器速度传感器");
        add("tooltip." + AspeedSensor + "_desc", "监测加速器中的粒子速度，并根据速度阈值输出红石信号");
        
        // Advanced Target Designator usage for Speed Sensor
        add("tooltip." + AspeedSensor + ".target_designator_usage", "§7高级目标指示器使用方法:");
        add("tooltip." + AspeedSensor + ".target_designator_step1", "§91. 用高级目标指示器右键点击粒子加速器以保存位置");
        add("tooltip." + AspeedSensor + ".target_designator_step2", "§92. 在所需位置放置速度传感器");
        add("tooltip." + AspeedSensor + ".target_designator_step3", "§93. 右键点击速度传感器以将其链接到粒子加速器");
        add("tooltip." + AspeedSensor + ".target_designator_benefit", "§6⚡ 允许远程监控和自动链接到加速器");

        //particle accelerator magnetic field

        var AMagneticField = MOD_ID + "." + DataGenUtil.getName(ModBlocks.ACCELERATOR_MAGNETIC_FIELD);
        
        add("tooltip." + AMagneticField, "粒子加速器磁场");
        add("tooltip." + AMagneticField + "_desc", "通过提供磁引导协助粒子进行急转弯");
        add("tooltip." + AMagneticField + "_limitation", "§c⚠ 每个粒子加速器只检查一个磁场");
        add("tooltip." + AMagneticField + "_addon_info", "§6⚡ 兼容插件: 效率, 电容");
        add("tooltip." + AMagneticField + "_disabled", "§c⚠ 服务器配置中已禁用磁场");
        add("tooltip." + MOD_ID + ".item_extra_info", "按住 CTRL 获取更多信息");
        
        // Advanced Target Designator usage for Magnetic Field Block
        add("tooltip." + AMagneticField + ".target_designator_usage", "§7高级目标指示器使用方法:");
        add("tooltip." + AMagneticField + ".target_designator_step1", "§91. 用高级目标指示器右键点击粒子加速器以保存位置");
        add("tooltip." + AMagneticField + ".target_designator_step2", "§92. 在粒子加速器环内放置磁场稳定器");
        add("tooltip." + AMagneticField + ".target_designator_step3", "§93. 右键点击磁场稳定器以将粒子加速器与磁场稳定器链接");
        add("tooltip." + AMagneticField + ".target_designator_benefit", "§6⚡ 允许精确定位和自动链接到加速器");
        
        // Magnetic field block messages
        add("block.oritechthings.accelerator_magnetic_field.invalid_controller", "无效的加速器控制器！必须指向粒子加速器控制器。");
        add("block.oritechthings.accelerator_magnetic_field.outside_area", "磁场稳定器必须放置在粒子加速器区域内且处于同一Y高度！");
        add("block.oritechthings.accelerator_magnetic_field.error", "无法将磁场稳定器连接到加速器控制器。");
        add("block.oritechthings.accelerator_magnetic_field.controller_set", "磁场已连接到加速器控制器位置 ");
        

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
        add("tooltip." + TargetDesignator + ".dimension", "维度: ");
        add("message." + TargetDesignator + ".accelerator_saved", "粒子加速器已保存到 ");

        add("tooltip." + MOD_ID + ".accelerator_target_designator",
                "用于存储粒子加速器的位置");

        // drone cross-dimensional messages
        add("message." + MOD_ID + ".drone.server_unavailable", "§c⚠ 服务器不可用于跨维度传输 ⚠");
        add("message." + MOD_ID + ".drone.dimension_unavailable", "§c⚠ 目标维度不可用 ⚠");
        add("message." + MOD_ID + ".drone.cross_dimensional_target_set", "跨维度目标端口已设置。\n当库存不为空时，无人机将跨维度传送物品。");
        add("message." + MOD_ID + ".drone.addon_required", "§c⚠ 跨维度传输需要跨维度插件 ⚠");
        
        // Advanced Target Designator tooltips
        add("tooltip." + MOD_ID + ".advanced_target_designator.usage", "§7兼容的机器:");
        add("tooltip." + MOD_ID + ".advanced_target_designator.speed_sensor", "§9• 粒子加速器 速度传感器");
        add("tooltip." + MOD_ID + ".advanced_target_designator.magnetic_field", "§9• 粒子加速器 磁场");
        add("tooltip." + MOD_ID + ".advanced_target_designator.drone_port", "§9• 无人机端口");
        add("tooltip." + MOD_ID + ".advanced_target_designator.laser_arm", "§9• 末影激光器");
        add("tooltip." + MOD_ID + ".advanced_target_designator.cross_dimensional", "§6⚡ 只有无人机端口支持跨维度传输");
        add("tooltip." + MOD_ID + ".addon_block_cross_dimensional", "附加到无人机端口以启用跨维度传输。\n允许无人机在维度之间旅行。");
        add("tooltip." + MOD_ID + ".addon_block_cross_dimensional_disabled", "§c⚠ 服务器配置中已禁用跨维度插件");
        
        // Accelerator Motor tooltips
        add("tooltip." + MOD_ID + ".accelerator_motor.addon_info", "§6⚡ 支持插件");
        add("tooltip." + MOD_ID + ".accelerator_motor.addon_placement", "§7在下方放置速度或效率插件以提升性能");
    }

}
