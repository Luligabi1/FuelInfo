package me.luligabi.fuelinfo.hook;

import me.luligabi.fuelinfo.mixin.HandledScreenAccessor;
import me.luligabi.fuelinfo.util.TimerUtil;
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
            int fuel = (screenHandler.getFuel() * 3) + (screenHandler.getSlot(4).getStack().getCount() * 20) * 3;

            if(fuel > 0) {
                Text fuelText = Text.translatable(
                    "message.fuelinfo.brewing_stand",
                    fuel
                );
                ctx.drawTooltip(MinecraftClient.getInstance().textRenderer, fuelText, mouseX, mouseY);
            }
        }

        if((mouseX >= x+98 && mouseX <= x+104) && (mouseY >= y+17 && mouseY <= y+42)) {
            int time = screenHandler.getBrewTime() / 20;

            if(time > 0) {
                Text timeText = Text.translatable(
                    "message.fuelinfo.timer",
                    "00", TimerUtil.format(time)
                );

                ctx.drawTooltip(MinecraftClient.getInstance().textRenderer, timeText, mouseX, mouseY);
            }
        }
    }
}