package me.luligabi.fuelinfo.mixin;

import me.luligabi.fuelinfo.hook.BrewingStandScreenHook;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.BrewingStandScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandScreen.class)
public abstract class BrewingStandScreenMixin extends HandledScreen<BrewingStandScreenHandler> {

    public BrewingStandScreenMixin(BrewingStandScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        BrewingStandScreen screen = ((BrewingStandScreen) (Object) this);
        BrewingStandScreenHook.render(screen, ctx, mouseX, mouseY);
    }
}