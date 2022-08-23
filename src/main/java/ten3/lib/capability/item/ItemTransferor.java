package ten3.lib.capability.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.DireUtil;

import java.util.List;

@SuppressWarnings("all")
public class ItemTransferor {

    CmTileMachine t;

    public ItemTransferor(CmTileMachine t) {
        this.t = t;
    }

    private BlockEntity checkTile(Direction d) {

        return checkTile(t.pos.offset(d.getNormal()));

    }

    private BlockEntity checkTile(BlockPos pos) {

        return t.getLevel().getBlockEntity(pos);

    }

    public static IItemHandler handlerOf(BlockEntity t, Direction d) {

        return t.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, d).orElse(null);

    }

    public void transferTo(BlockPos p, Direction d) {

        if(FaceOption.isPassive(t.direCheckItem(d))) return;
        if(!FaceOption.isOut(t.direCheckItem(d))) return;

        BlockEntity tile = checkTile(p);
        if(tile != null) {
            IItemHandler src = handlerOf(t, d);
            if(src == null) return;
            IItemHandler dest = handlerOf(tile, DireUtil.safeOps(d));
            if(dest == null) return;

            srcToDest(src, dest, false);
        }

    }

    public void transferFrom(BlockPos p, Direction d) {

        if(FaceOption.isPassive(t.direCheckItem(d))) return;
        if(!FaceOption.isIn(t.direCheckItem(d))) return;

        BlockEntity tile = checkTile(p);
        if(tile != null) {
            IItemHandler src = handlerOf(tile, DireUtil.safeOps(d));
            if(src == null) return;
            IItemHandler dest = handlerOf(t, d);
            if(dest == null) return;

            srcToDest(src, dest, true);
        }

    }

    public void transferTo(Direction d) {

        transferTo(t.pos.offset(d.getNormal()), d);

    }

    public void transferFrom(Direction d) {

        transferFrom(t.pos.offset(d.getNormal()), d);

    }

    //s - the extract item from src
    //return - src's max cap for <s>
    public int getRemainSize(IItemHandler src, ItemStack s) {

        ItemStack sin = s.copy();

        for(int i = 0; i < src.getSlots(); i++) {
            sin = src.insertItem(i, sin, true);
            if(sin.isEmpty()) break;
        }

        return s.getCount() - sin.getCount();

    }

    public void srcToDest(IItemHandler src, IItemHandler dest, boolean into) {

        ItemStack s = ItemStack.EMPTY;
        int i = -1;
        while(s.isEmpty()) {
            i++;
            if(i >= src.getSlots()) break;

            s = src.extractItem(i, Math.min(into ? t.maxReceiveItem : t.maxExtractItem,
                    getRemainSize(dest, src.getStackInSlot(i))), false);
        }

        int k = -1;
        while(true) {
            k++;
            if(k >= dest.getSlots()) break;

            s = dest.insertItem(k, s, false);
            if(s.isEmpty()) break;
        }

    }

    //return : stack is completely given.
    public boolean selfGive(ItemStack stack, int from, int to, boolean sim) {

        if(stack.isEmpty()) return true;

        InventoryWrapperCm dest = (InventoryWrapperCm) handlerOf(t, null);
        if(dest == null) return false;

        int k = from - 1;
        while(true) {
            k++;
            if(k > to) break;

            stack = dest.forceInsert(k, stack, sim);
            if(stack.isEmpty()) break;
        }

        return stack.isEmpty();

    }

    public boolean selfGive(ItemStack stack, boolean sim) {

        return selfGive(stack, 0, t.inventory.getContainerSize() - 1, sim);

    }

    public boolean selfGiveList(List<ItemStack> ss, boolean sim) {

        boolean allReceive = true;

        for(ItemStack stack : ss) {
            if(!selfGive(stack, sim)) {
                allReceive = false;
            }
        }

        return allReceive;

    }

    public ItemStack selfGet(int max, int from, int to, boolean sim) {

        InventoryWrapperCm src = (InventoryWrapperCm) handlerOf(t, null);
        if(src == null) return ItemStack.EMPTY;

        ItemStack s = ItemStack.EMPTY;
        int i = from - 1;
        while(s.getCount() < max || s.isEmpty()) {
            i++;
            if(i > to) break;

            s = src.forceExtract(i, max, sim);
        }

        return s;

    }

    public ItemStack selfGet(int max, boolean sim) {

        return selfGet(max, 0, t.inventory.getContainerSize() - 1, sim);

    }

}
