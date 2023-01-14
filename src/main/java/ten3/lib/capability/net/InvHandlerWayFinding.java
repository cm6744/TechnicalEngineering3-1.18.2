package ten3.lib.capability.net;

import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import ten3.core.machine.pipe.PipeTile;
import ten3.lib.capability.item.InvHandler;
import ten3.lib.capability.item.ItemTransferor;
import ten3.util.ExcUtil;

import java.util.List;

//connect generator with machine.
//#find - return a list of all connections
public class InvHandlerWayFinding extends InvHandler
{

    public static LevelNetsManager<IItemHandler> manager = new LevelNetsManager<>();

    public static void updateNet(PipeTile tile) {
        find(tile);
    }

    private static void find(PipeTile start) {
        Finder.find(manager,
                (t, d) -> ItemTransferor.handlerOf(t, d),
                (t) -> t.getType() == start.getType(),//for item filter
                (t) -> ((PipeTile) t).getCapacity(),
                start);
    }

    IItemHandler object;

    public InvHandlerWayFinding(Direction d, PipeTile t) {

        super(d, t);
        object = ExcUtil.randomInCollection(hand());

    }

    private List<IItemHandler> hand() {
        return manager.getLevelNet(tile).getNet(tile.getBlockPos()).elements;
    }

    private boolean valid(ItemStack stack) {
        if(stack.isEmpty()) return true;
        return manager.getLevelNet(tile).getNet(tile.getBlockPos()).isItemValid(stack);
    }

    private int cap() {
        return Integer.MAX_VALUE;
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
    {
        if(valid(stack)) {
            return listTransferTo(hand(), slot, stack, simulate);
        }
        return stack;
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        ItemStack stack = listTransferFrom(hand(), slot, amount, true);
        if(valid(stack)) {
            return listTransferFrom(hand(), slot, amount, simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlots()
    {
        return object == null ? 0 : object.getSlots();
    }

    @Override
    public Container getInv()
    {
        IItemHandler handler = object;
        if(handler instanceof InvWrapper) {
            return ((InvWrapper) handler).getInv();
        }
        SimpleContainer container = new SimpleContainer(getSlots());
        for(int i = 0; i < container.getContainerSize(); i++) {
            container.setItem(i, getStackInSlot(i));
        }
        return container;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return object == null ? 0 : object.getSlotLimit(slot);
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return object == null ? ItemStack.EMPTY : object.getStackInSlot(slot);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack)
    {
            IItemHandler handler = object;
            if(handler instanceof InvWrapper) {
                ((InvWrapper) handler).setStackInSlot(slot, stack);
            }
    }


    public ItemStack listTransferTo(List<IItemHandler> es, int slot, ItemStack stack, boolean sim) {

        int size = getSizeCanTrs(es);

        if(es == null) return stack;
        if(size == 0) return stack;
        if(object == null) return stack;

        return object.insertItem(slot, stack, sim);

    }

    public ItemStack listTransferFrom(List<IItemHandler> es, int slot, int amount, boolean sim) {

        int size = getSizeCanTrs(es);

        if(es == null) return ItemStack.EMPTY;
        if(size == 0) return ItemStack.EMPTY;
        if(object == null) return ItemStack.EMPTY;

        int am2 = cap();
        return object.extractItem(slot, Math.min(am2, amount), sim);

    }

    public int getSizeCanTrs(List<IItemHandler> es)
    {
        if(es == null) return 0;
        return es.size();
    }

}
