package me.luligabi.fuelinfo.mixin;

import net.minecraft.client.gui.screen.ingame.BrewingStandScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
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
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        BrewingStandScreen bss = ((BrewingStandScreen) (Object) this);
        BrewingStandScreenHandler bssh = bss.getScreenHandler();

        int inventoryX = this.x;
        int inventoryY = this.y;

        if((mouseX >= inventoryX+58 && mouseX <= inventoryX+78) && (mouseY >= inventoryY+42 && mouseY <= inventoryY+48)) {
            int i = (bssh.getFuel() * 3) + (bssh.getSlot(4).getStack().getCount() * 20) * 3;
            if(i > 0) {
                bss.renderTooltip(matrices, Text.translatable(
                    "message.fuelinfo.brewing_stand", i
                ), mouseX, mouseY);
            }
        }
    }
}