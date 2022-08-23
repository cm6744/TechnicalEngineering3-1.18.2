package ten3.lib.capability.item;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import ten3.lib.tile.CmTileEntity;
import ten3.lib.wrapper.SlotCm;

import java.util.ArrayList;
import java.util.List;

public class InventoryCm extends SimpleContainer {

    public List<? extends SlotCm> slots;

    public InventoryCm(int size, List<? extends SlotCm> slots) {

        super(size);
        this.slots = slots;
    }

    public InventoryCm copy() {
        InventoryCm inv = new InventoryCm(getContainerSize(), slots);
        for(int i = 0; i < getContainerSize(); i++) {
            inv.setItem(i, getItem(i));
        }
        return inv;
    }

    //used in InvWrapper, by FORGE.
    @Override
    public boolean canPlaceItem(int index, ItemStack stack)
    {
        SlotCm s = match(index);
        return s.isItemValidInHandler(stack);//not mayPlace!
    }

    public List<ItemStack> getStackInRange(int fr, int to) {
        List<ItemStack> lst = new ArrayList<>();
        for(int i = fr; i<= to; i++) {
            if(!getItem(i).isEmpty()) {
                lst.add(getItem(i));
            }
        }
        return lst;
    }

    public SlotCm match(int index) {
        for(SlotCm c : slots) {
            if(c == null) continue;
            if(c.getSlotIndex() == index) {
                return c;
            }
        }
        return null;
    }

    public boolean isIn(int index) {
        for(SlotCm c : slots) {
            if(c == null) continue;
            if(c.getSlotIndex() == index) {
                return c.canHandlerIn();
            }
        }
        return false;
    }

    public boolean isExt(int index) {
        for(SlotCm c : slots) {
            if(c == null) continue;
            if(c.getSlotIndex() == index) {
                return c.canHandlerExt();
            }
        }
        return false;
    }

    public boolean isUsed(int index) {
        for(SlotCm c : slots) {
            if(c == null) continue;
            if(c.getSlotIndex() == index) {
                return true;
            }
        }
        return false;
    }

}
