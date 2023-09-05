package ten3.core.machine.pipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ten3.core.block.PipeBased;
import ten3.lib.capability.net.InvHandlerWayFinding;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.option.Type;

public class PipeTile extends CmTileMachine {

    public int inventorySize()
    {
        return 0;
    }

    public PipeTile(BlockPos pos, BlockState state) {

        super(pos, state);

    }

    public boolean hasUpgrade()
    {
        return false;
    }

    public boolean hasSideBar()
    {
        return false;
    }

    public IngredientType slotType(int slot)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return true;
    }

    public IngredientType tankType(int tank)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return true;
    }

    @Override
    public Type typeOf() {
        return Type.NON_MAC;
    }

    public boolean isItemCanBeTransferred(ItemStack stack) {
        return true;
    }

    public int getCapacity() {
        return 6;
    }

    @Override
    public void update() {

        if(getTileAliveTime() % 10 == 0) {
            ((PipeBased) getBlockState().getBlock()).update(level, worldPosition);
            InvHandlerWayFinding.updateNet(this);
        }

    }

    @Override
    public LazyOptional<IItemHandler> getItemHandler(Direction d)
    {
        //cannot cache
        return LazyOptional.of(() -> new InvHandlerWayFinding(d, this));
    }

    @Override
    protected boolean hasFaceCapability(Capability<?> cap, Direction d) {
        if(cap != CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return false;
        }
        return super.hasFaceCapability(cap, d);
    }

}
