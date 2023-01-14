package ten3.lib.capability.item;

import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import ten3.lib.capability.UtilCap;
import ten3.lib.tile.mac.CmTileMachine;

import javax.annotation.Nonnull;

public class InvHandler extends InvWrapper {

    Direction di;
    public CmTileMachine tile;

    public InvHandler(Direction d, CmTileMachine t) {

        super(t.inventory);
        di = d;
        tile = t;

    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {

        if(UtilCap.canInITM(di, tile)) {
            if(tile.inventory.isIn(slot) && tile.inventory.isUsed(slot) && isItemValid(slot, stack)) {
                return insertWithCap(tile.info.maxReceiveItem, stack, slot, simulate);
            }
        }
        return stack;

    }

    // FIXME: 2022/8/28
    public ItemStack insertWithCap(int cap, ItemStack insert, int slot, boolean sim) {

        if(insert.getCount() <= cap) {
            return super.insertItem(slot, insert, sim);
        }
        else {
            int overflow = insert.getCount() - cap;
            ItemStack remain = super.insertItem(slot, ItemHandlerHelper.copyStackWithSize(insert, cap), sim);
            int totalRemain = remain.getCount() + overflow;
            int totalInsert = cap - remain.getCount();
            if(!sim) {
                insert.shrink(totalInsert);
            }
            return ItemHandlerHelper.copyStackWithSize(insert, totalRemain);
        }

    }

    //still check item valid(in super)
    public ItemStack forceInsert(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if(tile.inventory.isUsed(slot)) {
            return super.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    public ItemStack forceExtract(int slot, int value, boolean simulate) {
        if(tile.inventory.isUsed(slot)) {
            return super.extractItem(slot, value, simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return getInv().canPlaceItem(slot, stack) && tile.customFitStackIn(stack, slot);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {

        amount = Math.min(tile.info.maxExtractItem, amount);

        if(UtilCap.canOutITM(di, tile)) {
            if(tile.inventory.isExt(slot) && tile.inventory.isUsed(slot)) {//!!!!
                return super.extractItem(slot, amount, simulate);
            }
        }

        return ItemStack.EMPTY;

    }

    @Override
    public Container getInv() {
        return tile.inventory;
    }

}
