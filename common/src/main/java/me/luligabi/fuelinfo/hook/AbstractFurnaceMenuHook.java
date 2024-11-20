package me.luligabi.fuelinfo.hook;

import me.luligabi.fuelinfo.FuelInfo;
import me.luligabi.fuelinfo.mixin.AbstractFurnaceMenuAccessor;
import me.luligabi.fuelinfo.mixin.AbstractContainerScreenAccessor;
import me.luligabi.fuelinfo.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractFurnaceMenuHook {


    private static final List<Component> COMPONENTS = new ArrayList<>();

    public static void render(AbstractFurnaceScreen<AbstractFurnaceMenu> screen, GuiGraphics gui, int mouseX, int mouseY) {
        AbstractFurnaceMenu menu = screen.getMenu();
        ContainerData data = ((AbstractFurnaceMenuAccessor) menu).getData();
        int x = ((AbstractContainerScreenAccessor) screen).getX();
        int y = ((AbstractContainerScreenAccessor) screen).getY();

        if((mouseX >= x + 56 && mouseX <= x + 72) && (mouseY >= y + 35 && mouseY <= y + 50)) {
            addFuelData(menu, data, FuelInfo.CONFIG.furnace.flame.showFuelData);
            addTimer(menu, data, FuelInfo.CONFIG.furnace.flame.showTimer);
            if(!COMPONENTS.isEmpty()) {
                gui.renderComponentTooltip(Minecraft.getInstance().font, COMPONENTS, mouseX, mouseY);
                COMPONENTS.clear();
            }
        }
        if((mouseX >= x + 80 && mouseX <= x + 102) && (mouseY >= y + 35 && mouseY <= y + 50)) {
            addFuelData(menu, data, FuelInfo.CONFIG.furnace.progressArrow.showFuelData);
            addTimer(menu, data, FuelInfo.CONFIG.furnace.progressArrow.showTimer);
            if(!COMPONENTS.isEmpty()) {
                gui.renderComponentTooltip(Minecraft.getInstance().font, COMPONENTS, mouseX, mouseY);
                COMPONENTS.clear();
            }
        }
    }

    private static void addFuelData(AbstractFurnaceMenu menu, ContainerData data, boolean canAdd) {
        if(!canAdd) return;

        // Get how many fuel ticks there are within already consumed items
        int consumedFuelTicks = data.get(0);

        // Add +1 item to the count if the furnace is burning still,
        // accounting the item that is currently being smelted
        if(menu.isLit()) {
            consumedFuelTicks += isSpecialFurnace(menu) ? 100 : 200;
        }

        // Get how many burning ticks there are within items in the fuel slot, but that haven't been consumed yet
        int toBeConsumedFuelTicks = 0;

        ItemStack fuelStack = menu.getSlot(1).getItem();
        toBeConsumedFuelTicks += Minecraft.getInstance().level.fuelValues().burnDuration(fuelStack) * fuelStack.getCount();


        // Account for the fact special furnaces (i.e. Blast Furnace
        // and Smoker on vanilla) smelts stuff twice as fast
        if(isSpecialFurnace(menu)) {
            consumedFuelTicks /= 100;
        } else {
            consumedFuelTicks /= 200;
        }
        // Combine fuel ticks
        int i = (consumedFuelTicks) + (toBeConsumedFuelTicks / 200);
        if(i <= 0) return;
        Component fuelText;
        if(!Screen.hasShiftDown()) {
            int stacks = i / 64;
            int items = i % 64;

            if(stacks > 0) {
                if(items > 0) {
                    fuelText = Component.translatable("message.fuelinfo.furnace.both", stacks, items);
                } else {
                    fuelText = Component.translatable("message.fuelinfo.furnace.stacks", stacks);
                }
            } else {
                fuelText = Component.translatable("message.fuelinfo.furnace.items", items);
            }
        } else { // Shift-action: Don't show stacks
            fuelText = Component.translatable("message.fuelinfo.furnace.items", i);
        }
        COMPONENTS.add(fuelText);
    }

    private static void addTimer(AbstractFurnaceMenu menu, ContainerData data, boolean canAdd) {
        if(!canAdd) return;

        Component timeText;
        ItemStack inputStack = menu.getSlot(0).getItem();

        if(inputStack.isEmpty()) return;
        float tickrate = ((AbstractFurnaceMenuAccessor) menu).getLevel().tickRateManager().tickrate();

        float currentStackTime = (data.get(3) - data.get(2));
        float remainingStacksTime = (data.get(3) * (inputStack.getCount() - 1));
        int time = Math.round((currentStackTime + remainingStacksTime) / tickrate);

        boolean isIndividualTime = Screen.hasShiftDown();
        Tuple<String, String> timePair = TimerUtil.getTime(isIndividualTime ? Math.round(currentStackTime / tickrate) : time);

        timeText = Component.translatable(
            "message.fuelinfo.timer" + (isIndividualTime ? ".current" : ""),
            timePair.getA(), timePair.getB()
        );
        COMPONENTS.add(timeText);
    }

    // TODO: Investigate if there's better way to determine this on Neoforge
    private static boolean isSpecialFurnace(AbstractFurnaceMenu menu) {
        return ((AbstractFurnaceMenuAccessor) menu).getRecipeType() != RecipeType.SMELTING;
    }

}