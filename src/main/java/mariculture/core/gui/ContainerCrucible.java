package mariculture.core.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.tile.TileCrucible;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrucible extends ContainerMachine {
    public ContainerCrucible(TileCrucible tile, InventoryPlayer inventory) {
        super(tile);

        addUpgradeSlots(tile);

        addSlotToContainer(new SlotFluidContainer(tile, 3, 145, 36));
        addSlotToContainer(new SlotOutput(tile, 4, 145, 67));

        for (int i = 5; i <= 6; i++) {
            addSlotToContainer(new Slot(tile, i, 29 + (i - 5) * 18, 21));
        }

        addSlotToContainer(new SlotFuel(tile, 7, 38, 59));
        addSlotToContainer(new SlotOutput(tile, 8, 145, 13));

        bindPlayerInventory(inventory, 10);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        int size = getSizeInventory();
        int low = size + 27;
        int high = low + 9;
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (slotID < size) {
                if (!mergeItemStack(stack, size, high, true)) return null;

                slot.onSlotChange(stack, itemstack);
            } else if (slotID >= size) {
                if (MaricultureHandlers.crucible.getFuelInfo(stack) != null) {
                    if (!mergeItemStack(stack, 7, 8, false)) return null;
                } else if (MaricultureHandlers.crucible.getResult(stack, null, -1, true) != null) {
                    if (!mergeItemStack(stack, 5, 7, false)) return null;
                } else if (FluidHelper.isFluidOrEmpty(stack)) {
                    if (!mergeItemStack(stack, 3, 4, false)) return null;
                } else if (stack.getItem() instanceof IItemUpgrade) {
                    if (!mergeItemStack(stack, 0, 3, false)) return null;
                } else if (slotID >= size && slotID < low) {
                    if (!mergeItemStack(stack, low, high, false)) return null;
                } else if (slotID >= low && slotID < high && !mergeItemStack(stack, high, low, false)) return null;
            } else if (!mergeItemStack(stack, size, high, false)) return null;

            if (stack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (stack.stackSize == itemstack.stackSize) return null;

            slot.onPickupFromSlot(player, stack);
        }

        return itemstack;
    }
}