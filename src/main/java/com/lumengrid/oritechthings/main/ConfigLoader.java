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
            new AddonInfo(0.79f, 1.10f,0.79f, 4_000_000, 2_000, 1_000_000, 4000, 2f, 2 ),
            new AddonInfo(0.7f, 1.15f, 0.7f, 6_000_000, 3_000, 1_500_000, 6000 , 2.5f, 3 ),
            new AddonInfo(0.6f, 1.20f, 0.6f, 8_000_000, 4_000, 2_000_000, 8000 , 3f, 4 ),
            new AddonInfo(0.5f, 1.25f, 0.5f, 10_000_000, 5_000, 2_500_000, 10000 , 3.5f, 5 ),
            new AddonInfo(0.4f, 1.30f, 0.4f, 12_000_000, 6_000, 3_000_000, 12000 , 4f, 6 ),
            new AddonInfo(0.3f, 1.35f, 0.3f, 14_000_000, 7_000, 3_500_000, 14000 , 4.5f, 7 ),
            new AddonInfo(0.2f, 1.40f, 0.2f, 16_000_000, 8_000, 4_000_000, 14000 , 5f, 8 ),
            new AddonInfo(0.1f, 1.45f, 0.1f, 18_000_000, 9_000, 4_500_000, 18000, 5.5f, 9 )
        )
    );

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
                saveDefaultConfig(file, gson);
            } else {
                try (JsonReader jsonReader = new JsonReader(new FileReader(file))) {
                    INSTANCE = gson.fromJson(jsonReader, ConfigLoader.class);
                    if (INSTANCE == null) {
                        throw new JsonSyntaxException("Parsed configuration is null.");
                    }
                }
            }
        } catch (JsonSyntaxException | IOException e) {
            System.err.println("Invalid configuration file. Regenerating default config.");
            saveDefaultConfig(file, gson);
        }
    }

    private void saveDefaultConfig(File file, Gson gson) {
        try (FileWriter writer = new FileWriter(file)) {
            if(INSTANCE == null) INSTANCE = new ConfigLoader();

            gson.toJson(INSTANCE, ConfigLoader.class, writer);
            System.out.println("Default configuration file created successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create default configuration file.", e);
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
}
