package com.lumengrid.oritechthings.datagen;

import com.lumengrid.oritechthings.block.ModBlocks;
import com.lumengrid.oritechthings.entity.ModEntities;
import com.lumengrid.oritechthings.item.ModItems;
import com.lumengrid.oritechthings.util.DataGenUtil;

import static com.lumengrid.oritechthings.main.OritechThings.MOD_ID;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import rearth.oritech.init.ToolsContent;

public class ModLangProviderPtBr extends LanguageProvider {

    public ModLangProviderPtBr(PackOutput output) {
        super(output, MOD_ID, "pt_br");
    }

    @Override
    protected void addTranslations() {

        //misc
        add("itemGroup." + MOD_ID, "Oritech Things");
        add("message."+ToolsContent.EXO_JETPACK.getDescriptionId().replace("item.oritech.", "")+".energy_low", "§c⚠ Exo Jetpack - Energia Baixa ⚠");
        add("tooltip." + MOD_ID + ".tiered_addons.chambers_desc", "Câmaras Adicionais ");
        add("tooltip."+MOD_ID+".tier_addon", "Nível ");
        add("tooltip."+MOD_ID+".state.on", "LIGADO");
        add("tooltip."+MOD_ID+".state.off", "DESLIGADO");
        
        // Tag translations
        add("tag."+MOD_ID+".addons", "Oritech Things Addons");
        add("tag."+MOD_ID+".tiered_addon_processing", "Addons de Processamento");
        add("tag."+MOD_ID+".tiered_addon_acceptor", "Addons de Aceitação");
        add("tag."+MOD_ID+".tiered_addon_capacitor", "Addons de Capacitor");
        add("tag."+MOD_ID+".tiered_addon_speed", "Addons de Velocidade");
        add("tag."+MOD_ID+".tiered_addon_efficient_speed", "Addons de Velocidade Eficiente");
        add("tag."+MOD_ID+".tiered_addon_efficiency", "Addons de Eficiência");
        add("tag."+MOD_ID+".particle_accelerator", "Componentes do Acelerador de Partículas");
        

        // Manual block translations
        addBlock(ModBlocks.INFESTED_AMETHYST_BLOCK, "Bloco de Ametista Infestado");
        addBlock(ModBlocks.ACCELERATOR_SPEED_SENSOR, "Sensor de Velocidade do Acelerador de Partículas");
        addBlock(ModBlocks.ACCELERATOR_MAGNETIC_FIELD, "Campo Magnético do Acelerador");
        
        // Block addons - Manual translations
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_2, "Addon de Processamento Nível 2");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_3, "Addon de Processamento Nível 3");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_4, "Addon de Processamento Nível 4");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_5, "Addon de Processamento Nível 5");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_6, "Addon de Processamento Nível 6");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_7, "Addon de Processamento Nível 7");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_8, "Addon de Processamento Nível 8");
        addBlock(ModBlocks.ADDON_BLOCK_PROCESSING_TIER_9, "Addon de Processamento Nível 9");
        
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_2, "Addon de Aceitação Nível 2");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_3, "Addon de Aceitação Nível 3");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_4, "Addon de Aceitação Nível 4");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_5, "Addon de Aceitação Nível 5");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_6, "Addon de Aceitação Nível 6");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_7, "Addon de Aceitação Nível 7");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_8, "Addon de Aceitação Nível 8");
        addBlock(ModBlocks.ADDON_BLOCK_ACCEPTOR_TIER_9, "Addon de Aceitação Nível 9");
        
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_2, "Addon de Capacitor Nível 2");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_3, "Addon de Capacitor Nível 3");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_4, "Addon de Capacitor Nível 4");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_5, "Addon de Capacitor Nível 5");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_6, "Addon de Capacitor Nível 6");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_7, "Addon de Capacitor Nível 7");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_8, "Addon de Capacitor Nível 8");
        addBlock(ModBlocks.ADDON_BLOCK_CAPACITOR_TIER_9, "Addon de Capacitor Nível 9");
        
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_2, "Addon de Velocidade Nível 2");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_3, "Addon de Velocidade Nível 3");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_4, "Addon de Velocidade Nível 4");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_5, "Addon de Velocidade Nível 5");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_6, "Addon de Velocidade Nível 6");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_7, "Addon de Velocidade Nível 7");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_8, "Addon de Velocidade Nível 8");
        addBlock(ModBlocks.ADDON_BLOCK_SPEED_TIER_9, "Addon de Velocidade Nível 9");
        
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_2, "Addon de Velocidade Eficiente Nível 2");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_3, "Addon de Velocidade Eficiente Nível 3");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_4, "Addon de Velocidade Eficiente Nível 4");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_5, "Addon de Velocidade Eficiente Nível 5");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_6, "Addon de Velocidade Eficiente Nível 6");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_7, "Addon de Velocidade Eficiente Nível 7");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_8, "Addon de Velocidade Eficiente Nível 8");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENT_SPEED_TIER_9, "Addon de Velocidade Eficiente Nível 9");
        
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_2, "Addon de Eficiência Nível 2");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_3, "Addon de Eficiência Nível 3");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_4, "Addon de Eficiência Nível 4");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_5, "Addon de Eficiência Nível 5");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_6, "Addon de Eficiência Nível 6");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_7, "Addon de Eficiência Nível 7");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_8, "Addon de Eficiência Nível 8");
        addBlock(ModBlocks.ADDON_BLOCK_EFFICIENCY_TIER_9, "Addon de Eficiência Nível 9");
        
        addBlock(ModBlocks.ADDON_BLOCK_CROSS_DIMENSIONAL, "Transdimensional");
        
        // Manual item translations
        addItem(ModItems.ADVANCED_TARGET_DESIGNATOR, "Designador de Alvo Avançado");
        addItem(ModItems.FRAME_PLACER, "Colocador de Estrutura");
        addItem(ModItems.AMETHYST_FISH_SPAWN_EGG, "Ovo de Geração de Peixe de Ametista");
        
        // Manual entity translations
        addEntityType(ModEntities.AMETHYST_FISH, "Peixe de Ametista");

        //particle accelerator speed sensor

        var AspeedSensor = MOD_ID + "." + DataGenUtil.getName(ModBlocks.ACCELERATOR_SPEED_SENSOR);
        
        add("gui." + AspeedSensor + ".title", "Sensor de Velocidade");
        add("gui." + AspeedSensor + ".controller_not_set", "NÃO DEFINIDO !");
        add("gui." + AspeedSensor + ".controller", "Acelerador de Partículas ");
        add("block." + AspeedSensor + ".controller_set", "Acelerador de Partículas Alvo definido para ");
        add("block." + AspeedSensor + ".invalid_controller", "⚠ Acelerador de Partículas Alvo Inválido ⚠");
        add("block." + AspeedSensor + ".invalid_controller.to_far",
                "⚠ Acelerador de Partículas Alvo muito longe - Distância máxima de 128 blocos ⚠");
        add("gui."+AspeedSensor+".speed_input","Entrada de Velocidade" );
        
        // Speed Sensor Block tooltips
        add("tooltip." + AspeedSensor, "Sensor de Velocidade do Acelerador de Partículas");
        add("tooltip." + AspeedSensor + "_desc", "Monitora a velocidade das partículas no acelerador e emite sinal de redstone com base no limite de velocidade");
        
        // Advanced Target Designator usage for Speed Sensor
        add("tooltip." + AspeedSensor + ".target_designator_usage", "§7Uso do Designador de Alvo Avançado:");
        add("tooltip." + AspeedSensor + ".target_designator_step1", "§91. Clique com botão direito em um acelerador de partículas com o Designador de Alvo Avançado para salvar a posição");
        add("tooltip." + AspeedSensor + ".target_designator_step2", "§92. Coloque o Sensor de Velocidade no local desejado");
        add("tooltip." + AspeedSensor + ".target_designator_step3", "§93. Clique com botão direito no sensor de velocidade para vinculá-lo ao acelerador de partículas");
        add("tooltip." + AspeedSensor + ".target_designator_benefit", "§6⚡ Permite monitoramento remoto e vinculação automática a aceleradores");

        //particle accelerator magnetic field

        var AMagneticField = MOD_ID + "." + DataGenUtil.getName(ModBlocks.ACCELERATOR_MAGNETIC_FIELD);
        
        add("tooltip." + AMagneticField, "Campo Magnético do Acelerador de Partículas");
        add("tooltip." + AMagneticField + "_desc", "Auxilia partículas em curvas fechadas fornecendo orientação magnética");
        add("tooltip." + AMagneticField + "_limitation", "§c⚠ Apenas um campo magnético é verificado por acelerador de partículas");
        add("tooltip." + AMagneticField + "_addon_info", "§6⚡ Addons Compatíveis: Eficiência, Capacitor");
        add("tooltip." + AMagneticField + "_disabled", "§c⚠ Campos Magnéticos estão desabilitados na configuração do servidor");
        add("tooltip." + MOD_ID + ".item_extra_info", "Segure CTRL para mais informações");
        
        // Advanced Target Designator usage for Magnetic Field Block
        add("tooltip." + AMagneticField + ".target_designator_usage", "§7Uso do Designador de Alvo Avançado:");
        add("tooltip." + AMagneticField + ".target_designator_step1", "§91. Clique com botão direito em um acelerador de partículas com o Designador de Alvo Avançado para salvar a posição");
        add("tooltip." + AMagneticField + ".target_designator_step2", "§92. Coloque o Bloco de Campo Magnético dentro do anel do acelerador de partículas");
        add("tooltip." + AMagneticField + ".target_designator_step3", "§93. Clique com botão direito no campo magnético para vincular o acelerador de partículas ao bloco de campo magnético");
        add("tooltip." + AMagneticField + ".target_designator_benefit", "§6⚡ Permite colocação precisa e vinculação automática a aceleradores");
        
        // Magnetic field block messages
        add("block.oritechthings.accelerator_magnetic_field.invalid_controller", "Controlador de acelerador inválido! Deve mirar em um controlador de acelerador de partículas.");
        add("block.oritechthings.accelerator_magnetic_field.outside_area", "O campo magnético deve ser colocado dentro da área do acelerador de partículas no mesmo nível Y!");
        add("block.oritechthings.accelerator_magnetic_field.error", "Falha ao vincular o campo magnético ao controlador do acelerador.");
        add("block.oritechthings.accelerator_magnetic_field.controller_set", "Campo magnético vinculado ao controlador do acelerador em ");
        

        // frame placer

        var FramePlacer = MOD_ID + "." + DataGenUtil.getName(ModItems.FRAME_PLACER);

        add("message." + FramePlacer + ".missing_frame", "⚠ Colocador de Estrutura - Estrutura Ausente ⚠");
        add("tooltip." + FramePlacer,
                "Clique com Shift e Botão Direito em uma Máquina compatível com Estrutura \ncomo o Destruidor para construir automaticamente uma estrutura de Frame");

        add("message." + FramePlacer + ".wrong_machine", "§c⚠ Não é uma Máquina compatível com Estrutura ⚠");
        add("message." + FramePlacer + ".not_assembled", "§c⚠ Máquina ainda não Montada ⚠");

        add("message."+FramePlacer+".offset", "Deslocamento ");
        add("message."+FramePlacer+".abort", "Abortar");
        add("message."+FramePlacer+".confirm", "Confirmar");

        add("message."+FramePlacer+".blocks_required", "Blocos de Estrutura Necessários: ");
        add("message."+FramePlacer+".frame_size", "Definir Dimensões para a Estrutura");

        //advanced target designator

        var TargetDesignator = MOD_ID + "." + DataGenUtil.getName(ModItems.ADVANCED_TARGET_DESIGNATOR);

        add("message." + TargetDesignator + ".position_invalid", "⚠ Posição inválida ⚠");
        add("message." + TargetDesignator + ".different_dimension", "⚠ Dimensão Diferente ⚠");
        add("tooltip." + TargetDesignator + ".dimension", "Dimensão: ");
        add("message." + TargetDesignator + ".accelerator_saved", "Acelerador de Partículas salvo em ");

        add("tooltip." + MOD_ID + ".accelerator_target_designator",
                "Usável para armazenar a posição de um Acelerador de Partículas");

        // drone cross-dimensional messages
        add("message." + MOD_ID + ".drone.server_unavailable", "§c⚠ Servidor indisponível para transferência transdimensional ⚠");
        add("message." + MOD_ID + ".drone.dimension_unavailable", "§c⚠ Dimensão alvo não está disponível ⚠");
        add("message." + MOD_ID + ".drone.cross_dimensional_target_set", "Porta de alvo transdimensional definida.\nO drone entregará entre dimensões sempre que o inventário não estiver vazio.");
        add("message." + MOD_ID + ".drone.addon_required", "§c⚠ Addon Transdimensional necessário para transferências interdimensionais ⚠");
        
        // Advanced Target Designator tooltips
        add("tooltip." + MOD_ID + ".advanced_target_designator.usage", "§7Máquinas Compatíveis:");
        add("tooltip." + MOD_ID + ".advanced_target_designator.speed_sensor", "§9• Sensor de Velocidade do Acelerador de Partículas");
        add("tooltip." + MOD_ID + ".advanced_target_designator.magnetic_field", "§9• Campo Magnético do Acelerador de Partículas");
        add("tooltip." + MOD_ID + ".advanced_target_designator.drone_port", "§9• Porta de Drone");
        add("tooltip." + MOD_ID + ".advanced_target_designator.laser_arm", "§9• Laser Endérico");
        add("tooltip." + MOD_ID + ".advanced_target_designator.cross_dimensional", "§6⚡ Apenas Portas de Drone suportam transferências transdimensionais");
        add("tooltip." + MOD_ID + ".addon_block_cross_dimensional", "Anexe a uma Porta de Drone para habilitar transferências transdimensionais.\nPermite que drones viajem entre dimensões.");
        add("tooltip." + MOD_ID + ".addon_block_cross_dimensional_disabled", "§c⚠ Addons transdimensionais estão desabilitados na configuração do servidor");
        
        // Accelerator Motor tooltips
        add("tooltip." + MOD_ID + ".accelerator_motor.addon_info", "§6⚡ Compatível com Addon");
        add("tooltip." + MOD_ID + ".accelerator_motor.addon_placement", "§7Coloque addons de Velocidade ou Eficiência embaixo para melhorar o desempenho");
    }

}

