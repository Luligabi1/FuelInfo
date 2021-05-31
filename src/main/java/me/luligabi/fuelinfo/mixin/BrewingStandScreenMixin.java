package me.luligabi.fuelinfo.mixin;

import net.minecraft.client.gui.screen.ingame.BrewingStandScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandScreen.class)
public class BrewingStandScreenMixin {

    @Inject(method = "render",
            at = @At("RETURN"),
            cancellable = true)
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        BrewingStandScreen bss = ((BrewingStandScreen) (Object) this);
        BrewingStandScreenHandler bssh = bss.getScreenHandler();
        int fuelCount = bssh.getFuel() + (bssh.getSlot(4).getStack().getCount() * 20);

        bss.renderTooltip(matrices, new LiteralText("" + fuelCount*3), mouseX, mouseY);

        callbackInfo.cancel();
    }
}
