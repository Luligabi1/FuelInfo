package me.luligabi.fuelinfo.mixin;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin {

    @Inject(method = "render",
            at = @At("RETURN"),
            cancellable = true)
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        AbstractFurnaceScreen afs = ((AbstractFurnaceScreen) (Object) this);
        AbstractFurnaceScreenHandler afsh = (((AbstractFurnaceScreenHandler) afs.getScreenHandler()));

        //afs.recipeBook.isOpen()

        int inventoryX = ((HandledScreenAccessor) afs).getX();
        int inventoryY = ((HandledScreenAccessor) afs).getY();


        int consumedFuelTicks = ((AbstractFurnaceScreenHandlerAccessor) afsh).getPropertyDelegate().get(0);

        if(afsh.isBurning()) {
            consumedFuelTicks += 200;
        }

        Map<Item, Integer> fuelMap = AbstractFurnaceBlockEntity.createFuelTimeMap();
        int toBeConsumedFuelTicks = 0;

        if(fuelMap.containsKey(afsh.getSlot(1).getStack().getItem())) {
            toBeConsumedFuelTicks += fuelMap.get(afsh.getSlot(1).getStack().getItem()) * afsh.getSlot(1).getStack().getCount();
            //System.out.println(fuelMap.get(afsh.getSlot(1).getStack().getItem()));
        }

        //if((mouseX >= inventoryX+208 && inventoryX+224 <= mouseX) && (mouseY >= inventoryY+92 && inventoryY+108 <= mouseY)) {
            afs.renderTooltip(matrices, new TranslatableText("message.fuelinfo.furnace",
                    fuelTicksToItems(consumedFuelTicks) + fuelTicksToItems(toBeConsumedFuelTicks),
                    consumedFuelTicks + toBeConsumedFuelTicks)
                    , mouseX, mouseY);
        //}
        callbackInfo.cancel();
    }

    private int fuelTicksToItems(int fuelTicks) {
        return fuelTicks/200;
    }
}