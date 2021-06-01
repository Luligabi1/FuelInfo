package me.luligabi.fuelinfo.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin extends HandledScreen {

    public AbstractFurnaceScreenMixin(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "render",
            at = @At("RETURN"),
            cancellable = true)
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        AbstractFurnaceScreen afs = ((AbstractFurnaceScreen) (Object) this);
        AbstractFurnaceScreenHandler afsh = (((AbstractFurnaceScreenHandler) afs.getScreenHandler()));

        int inventoryX = this.x;
        int inventoryY = this.y;

        if((mouseX >= inventoryX+56 && mouseX <= inventoryX+72) && (mouseY >= inventoryY+34 && mouseY <= inventoryY+50)) {

        // Calculates how many fuel ticks there are within already consumed items.
        int consumedFuelTicks = ((AbstractFurnaceScreenHandlerAccessor) afsh).getPropertyDelegate().get(0);

        // Adds +1 item to the count if the furnace is burning still (avoids slight miscalculation)
        if(afsh.isBurning()) {
            consumedFuelTicks += 200;
        }

        // Calculates how many burning ticks there are within items in the fuel slot, but that haven't been consumed yet.
        Map<Item, Integer> fuelMap = AbstractFurnaceBlockEntity.createFuelTimeMap();
        int toBeConsumedFuelTicks = 0;

        if(fuelMap.containsKey(afsh.getSlot(1).getStack().getItem())) {
            toBeConsumedFuelTicks += fuelMap.get(afsh.getSlot(1).getStack().getItem()) * afsh.getSlot(1).getStack().getCount();
        }

            afs.renderTooltip(matrices, new TranslatableText("message.fuelinfo.furnace",
                    fuelTicksToItems(consumedFuelTicks + toBeConsumedFuelTicks)).setStyle(Style.EMPTY).formatted(Formatting.GRAY)
                    , mouseX, mouseY);

        }
        callbackInfo.cancel();
    }

    // Converts fuel ticks to how many items can be smelt.
    private int fuelTicksToItems(int fuelTicks) {
        return fuelTicks/200;
    }
}