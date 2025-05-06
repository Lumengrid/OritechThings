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

        // (?) deprecated (?)
        add("tooltip." + MOD_ID + ".accelerator_target_designator",
                "Usable to store the position of a Particle Accelerator");

    }

}
