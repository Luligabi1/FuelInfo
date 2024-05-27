package me.luligabi.fuelinfo.config;

import dev.architectury.platform.Platform;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.luligabi.fuelinfo.FuelInfo;

public class ModConfig {

    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
        .id(FuelInfo.id("config"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setJson5(true)
            .setPath(Platform.getConfigFolder().resolve("fuelinfo.json5"))
            .build())
        .build();



    @SerialEntry
    public Furnace furnace = new Furnace();
    @SerialEntry
    public BrewingStand brewingStand = new BrewingStand();

    public static class Furnace {

        @SerialEntry
        public Flame flame = new Flame();
        @SerialEntry
        public ProgressArrow progressArrow = new ProgressArrow();

        public static class Flame {

            @SerialEntry
            public boolean showFuelData = true;
            @SerialEntry
            public boolean showTimer = false;
        }

        public static class ProgressArrow {

            @SerialEntry
            public boolean showFuelData = false;
            @SerialEntry
            public boolean showTimer = true;
        }
    }

    public static class BrewingStand {

        @SerialEntry public Gauge gauge = new Gauge();
        @SerialEntry public ProgressArrow progressArrow = new ProgressArrow();

        public static class Gauge {

            @SerialEntry public boolean showFuelData = true;
            @SerialEntry public boolean showTimer = false;
        }

        public static class ProgressArrow {

            @SerialEntry public boolean showFuelData = false;
            @SerialEntry public boolean showTimer = true;
        }
    }

}