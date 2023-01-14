package ten3.lib.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import ten3.init.ContInit;
import ten3.lib.tile.mac.CmTileEntity;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.wrapper.IntArrayCm;

public class CmContainerMachine extends AbstractContainerMenu
{

    public final IntArrayCm data;
    public final IntArrayCm fluidData;
    public final IntArrayCm fluidAmount;

    public String name;
    public BlockPos pos;
    public CmTileMachine tile;

    public CmContainerMachine(int cid, String id, CmTileMachine ti, Inventory pi, BlockPos pos, IntArrayCm data, IntArrayCm f1, IntArrayCm f2) {

        super(ContInit.getType(id), cid);

        this.data = data;
        this.pos = pos;
        fluidData = f1;
        fluidAmount = f2;

        layoutInventorySlots(pi, 141, 0);
        layoutInventorySlots(pi, 83, 9);
        layoutInventorySlots(pi, 101, 18);
        layoutInventorySlots(pi, 119, 27);

        //after player, care!
        this.tile = ti;
        for(Slot slot : tile.slots) {
            addSlot(slot);
        }

        if(data != null) {
            addDataSlots(data);
        }
        if(fluidData != null) {
            addDataSlots(fluidData);
        }
        if(fluidAmount != null) {
            addDataSlots(fluidAmount);
        }

        this.name = id;
    }

    public void layoutInventorySlots(Container ct, int y, int from) {
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(ct, k + from, 7 + 1 + k * 18, y + 1));
        }
    }

    public static boolean isInBackpack(int slot) {
        return slot >= playerMin && slot < playerMax;
    }

    public static boolean isInFastBar(int slot) {
        return slot >= fastMin && slot < fastMax;
    }

    static int playerMin=0;
    static int playerMax=36;
    static int fastMin=0;
    static int fastMax=9;

    public ItemStack quickMoveStack(Player playerIn, int index) {

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = slots.get(index);

            if(slot.hasItem()) {
                ItemStack itemstack1 = slot.getItem();
                itemstack = itemstack1.copy();
                //from: crafting table menu
                if (isInBackpack(index)) {
                    if(!this.moveItemStackTo(itemstack1, playerMax, slots.size(), false)) {
                        if(!isInFastBar(index)) {
                            if(!this.moveItemStackTo(itemstack1, fastMin, fastMax, false)) {
                                //to fast bar
                                return ItemStack.EMPTY;
                            }
                        } else if(!this.moveItemStackTo(itemstack1, fastMax, playerMax, false)) {
                            //to other backpack slots
                            return ItemStack.EMPTY;
                        }
                    }
                }
                else if (!this.moveItemStackTo(itemstack1, playerMin, playerMax, false)) {
                    return ItemStack.EMPTY;
                }
                if(itemstack1.getCount() == 0) {
                    slot.set(ItemStack.EMPTY);
                } else {
                    slot.setChanged();
                }
                if(itemstack1.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                }
                slot.onTake(playerIn, itemstack1);
            }

            return itemstack;

    }

    @Override
    public boolean stillValid(Player player)
    {
        return pos.closerThan(player.getOnPos(), 12);
    }

}
