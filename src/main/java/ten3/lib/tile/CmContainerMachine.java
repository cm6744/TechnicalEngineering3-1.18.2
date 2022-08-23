package ten3.lib.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import ten3.core.item.upgrades.UpgradeItem;
import ten3.init.ContInit;
import ten3.lib.capability.item.InventoryCm;
import ten3.lib.wrapper.IntArrayCm;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotUpgCm;

import java.util.List;

public class CmContainerMachine extends CmContainer {

    public CmContainerMachine(int cid, String id, CmTileEntity ti, Inventory pi, BlockPos pos, IntArrayCm data) {

        super(ContInit.getType(id), cid, id, ti, pi, pos, data);

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
                    int[] iarr = tile.getItemFirstTransferSlot(itemstack1.getItem());
                    if(iarr.length == 2) {
                        int p1 = slots.indexOf(tileInv.match(iarr[0]));
                        int p2 = slots.indexOf(tileInv.match(iarr[1]));
                        if(!moveItemStackTo(itemstack1, p1, p2, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
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
