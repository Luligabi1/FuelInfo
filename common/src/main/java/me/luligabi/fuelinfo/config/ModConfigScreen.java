package me.luligabi.fuelinfo.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import me.luligabi.fuelinfo.FuelInfo;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfigScreen {

    public static Screen createConfigScreen(Screen parent) {
        ModConfig config = FuelInfo.CONFIG;

        // Furnace
        Option<Boolean> furnaceFlameShowFuelData = showFuelData("flame")
            .binding(
                true,
                () -> config.furnace.flame.showFuelData,
                newValue -> config.furnace.flame.showFuelData = newValue
            )
            .build();

        Option<Boolean> furnaceFlameShowTimer = showTimer("flame")
            .binding(
                false,
                () -> config.furnace.flame.showTimer,
                newValue -> config.furnace.flame.showTimer = newValue
            )
            .build();

        Option<Boolean> furnaceProgressArrowShowFuelData = showFuelData("progress_arrow_furnace")
            .binding(
                false,
                () -> config.furnace.progressArrow.showFuelData,
                newValue -> config.furnace.progressArrow.showFuelData = newValue
            )
            .build();

        Option<Boolean> furnaceProgressArrowShowTimer = showTimer("progress_arrow_furnace")
            .binding(
                true,
                () -> config.furnace.progressArrow.showTimer,
                newValue -> config.furnace.progressArrow.showTimer = newValue
            )
            .build();

        // Brewing Stand
        Option<Boolean> brewingStandGaugeShowFuelData = showFuelData("gauge")
            .binding(
                true,
                () -> config.brewingStand.gauge.showFuelData,
                newValue -> config.brewingStand.gauge.showFuelData = newValue
            )
            .build();

        Option<Boolean> brewingStandGaugeShowTimer = showTimer("gauge")
            .binding(
                false,
                () -> config.brewingStand.gauge.showTimer,
                newValue -> config.brewingStand.gauge.showTimer = newValue
            )
            .build();

        Option<Boolean> brewingStandProgressArrowShowFuelData = showFuelData("progress_arrow_brewing_stand")
            .binding(
                false,
                () -> config.brewingStand.progressArrow.showFuelData,
                newValue -> config.brewingStand.progressArrow.showFuelData = newValue
            )
            .build();

        Option<Boolean> brewingStandProgressArrowShowTimer = showTimer("progress_arrow_brewing_stand")
            .binding(
                true,
                () -> config.brewingStand.progressArrow.showTimer,
                newValue -> config.brewingStand.progressArrow.showTimer = newValue
            )
            .build();

        return YetAnotherConfigLib.createBuilder()
            .title(Component.literal("Fuel Info"))
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("block.minecraft.furnace"))
                .group(
                    createGroup(
                        "flame",
                        "flame",
                        furnaceFlameShowFuelData,
                        furnaceFlameShowTimer
                    )
                )
                .group(
                    createGroup(
                        "progress_arrow",
                        "progress_arrow_furnace",
                        furnaceProgressArrowShowFuelData,
                        furnaceProgressArrowShowTimer
                    )
                )
                .build())
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("block.minecraft.brewing_stand"))
                .group(
                    createGroup(
                        "gauge",
                        "gauge",
                        brewingStandGaugeShowFuelData,
                        brewingStandGaugeShowTimer
                    )
                )
                .group(
                    createGroup(
                        "progress_arrow",
                        "progress_arrow_brewing_stand",
                        brewingStandProgressArrowShowFuelData,
                        brewingStandProgressArrowShowTimer
                    )
                )
                .build())
            .save(ModConfig.HANDLER::save)
            .build()
            .generateScreen(parent);
    }


    private static Option.Builder<Boolean> showFuelData(String imageId) {
        return Option.<Boolean>createBuilder()
            .name(Component.translatable("configOption.fuelinfo.showFuelData"))
            .description(OptionDescription.createBuilder()
                .text(Component.translatable(("configOption.fuelinfo.showFuelData.desc")))
                .image(FuelInfo.id(String.format("textures/config/%s.png", imageId)), 1035, 720)
                .build()
            )
            .controller(option -> BooleanControllerBuilder.create(option).yesNoFormatter().coloured(true));
    }


    private static Option.Builder<Boolean> showTimer(String imageId) {
        return Option.<Boolean>createBuilder()
            .name(Component.translatable("configOption.fuelinfo.showTimer"))
            .description(OptionDescription.createBuilder()
                .text(Component.translatable(("configOption.fuelinfo.showTimer.desc")))
                .image(FuelInfo.id(String.format("textures/config/%s.png", imageId)), 1035, 720)
                .build()
            )
            .controller(option -> BooleanControllerBuilder.create(option).yesNoFormatter().coloured(true));
    }


    private static OptionGroup createGroup(String id, String imageId, Option<Boolean> showFuelData, Option<Boolean> showTimer) {
        return OptionGroup.createBuilder()
            .name(Component.translatable("configGroup.fuelinfo." + id))
            .description(OptionDescription.createBuilder()
                .text(Component.translatable(String.format("configGroup.fuelinfo.%s.desc", id)))
                .image(FuelInfo.id(String.format("textures/config/%s.png", imageId)), 1035, 720)
                .build()
            )
            .option(showFuelData)
            .option(showTimer)
            .build();
    }
}