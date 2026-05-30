package me.luligabi.fuelinfo.platform;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public Path getConfigFolder() {
        return FabricLoader.getInstance()
                .getConfigDir()
                .toAbsolutePath()
                .normalize();
    }

}