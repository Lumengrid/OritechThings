package com.lumengrid.oritechthings.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import net.neoforged.fml.loading.FMLPaths;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {
    public static final String CONFIG_FILE = "oritech-things.json5";
    private static ConfigLoader INSTANCE = new ConfigLoader();

    private ConfigLoader() {}

    public static ConfigLoader getInstance() {
        return INSTANCE != null ? INSTANCE : new ConfigLoader();
    }

    @Expose
    public List<AddonInfo> addonSettings = new ArrayList<>(
            List.of(
                    new AddonInfo(0f, 1.40f,0.60f, 4_000_000, 2_000, 1_000_000, 4000, 2f, 2 ),
                    new AddonInfo(-0.5f, 1.60f, 0.40f, 6_000_000, 3_000, 1_500_000, 6000 , 2.5f, 3 ),
                    new AddonInfo(-1f, 1.80f, 0.20f, 8_000_000, 4_000, 2_000_000, 8000 , 3f, 4 ),
                    new AddonInfo(-1.5f, 2.00f, 0f,  10_000_000, 5_000, 2_500_000, 10000 , 3.5f, 5 ),
                    new AddonInfo(-2f, 2.20f, -0.2f, 12_000_000, 6_000, 3_000_000, 12000 , 4f, 6 ),
                    new AddonInfo(-2.5f, 2.40f,-0.40f,  14_000_000, 7_000, 3_500_000, 14000 , 4.5f, 7 ),
                    new AddonInfo(-3f, 2.60f, -0.6f, 16_000_000, 8_000, 4_000_000, 14000 , 5f, 8 ),
                    new AddonInfo(-3.5f, 2.80f, -0.8f, 18_000_000, 9_000, 4_500_000, 18000, 5.5f, 9 )
            )
    );

    @Expose
    public ExoJetPack exoJetPackSettings = new ExoJetPack(true, 10000);

    @Expose
    public DimensionalDrone dimensionalDroneSettings = new DimensionalDrone(true, 10000, 200);

    @Expose
    public MagneticField magneticFieldSettings = new MagneticField(true, 10000.0f, 50.0f, 32, 16);

    public void load() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        Path configPath = FMLPaths.CONFIGDIR.get().resolve(CONFIG_FILE);
        File file = configPath.toFile();

        try {
            if (!file.exists()) {
                System.out.println("Configuration file does not exist. Creating a new one.");
                saveConfig(file, gson);
            } else {
                try (JsonReader jsonReader = new JsonReader(new FileReader(file))) {
                    INSTANCE = gson.fromJson(jsonReader, ConfigLoader.class);
                    if (INSTANCE == null) {
                        throw new JsonSyntaxException("Parsed configuration is null.");
                    }
                    validateAndUpgrade(file, gson);
                }
            }
        } catch (JsonSyntaxException | IOException e) {
            System.err.println("Invalid configuration file. Regenerating default config.");
            saveConfig(file, gson);
        }
    }

    private void validateAndUpgrade(File file, Gson gson) {
        boolean needsSaving = false;

        if (INSTANCE.dimensionalDroneSettings == null) {
            INSTANCE.dimensionalDroneSettings = new DimensionalDrone(true, 10000, 200);
            needsSaving = true;
        } else if (INSTANCE.dimensionalDroneSettings.cooldownTicks() <= 0) {
            INSTANCE.dimensionalDroneSettings = new DimensionalDrone(
                    INSTANCE.dimensionalDroneSettings.enabled(),
                    INSTANCE.dimensionalDroneSettings.energyToCross(),
                    200
            );
            needsSaving = true;
            System.out.println("Upgrading Config: Added missing cooldownTicks to DimensionalDrone settings.");
        }

        if (INSTANCE.exoJetPackSettings == null) {
            INSTANCE.exoJetPackSettings = new ExoJetPack(true, 10000);
            needsSaving = true;
        }
        if (INSTANCE.magneticFieldSettings == null) {
            INSTANCE.magneticFieldSettings = new MagneticField(true, 10000.0f, 50.0f, 32, 16);
            needsSaving = true;
        }
        if (INSTANCE.addonSettings == null || INSTANCE.addonSettings.isEmpty()) {
            INSTANCE = new ConfigLoader();
            needsSaving = true;
        }

        if (needsSaving) {
            saveConfig(file, gson);
        }
    }

    private void saveConfig(File file, Gson gson) {
        try (FileWriter writer = new FileWriter(file)) {
            if(INSTANCE == null) INSTANCE = new ConfigLoader();

            gson.toJson(INSTANCE, ConfigLoader.class, writer);
            System.out.println("Configuration file saved successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create or update configuration file.", e);
        }
    }

    public record AddonInfo(
            @Expose float speedMultiplier,
            @Expose float efficiencyDown,
            @Expose float efficiencyUp,
            @Expose long capacitorCapacity,
            @Expose long capacitorRate,
            @Expose long acceptorCapacity,
            @Expose long acceptorRate,
            @Expose float processingEfficiency,
            @Expose int processingChambers
    ) {
    }

    public record ExoJetPack(
            @Expose boolean enabledCreativeFlight,
            @Expose long rfThreshold
    ) {
    }

    public record DimensionalDrone(
            @Expose boolean enabled,
            @Expose int energyToCross,
            @Expose int cooldownTicks
    ) {
    }

    public record MagneticField(
            @Expose boolean enabled,
            @Expose float baseCost,
            @Expose float speedCostDivisor,
            @Expose int searchRadius,
            @Expose int minDistance
    ) {
    }
}