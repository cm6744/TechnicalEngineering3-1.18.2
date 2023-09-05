package ten3.lib.wrapper;

import com.google.common.collect.Lists;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ten3.lib.capability.item.AdvancedInventory;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.mac.IngredientType;

import java.util.List;

public class SlotCm extends Slot {

    boolean isRes;
    ISlotAcceptor acceptor;

    public SlotCm(ISlotAcceptor i, int id, int x, int y) {

        //bug fixed+1
        super(i.getInv(), id, x+1, y+1);
        acceptor = i;
    }

    public SlotCm withIsResultSlot()
    {
        isRes = true;
        return this;
    }

    //check player input, vanilla method(I cannot change it)
    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return isItemValidInHandler(stack) && !isRes;
    }

    //check handler input
    public boolean isItemValidInHandler(ItemStack stack)
    {
        int ind = getSlotIndex();
        return acceptor.valid(ind, stack);
    }

}
