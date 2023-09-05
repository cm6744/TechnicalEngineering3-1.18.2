package ten3.core.machine.channel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ten3.lib.capability.energy.BatteryTile;
import ten3.lib.capability.item.InvHandler;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.wrapper.SlotCm;

public class ChannelItemTile extends ChannelTile
{

    public ChannelItemTile(BlockPos pos, BlockState state)
    {
        super(pos, state);
        info.setCap(0, 16, 0);

        addSlot(new SlotCm(this, 0, 7, 26));
        addSlot(new SlotCm(this, 1, 25, 26));
        addSlot(new SlotCm(this, 2, 7, 44));
        addSlot(new SlotCm(this, 3, 25, 44));
    }

    public int inventorySize()
    {
        return 4;
    }

    public IngredientType slotType(int slot)
    {
        return IngredientType.BOTH;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return true;
    }

    public void update()
    {
        doBaseData();
        reflection.setActive(false);
        if(!signalAllowRun()) {
            return;
        }

        itr.transferItem();

        if(outputs.size() > 0) {
            reflection.setActive(true);
            BlockPos pos = outputs.get(nowOutputIndex);
            itr.transferTo(pos, null);
            if(!isPosSame(pos)) {
                spiltOut(pos);
            }
        }
        if(inputs.size() > 0) {
            reflection.setActive(true);
            BlockPos pos = inputs.get(nowInputIndex);
            itr.transferFrom(inputs.get(nowInputIndex), null);
            if(!isPosSame(pos)) {
                spiltIn(pos);
            }
        }

        cycle();
    }

    public boolean isPosSame(BlockPos pos)
    {
        return (level.getBlockEntity(pos) instanceof ChannelItemTile);
    }

    protected boolean hasFaceCapability(Capability<?> cap, Direction d)
    {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
                (d == reflection.direction() || d == null);
    }

    IItemHandler handler;

    public void initHandlers()
    {
        handler = new InvHandler(null, this);
    }

    public LazyOptional<IItemHandler> getItemHandler(Direction d)
    {
        return LazyOptional.of(() -> handler);
    }

}
