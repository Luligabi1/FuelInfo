package me.luligabi.fuelinfo.mixin;

import me.luligabi.fuelinfo.hook.BrewingStandScreenHook;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandScreen.class)
public abstract class BrewingStandScreenMixin /* extends AbstractContainerScreen<BrewingStandMenu>*/ {

    @Unique
    private Level fuelInfo_level;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void BrewingStandScreen(BrewingStandMenu brewingStandMenu, Inventory inventory, Component component, CallbackInfo ci) {
        fuelInfo_level = inventory.player.level();
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        BrewingStandScreen screen = ((BrewingStandScreen) (Object) this);
        BrewingStandScreenHook.render(screen, gui, mouseX, mouseY, fuelInfo_level);
    }
}