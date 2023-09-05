package ten3.core.machine.pole;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.option.Type;

public class PoleTile extends CmTileMachine {

    public BlockPos bind;

    public PoleTile(BlockPos pos, BlockState state) {

        super(pos, state);

    }

    public int inventorySize()
    {
        return 0;
    }

    @Override
    public void readTileData(CompoundTag nbt)
    {
        super.readTileData(nbt);
        if(nbt.contains("bind"))
        bind = BlockPos.of(nbt.getLong("bind"));
    }

    @Override
    public void writeTileData(CompoundTag nbt)
    {
        super.writeTileData(nbt);
        if(bind != null)
        nbt.putLong("bind", bind.asLong());
    }

    @Override
    public Type typeOf() {
        return Type.NON_MAC;
    }

    public IngredientType slotType(int slot)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return false;
    }

    public IngredientType tankType(int tank)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return false;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyHandler(Direction d) {
        if(d != Direction.DOWN) return LazyOptional.empty();
        if(bind == null) {
            return LazyOptional.empty();
        }
        BlockEntity be = level.getBlockEntity(bind.below());
        if(be == null) {
            return LazyOptional.empty();
        }
        return be.getCapability(CapabilityEnergy.ENERGY, Direction.UP);
    }

    @Override
    public LazyOptional<IItemHandler> getItemHandler(Direction d) {
        return LazyOptional.empty();
    }

    @Override
    public void update() {}

    @Override
    protected boolean hasFaceCapability(Capability<?> cap, Direction d) {
        if(cap != CapabilityEnergy.ENERGY) {
            return false;
        }
        return super.hasFaceCapability(cap, d);
    }

}
