package me.luligabi.fuelinfo.hook;

import me.luligabi.fuelinfo.mixin.AbstractFurnaceScreenHandlerAccessor;
import me.luligabi.fuelinfo.mixin.HandledScreenAccessor;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.Map;

public class AbstractFurnaceScreenHook {


    public static void render(AbstractFurnaceScreen<AbstractFurnaceScreenHandler> screen, DrawContext ctx, int mouseX, int mouseY) {
        AbstractFurnaceScreenHandler screenHandler = screen.getScreenHandler();
        PropertyDelegate propertyDelegate = ((AbstractFurnaceScreenHandlerAccessor) screenHandler).getPropertyDelegate();
        
        
        int x = ((HandledScreenAccessor) screen).getX();
        int y = ((HandledScreenAccessor) screen).getY();

        // Check if player's mouse is hovering the "flame" icon between entry and fuel slots.
        if((mouseX >= x+56 && mouseX <= x+72) && (mouseY >= y+34 && mouseY <= y+50)) {

            // Get how many fuel ticks there are within already consumed items
            int consumedFuelTicks = propertyDelegate.get(0);


            // Add +1 item to the count if the furnace is burning still,
            // accounting the item that is currently being smelted
            if(screenHandler.isBurning()) {
                consumedFuelTicks += isSpecialFurnace(screenHandler) ? 100 : 200;
            }

            // Get how many burning ticks there are within items in the fuel slot, but that haven't been consumed yet.
            Map<Item, Integer> fuelMap = AbstractFurnaceBlockEntity.createFuelTimeMap();
            int toBeConsumedFuelTicks = 0;

            ItemStack fuelStack = screenHandler.getSlot(1).getStack();
            if(fuelMap.containsKey(fuelStack.getItem())) {
                toBeConsumedFuelTicks += fuelMap.get(fuelStack.getItem()) * fuelStack.getCount();
            }

            // combine fuel ticks
            if (isSpecialFurnace(screenHandler)) {
                consumedFuelTicks /= 100;
            } else {
                consumedFuelTicks /= 200;
            }
            int i = (consumedFuelTicks) + (toBeConsumedFuelTicks/200);
            if(i > 0) {

                // Fuel Info Logic
                Text fuelText;
                if(!Screen.hasShiftDown()) {
                    int stacks = i / 64;
                    int items = i % 64;

                    if (stacks > 0) {
                        if (items > 0) {
                            fuelText = Text.translatable("message.fuelinfo.furnace.both", stacks, items);
                        } else {
                            fuelText = Text.translatable("message.fuelinfo.furnace.stacks", stacks);
                        }
                    } else {
                        fuelText = Text.translatable("message.fuelinfo.furnace.items", items);
                    }
                } else { // Shift-action: Don't show stacks
                    fuelText = Text.translatable("message.fuelinfo.furnace.items", i);
                }


                // Timer Logic
                Text timeText;
                ItemStack inputStack = screenHandler.getSlot(0).getStack();

                if(!inputStack.isEmpty()) {
                    int currentStackTime = (propertyDelegate.get(3) - propertyDelegate.get(2));
                    int remainingStacksTime = (propertyDelegate.get(3) * (inputStack.getCount() - 1));
                    int time = (currentStackTime + remainingStacksTime) / 20;

                    boolean isIndividualTime = Screen.hasShiftDown();
                    Pair<String, String> timePair = getTime(isIndividualTime ? currentStackTime / 20 : time);

                    timeText = Text.translatable(
                        "message.fuelinfo.timer" + (isIndividualTime ? ".current" : ""),
                            timePair.getLeft(), timePair.getRight()
                    );
                } else {
                    timeText = Text.translatable(
                        "message.fuelinfo.timer",
                        "00", "00"
                    );
                }


                List<Text> texts = List.of(
                        fuelText,
                        Text.of(""),
                        timeText
                );
                ctx.drawTooltip(MinecraftClient.getInstance().textRenderer, texts, mouseX, mouseY);
            }
        }
    }


    private static boolean isSpecialFurnace(AbstractFurnaceScreenHandler screenHandler) {
        return ((AbstractFurnaceScreenHandlerAccessor) screenHandler).getRecipeType() != RecipeType.SMELTING;
    }

    private static Pair<String, String> getTime(int time) {
        String minutes = String.format("%02d", (time % 3600) / 60);
        String seconds = String.format("%02d", time % 60);

        return new Pair<>(minutes, seconds);
    }

}
