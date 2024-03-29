package me.luligabi.fuelinfo.hook;

import me.luligabi.fuelinfo.mixin.AbstractContainerScreenAccessor;
import me.luligabi.fuelinfo.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.level.Level;


public class BrewingStandScreenHook {


    public static void render(BrewingStandScreen screen, GuiGraphics gui, int mouseX, int mouseY, Level level) {
        BrewingStandMenu menu = screen.getMenu();
        int x = ((AbstractContainerScreenAccessor) screen).getX();
        int y = ((AbstractContainerScreenAccessor) screen).getY();

        if((mouseX >= x + 58 && mouseX <= x + 78) && (mouseY >= y + 42 && mouseY <= y + 48)) {
            int potionCount = (menu.getFuel() * 3) + (menu.getSlot(4).getItem().getCount() * 20) * 3;

            if(potionCount > 0) {
                Component countText;

                if(Screen.hasShiftDown()) {
                    countText = Component.translatable(
                        "message.fuelinfo.brewing_stand.set",
                        potionCount / 3
                    );
                } else {
                    countText = Component.translatable(
                        "message.fuelinfo.brewing_stand",
                        potionCount
                    );
                }
                gui.renderTooltip(Minecraft.getInstance().font, countText, mouseX, mouseY);
            }
        }

        if((mouseX >= x + 98 && mouseX <= x + 104) && (mouseY >= y + 17 && mouseY <= y + 42)) {
            int time = Math.round(menu.getBrewingTicks() / level.tickRateManager().tickrate());

            if(time > 0) {
                Component timeText = Component.translatable(
                    "message.fuelinfo.timer",
                    "00", TimerUtil.format(time)
                );
                gui.renderTooltip(Minecraft.getInstance().font, timeText, mouseX, mouseY);
            }
        }
    }
}