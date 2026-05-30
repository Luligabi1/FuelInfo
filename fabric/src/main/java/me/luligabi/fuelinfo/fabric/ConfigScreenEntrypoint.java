package me.luligabi.fuelinfo.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.luligabi.fuelinfo.config.ModConfigScreen;

public class ConfigScreenEntrypoint implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfigScreen::createConfigScreen;
    }
}
