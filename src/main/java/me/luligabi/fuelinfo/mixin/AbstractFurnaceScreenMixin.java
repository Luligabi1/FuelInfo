package me.luligabi.fuelinfo.mixin;

import me.luligabi.fuelinfo.hook.AbstractFurnaceScreenHook;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin extends HandledScreen<AbstractFurnaceScreenHandler> {

    public AbstractFurnaceScreenMixin(AbstractFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        AbstractFurnaceScreen<AbstractFurnaceScreenHandler> screen = ((AbstractFurnaceScreen<AbstractFurnaceScreenHandler>) (Object) this);
        AbstractFurnaceScreenHook.render(screen, ctx, mouseX, mouseY);
    }

}