package me.luligabi.fuelinfo.forge;

import me.luligabi.fuelinfo.FuelInfo;
import me.luligabi.fuelinfo.config.ModConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = FuelInfo.MOD_ID, value = Dist.CLIENT)
public class ConfigScreenEventRegistry {

    @SubscribeEvent
    public static void onPostInit(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(
            IConfigScreenFactory.class,
            () -> (client, parent) -> ModConfigScreen.createConfigScreen(parent)
        );
    }
}