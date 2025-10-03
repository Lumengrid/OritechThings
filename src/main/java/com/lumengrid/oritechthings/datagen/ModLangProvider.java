package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.util.DataGenUtil;

import static com.lumengrid.oritechthings.main.OritechThings.MOD_ID;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import rearth.oritech.init.ToolsContent;

public class ModLangProvider extends LanguageProvider {

    public ModLangProvider(PackOutput output) {
        super(output, MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        //misc
        add("itemGroup." + MOD_ID, "Oritech Things");
        add("message."+ToolsContent.EXO_JETPACK.getDescriptionId().replace("item.oritech.", "")+".energy_low", "§c⚠ Exo Jetpack - Energy Low ⚠");
        add("tooltip." + MOD_ID + ".tiered_addons.chambers_desc", "Additional Chambers ");
        add("tooltip."+MOD_ID+".tier_addon", "Tier ");
        add("tooltip."+MOD_ID+".state.on", "ON");
        add("tooltip."+MOD_ID+".state.off", "OFF");

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
        
        add("gui." + AspeedSensor + ".title", "Speed Sensor");
        add("gui." + AspeedSensor + ".controller_not_set", "NOT SET !");
        add("gui." + AspeedSensor + ".controller", "Particle Accelerator ");
        add("block." + AspeedSensor + ".controller_set", "Target Particle Accelerator set to ");
        add("block." + AspeedSensor + ".invalid_controller", "⚠ Invalid Target Particle Accelerator ⚠");
        add("block." + AspeedSensor + ".invalid_controller.to_far",
                "⚠ Target Particle Accelerator too far - Max distance 128 blocks ⚠");
        add("gui."+AspeedSensor+".speed_input","Speed Input" );

        // frame placer

        var FramePlacer = MOD_ID + "." + DataGenUtil.getName(ModItems.FRAME_PLACER);

        add("message." + FramePlacer + ".missing_frame", "⚠ Frame Placer - Missing Frame ⚠");
        add("tooltip." + FramePlacer,
                "Shift Right Click on an Frame compatible Machine \nlike the Destroyer to auto build a Frame structure");

        add("message." + FramePlacer + ".wrong_machine", "§c⚠ Not a Frame compatible Machine ⚠");
        add("message." + FramePlacer + ".not_assembled", "§c⚠ Machine not Assembled yet ⚠");

        add("message."+FramePlacer+".offset", "Offset ");
        add("message."+FramePlacer+".abort", "Abort");
        add("message."+FramePlacer+".confirm", "Confirm");

        add("message."+FramePlacer+".blocks_required", "Frame Blocks Required: ");
        add("message."+FramePlacer+".frame_size", "Set Dimensions for the Frame");

        //advanced target designator

        var TargetDesignator = MOD_ID + "." + DataGenUtil.getName(ModItems.ADVANCED_TARGET_DESIGNATOR);

        add("message." + TargetDesignator + ".position_invalid", "⚠ Invalid position ⚠");
        add("message." + TargetDesignator + ".different_dimension", "⚠ Different Dimension ⚠");

        add("tooltip." + MOD_ID + ".accelerator_target_designator",
                "Usable to store the position of a Particle Accelerator");

        // drone cross-dimensional messages
        add("message." + MOD_ID + ".drone.server_unavailable", "§c⚠ Server unavailable for cross-dimensional transfer ⚠");
        add("message." + MOD_ID + ".drone.dimension_unavailable", "§c⚠ Target dimension is not available ⚠");
        add("message." + MOD_ID + ".drone.cross_dimensional_target_set", "Cross-dimensional target port set.\nDrone will deliver across dimensions whenever the inventory is not empty.");
        add("message." + MOD_ID + ".drone.addon_required", "§c⚠ Cross-Dimensional Addon required for inter-dimensional transfers ⚠");
        
        // Advanced Target Designator tooltips
        add("tooltip." + MOD_ID + ".advanced_target_designator.usage", "§7Compatible Machines:");
        add("tooltip." + MOD_ID + ".advanced_target_designator.speed_sensor", "§9• Particle Accelerator Speed Sensor");
        add("tooltip." + MOD_ID + ".advanced_target_designator.drone_port", "§9• Drone Port");
        add("tooltip." + MOD_ID + ".advanced_target_designator.laser_arm", "§9• Enderic Laser");
        add("tooltip." + MOD_ID + ".advanced_target_designator.cross_dimensional", "§6⚡ Only Drone Ports support cross-dimensional transfers");
        add("tooltip." + MOD_ID + ".addon_block_cross_dimensional", "Attach to a Drone Port to enable cross-dimensional transfers.\nAllows drones to travel between dimensions.");
    }

}
