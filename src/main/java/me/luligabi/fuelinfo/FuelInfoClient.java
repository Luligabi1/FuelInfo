package me.luligabi.fuelinfo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class FuelInfoClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LoggerFactory.getLogger("FuelInfo").info("Mod Initialized!");
    }

}
