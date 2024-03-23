package me.luligabi.fuelinfo.mixin;

import me.luligabi.fuelinfo.hook.AbstractFurnaceMenuHook;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin extends AbstractContainerScreen<AbstractFurnaceMenu> {

    public AbstractFurnaceScreenMixin(AbstractFurnaceMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        AbstractFurnaceScreen<AbstractFurnaceMenu> screen = ((AbstractFurnaceScreen<AbstractFurnaceMenu>) (Object) this);
        AbstractFurnaceMenuHook.render(screen, gui, mouseX, mouseY);
    }

}