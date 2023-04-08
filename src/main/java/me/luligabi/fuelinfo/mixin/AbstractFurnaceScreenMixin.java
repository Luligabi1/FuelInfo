package me.luligabi.fuelinfo.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin extends HandledScreen<AbstractFurnaceScreenHandler> {

    public AbstractFurnaceScreenMixin(AbstractFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        AbstractFurnaceScreen<AbstractFurnaceScreenHandler> afs = ((AbstractFurnaceScreen<AbstractFurnaceScreenHandler>) (Object) this);
        AbstractFurnaceScreenHandler afsh = afs.getScreenHandler();

        int inventoryX = this.x;
        int inventoryY = this.y;

        // Check if player's mouse is hovering the "flame" icon between entry and fuel slots.
        if((mouseX >= inventoryX+56 && mouseX <= inventoryX+72) && (mouseY >= inventoryY+34 && mouseY <= inventoryY+50)) {

            // Get how many fuel ticks there are within already consumed items
            int consumedFuelTicks = ((AbstractFurnaceScreenHandlerAccessor) afsh).getPropertyDelegate().get(0);


            // Adds +1 item to the count if the furnace is burning still (avoids slight miscalculation)
            if(afsh.isBurning()) consumedFuelTicks += 100;

            // Get how many burning ticks there are within items in the fuel slot, but that haven't been consumed yet.
            Map<Item, Integer> fuelMap = AbstractFurnaceBlockEntity.createFuelTimeMap();
            int toBeConsumedFuelTicks = 0;

            ItemStack slotStack = afsh.getSlot(1).getStack();
            if(fuelMap.containsKey(slotStack.getItem())) {
                toBeConsumedFuelTicks += fuelMap.get(slotStack.getItem()) * slotStack.getCount();
            }

            // consumed ticks are divided by 100, while unconsumed are by 200. don't ask me why it works like that lol
            afs.renderTooltip(matrices, Text.translatable("message.fuelinfo.furnace",
            (consumedFuelTicks/100) + (toBeConsumedFuelTicks/200)),
                    mouseX, mouseY);
        }
    }
}