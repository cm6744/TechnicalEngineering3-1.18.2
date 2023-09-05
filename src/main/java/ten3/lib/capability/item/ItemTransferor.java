package ten3.lib.capability.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.DirectionHelper;

import java.util.List;
import java.util.Queue;

@SuppressWarnings("all")
public class ItemTransferor {

    CmTileMachine t;

    public ItemTransferor(CmTileMachine t) {
        this.t = t;
    }

    public final Queue<Direction> itemQR = DirectionHelper.newQueueOffer();

    public void transferItem() {
        //if(getTileAliveTime() % 10 == 0) {
        itemQR.offer(itemQR.remove());
        for(Direction d : itemQR) {
            transferTo(d);
            transferFrom(d);
            break;
        }
        //}
    }

    private BlockEntity checkTile(Direction d) {

        return checkTile(t.getBlockPos().offset(d.getNormal()));

    }

    private BlockEntity checkTile(BlockPos pos) {

        return t.getLevel().getBlockEntity(pos);

    }

    public static IItemHandler handlerOf(BlockEntity t, Direction d) {

        return t.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, d).orElse(null);

    }

    public void transferTo(BlockPos p, Direction d) {

        if(d != null) {
            if(FaceOption.isPassive(t.info.direCheckItem(d))) return;
            if(!FaceOption.isOut(t.info.direCheckItem(d))) return;
        }

        BlockEntity tile = checkTile(p);
        if(tile != null) {
            IItemHandler src = handlerOf(t, d);
            if(src == null) return;
            IItemHandler dest = handlerOf(tile, DirectionHelper.safeOps(d));
            if(dest == null) return;

            srcToDest(src, dest, false);
        }

    }

    public void transferFrom(BlockPos p, Direction d) {

        if(d != null) {
            if(FaceOption.isPassive(t.info.direCheckItem(d))) return;
            if(!FaceOption.isIn(t.info.direCheckItem(d))) return;
        }

        BlockEntity tile = checkTile(p);
        if(tile != null) {
            IItemHandler src = handlerOf(tile, DirectionHelper.safeOps(d));
            if(src == null) return;
            IItemHandler dest = handlerOf(t, d);
            if(dest == null) return;

            srcToDest(src, dest, true);
        }

    }

    public void transferTo(Direction d) {

        transferTo(t.getBlockPos().offset(d.getNormal()), d);

    }

    public void transferFrom(Direction d) {

        transferFrom(t.getBlockPos().offset(d.getNormal()), d);

    }

    //s - the extract item from src
    //return - src's max cap for <s>
    public static int getFirstSlotFit(IItemHandler src, ItemStack s) {

        ItemStack sin = s.copy();
        int orid = sin.getCount();

        for(int i = 0; i < src.getSlots(); i++) {
            sin = src.insertItem(i, sin, true);
            if(orid > sin.getCount()) {
                return i;
            }
        }

        return -1;

    }

    public void srcToDest(IItemHandler src, IItemHandler dest, boolean into) {

        srcToDest(-1, src, dest, into);

    }

    public void srcToDest(int init, IItemHandler src, IItemHandler dest, boolean into) {

        int iniSlot = init == -1 ? 0 : init;

        if(iniSlot >= src.getSlots()) return;

        ItemStack stack = ItemStack.EMPTY;
        for(int i = iniSlot; i < src.getSlots(); i++) {
            if(!stack.isEmpty()) break;
            stack = src.extractItem(i, into ? t.info.maxReceiveItem : t.info.maxExtractItem, true);
        }

        int cache = stack.getCount();

        for(int i = 0; i < dest.getSlots(); i++) {
            if(stack.isEmpty()) break;
            stack = dest.insertItem(i, stack, true);
        }

        int ins = cache - stack.getCount();
        if(ins == 0) {
            srcToDest(iniSlot + 1, src, dest, into);
        }

        ItemStack s2 = ItemStack.EMPTY;

        for(int i = iniSlot; i < src.getSlots(); i++) {
            if(!s2.isEmpty()) break;
            s2 = src.extractItem(i, ins, false);
        }

        for(int i = 0; i < dest.getSlots(); i++) {
            if(s2.isEmpty()) break;
            s2 = dest.insertItem(i, s2, false);
        }

    }


    //return : stack is completely given.
    public boolean selfGive(ItemStack stack, int from, int to, boolean sim) {

        if(stack.isEmpty()) return true;

        InvHandler dest = (InvHandler) handlerOf(t, null);
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

        InvHandler src = (InvHandler) handlerOf(t, null);
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
