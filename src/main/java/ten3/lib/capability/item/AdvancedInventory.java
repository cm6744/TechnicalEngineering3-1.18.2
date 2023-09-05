package ten3.lib.capability.item;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import ten3.lib.tile.mac.CmTileEntity;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.wrapper.SlotCm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AdvancedInventory extends SimpleContainer {

    public List<? extends SlotCm> slots;
    public CmTileMachine tile;

    public AdvancedInventory(int size, List<? extends SlotCm> slots, CmTileMachine t) {

        super(size);
        this.slots = slots;
        tile = t;
    }

    public AdvancedInventory(ItemStack[] stacks, List<? extends SlotCm> slots, CmTileMachine t) {

        super(stacks);
        this.slots = slots;
        tile = t;
    }

    public boolean asAValidCheckOf(ItemStack stack) {
        for(int i = 0; i < getContainerSize(); i++) {
            if(getItem(i).getItem() == stack.getItem()) {
                return true;
            }
        }
        return false;
    }

    public AdvancedInventory copy() {
        AdvancedInventory inv = new AdvancedInventory(getContainerSize(), slots, tile);
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

}
