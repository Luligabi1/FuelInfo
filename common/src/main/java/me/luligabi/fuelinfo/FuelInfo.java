package me.luligabi.fuelinfo;

import me.luligabi.fuelinfo.config.ModConfig;
import net.minecraft.resources.ResourceLocation;

public class FuelInfo {

	public static ResourceLocation id(String id) {
		return new ResourceLocation(MOD_ID, id);
	}

	public static final String MOD_ID = "fuelinfo";
	public static final ModConfig CONFIG;

	static {
		ModConfig.HANDLER.load();
		CONFIG = ModConfig.HANDLER.instance();
	}
}