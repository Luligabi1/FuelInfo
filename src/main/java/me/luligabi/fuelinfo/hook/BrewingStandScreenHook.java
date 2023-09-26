package me.luligabi.fuelinfo.hook;

import me.luligabi.fuelinfo.mixin.HandledScreenAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.BrewingStandScreen;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.text.Text;

public class BrewingStandScreenHook {


    public static void render(BrewingStandScreen screen, DrawContext ctx, int mouseX, int mouseY) {
        BrewingStandScreenHandler screenHandler = screen.getScreenHandler();

        int x = ((HandledScreenAccessor) screen).getX();
        int y = ((HandledScreenAccessor) screen).getY();

        if((mouseX >= x+58 && mouseX <= x+78) && (mouseY >= y+42 && mouseY <= y+48)) {
            int i = (screenHandler.getFuel() * 3) + (screenHandler.getSlot(4).getStack().getCount() * 20) * 3;
            if(i > 0) {
                ctx.drawTooltip(MinecraftClient.getInstance().textRenderer, Text.translatable(
                        "message.fuelinfo.brewing_stand", i
                ), mouseX, mouseY);
            }
        }
    }
}