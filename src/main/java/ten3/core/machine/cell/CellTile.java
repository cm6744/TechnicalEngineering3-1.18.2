package ten3.core.machine.cell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.SlotCm;

public class CellTile extends CmTileMachine {

    public CellTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(getCapacity());

        addSlot(new SlotCm(this, 0, 42, 32));
        addSlot(new SlotCm(this, 1, 115, 32));

    }

    public int inventorySize()
    {
        return 2;
    }

    @Override
    public Type typeOf() {
        return Type.CELL;
    }

    public IngredientType slotType(int slot)
    {
        if(slot == 0) {
            return isItemEnergyFull(inventory.getItem(slot)) ? IngredientType.INPUT : IngredientType.OUTPUT;
        }
        if(slot == 1) {
            return isItemEnergyFull(inventory.getItem(slot)) ? IngredientType.OUTPUT : IngredientType.INPUT;
        }
        return IngredientType.IGNORE;
    }

    private boolean isItemEnergyFull(ItemStack s)
    {
        IEnergyStorage e = s.getCapability(CapabilityEnergy.ENERGY).orElse(null);
        if(e != null) {
            return e.getEnergyStored() >= e.getMaxEnergyStored();
        }
        return false;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        if(slot == 0) {
            return isItemEnergyFull(stack);
        }
        if(slot == 1) {
            return !isItemEnergyFull(stack);
        }
        return false;
    }

    public IngredientType tankType(int tank)
    {
        return null;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return false;
    }

    public int getCapacity() {

        return kFE(1000);

    }

    public void update() {

        super.update();

        if(!signalAllowRun()) {
            return;
        }

        ItemStack stack0 = inventory.getItem(0);
        ItemStack stack1 = inventory.getItem(1);

        if(stack0.getCount() == 1) {
            stack0.getCapability(CapabilityEnergy.ENERGY).ifPresent(
                    (e) -> {
                        if(e.canExtract()) {
                            int diff = e.extractEnergy(Math.min(info.maxReceiveEnergy, info.maxStorageEnergy - data.get(ENERGY)), false);
                            if(diff != 0) {
                                data.translate(ENERGY, diff);
                            }
                        }
                    }
            );
        }

        if(stack1.getCount() == 1) {
            stack1.getCapability(CapabilityEnergy.ENERGY).ifPresent(
                    (e) -> {
                        if(e.canReceive()) {
                            int diff = e.receiveEnergy(Math.min(info.maxExtractEnergy, data.get(ENERGY)), false);
                            if(diff != 0) {
                                data.translate(ENERGY, -diff);
                            }
                        }
                    }
            );
        }

    }

}
