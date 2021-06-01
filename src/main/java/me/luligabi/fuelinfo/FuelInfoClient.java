package me.luligabi.fuelinfo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;

@Environment(EnvType.CLIENT)
public class FuelInfoClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() { LogManager.getLogger("FuelInfo").info("Mod Initialized!"); }
}
