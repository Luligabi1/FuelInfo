package me.luligabi.fuelinfo;

import me.luligabi.fuelinfo.config.ModConfig;
import net.minecraft.resources.Identifier;

public class FuelInfo {

	public static Identifier id(String id) {
		return Identifier.fromNamespaceAndPath(MOD_ID, id);
	}

	public static final String MOD_ID = "fuelinfo";
	public static final ModConfig CONFIG;

	static {
		ModConfig.HANDLER.load();
		CONFIG = ModConfig.HANDLER.instance();
	}
}