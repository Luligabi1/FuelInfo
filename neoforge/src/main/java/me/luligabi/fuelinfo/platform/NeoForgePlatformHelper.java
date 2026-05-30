package me.luligabi.fuelinfo.platform;

import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public Path getConfigFolder() {
        return FMLPaths.CONFIGDIR.get();
    }

}